package com.antonklintsevich.persistense;

import java.util.Set;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.antonklintsevich.entity.Book;
import com.antonklintsevich.entity.Order;
import com.antonklintsevich.exception.OrderNotFoundException;

@Repository
public class OrderRepository extends AbstractHibernateDao<Order> {

    public Set<Book> getAllOrderBooks(Long id, EntityManager entityManager) {
        Order order = findOne(id, entityManager).orElseThrow(OrderNotFoundException::new);
        Hibernate.initialize(order.getBooks());
        return order.getBooks();
    }

    public void deleteBooksFromOrder(Long orderId, EntityManager entityManager) {
        Order order = findOne(orderId, entityManager).orElseThrow(OrderNotFoundException::new);
        order.setBooks(null);
        entityManager.merge(order);
    }
}
