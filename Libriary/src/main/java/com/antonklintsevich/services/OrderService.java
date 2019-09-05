package com.antonklintsevich.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antonklintcevich.common.BookDto;
import com.antonklintcevich.common.GiftDto;
import com.antonklintcevich.common.OrderDto;
import com.antonklintsevich.common.DtoConverter;
import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Order;
import com.antonklintsevich.entity.OrderStatus;
import com.antonklintsevich.entity.User;
import com.antonklintsevich.exception.BookNotFoundException;
import com.antonklintsevich.exception.ItsFreeBookForUserException;
import com.antonklintsevich.exception.NotEnoughMoneyException;
import com.antonklintsevich.exception.OrderNotFoundException;
import com.antonklintsevich.exception.UserAlreadyHasThisBookException;
import com.antonklintsevich.exception.UserNotFoundException;
import com.antonklintsevich.persistense.BookRepository;
import com.antonklintsevich.persistense.OrderRepository;
import com.antonklintsevich.persistense.UserRepository;

@Service
public class OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    UserServiceIml userServiceIml;

    public void delete(Long orderId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        orderRepository.findOne(orderId, entityManager).orElseThrow(OrderNotFoundException::new);
        try {
            orderRepository.deleteBooksFromOrder(orderId, entityManager);
            orderRepository.deleteById(orderId, entityManager);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("An exeption ocurred!", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public void update(Long orderId, OrderDto orderDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Order order = orderRepository.findOne(orderId, entityManager).orElseThrow(OrderNotFoundException::new);
        order.setPrice(orderDto.getPrice());
        order.setBooks(DtoConverter.constructBookSet(orderDto.getBookDtos()));
        order.setPrice(orderDto.getPrice());
        order.setOrderdate(orderDto.getOrderdate());
        try {
            orderRepository.update(order, entityManager);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("An exeption ocurred!", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    final public void create(OrderDto orderDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Order order = new Order();
        BigDecimal totalPrice = new BigDecimal(0);
        User user = userServiceIml.getCurrentUser();
        order.setUser(user);
        Set<Book> userBooks = userRepository.getAllUserBooks(order.getUser().getId(), entityManager);
        for (BookDto bookDto : orderDto.getBookDtos()) {
            Book book = bookRepository.findOne(bookDto.getId(), entityManager).orElseThrow(BookNotFoundException::new);
            if (userBooks.contains(book)) {
                throw new UserAlreadyHasThisBookException();
            }
            if ("Invalid".equals(user.getStatus().getUserStatus())) {
                if (book.getPrice().compareTo(new BigDecimal(10.00)) != -1) {
                    totalPrice = totalPrice.add(book.getPrice());
                }
            } else {
                totalPrice = totalPrice.add(book.getPrice());
            }
            order.addBook(book);
        }
        order.setOrderdate(orderDto.getOrderdate());
        order.setPrice(totalPrice);
        order.setOrderStatus(OrderStatus.INPROGRESS);
        try {
            orderRepository.create(order, entityManager);
            confirmOrder(order, entityManager);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("An exeption ocurred!", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    private synchronized void confirmOrder(Order order, EntityManager entityManager) {
        User user = userRepository.findOne(order.getUser().getId(), entityManager)
                .orElseThrow(UserNotFoundException::new);
        if (user.getWallet().getBalance().compareTo(order.getPrice()) == -1) {
            throw new NotEnoughMoneyException();
        }
        if ("inprogress".equals(order.getOrderStatus().getStatus())) {
            order.getBooks().forEach(book ->{ 
                if (user.getBooks().contains(book)) {
                    throw new UserAlreadyHasThisBookException();
                }
                user.addBook(book);});
            user.getWallet().setBalance(order.getUser().getWallet().getBalance().subtract(order.getPrice()));
            order.setOrderStatus(OrderStatus.COMPLETED);
            userRepository.update(user, entityManager);
            orderRepository.update(order, entityManager);
        }

    }

    private synchronized void confirmGift(Order order, GiftDto giftDto, EntityManager entityManager) {
        User user = order.getUser();
        User recipient = userRepository.findByUsername(giftDto.getRecipientName(), entityManager)
                .orElseThrow(UserNotFoundException::new);

        if (user.getWallet().getBalance().compareTo(order.getPrice()) == -1) {
            throw new NotEnoughMoneyException();
        }
        if ("gift".equals(order.getOrderStatus().getStatus())) {
            user.getWallet().setBalance(order.getUser().getWallet().getBalance().subtract(order.getPrice()));
            order.getBooks().forEach(book -> {
                if (recipient.getBooks().contains(book)) {
                    throw new UserAlreadyHasThisBookException();
                }
                recipient.addBook(book);
            });
            userRepository.update(user, entityManager);
            userRepository.update(recipient, entityManager);
            order.setOrderStatus(OrderStatus.COMPLETED);
            orderRepository.update(order, entityManager);
        }

    }

    public void sendBookAsaGift(GiftDto giftDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Order order = new Order();
        BigDecimal totalPrice = new BigDecimal(0);
        order.setUser(userRepository.findByUsername(userServiceIml.getCurrentUserUsername(), entityManager)
                .orElseThrow(UserNotFoundException::new));
        User recipient = userRepository.findByUsername(giftDto.getRecipientName(), entityManager)
                .orElseThrow(UserNotFoundException::new);
        for (BookDto bookDto : giftDto.getBookDtos()) {
            Book book = bookRepository.findOne(bookDto.getId(), entityManager).orElseThrow(BookNotFoundException::new);
            if (recipient.getBooks().contains(book)) {
                throw new UserAlreadyHasThisBookException();
            }
            if (("Invalid".equals(recipient.getStatus().getUserStatus())) && (book.getPrice().compareTo(new BigDecimal(10.00)) == -1)) {
                throw new ItsFreeBookForUserException("You cannot send this book,its free for ",
                        recipient.getUsername());
            }
            order.addBook(book);
            totalPrice = totalPrice.add(book.getPrice());
        }
        order.setOrderdate(new Date());
        order.setPrice(totalPrice);
        order.setOrderStatus(OrderStatus.GIFT);
        if (order.getUser().getWallet().getBalance().compareTo(totalPrice) == -1)
            throw new NotEnoughMoneyException();

        try {
            orderRepository.create(order, entityManager);
            confirmGift(order, giftDto, entityManager);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("An exeption ocurred!", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public List<OrderDto> getAllOrdersAsOrderDTO() {
        return getAllOrders().stream().map(order -> DtoConverter.constructOrderDTO(order)).collect(Collectors.toList());
    }

    private List<Order> getAllOrders() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return orderRepository.findAll(entityManager);
        } finally {
            entityManager.close();
        }
    }

    public OrderDto getOrderById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        OrderDto orderDto = DtoConverter
                .constructOrderDTO(orderRepository.findOne(id, entityManager).orElseThrow(OrderNotFoundException::new));
        orderDto.setBookDtos(DtoConverter.constructBookDtoSet(getAllOrderBooks(id)));
        return orderDto;
    }

    private Set<Book> getAllOrderBooks(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return orderRepository.getAllOrderBooks(id, entityManager);
        } finally {
            entityManager.close();
        }
    }

    public void addBookToOrder(Long orderId, Long bookId) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        System.out.println("Adding a book...");
        Order order = orderRepository.findOne(orderId, entityManager).orElseThrow(OrderNotFoundException::new);
        order.addBook(bookRepository.findOne(bookId, entityManager).orElseThrow(OrderNotFoundException::new));
        try {
            orderRepository.update(order, entityManager);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("An exeption ocurred!", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public void deleteBookFromOrder(Long orderId, Long bookId) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        System.out.println("Adding a book...");
        Order order = orderRepository.findOne(orderId, entityManager).orElseThrow(OrderNotFoundException::new);
        Set<Book> books = orderRepository.getAllOrderBooks(orderId, entityManager);
        books.remove(bookRepository.findOne(bookId, entityManager).orElseThrow(BookNotFoundException::new));
        order.setBooks(books);
        try {
            orderRepository.update(order, entityManager);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("An exeption ocurred!", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }
}
