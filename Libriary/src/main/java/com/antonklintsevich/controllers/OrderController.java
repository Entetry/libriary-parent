package com.antonklintsevich.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.antonklintcevich.common.GiftDto;
import com.antonklintcevich.common.OrderDto;
import com.antonklintsevich.exception.BookNotFoundException;
import com.antonklintsevich.exception.OrderNotFoundException;
import com.antonklintsevich.services.OrderService;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PutMapping("/orders/addbook")
    public void addBook(@RequestParam("orderId") Long orderId, @RequestParam("bookId") Long bookId) {
        try {
            orderService.addBookToOrder(orderId, bookId);
        } catch (OrderNotFoundException | BookNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }

    }

    @DeleteMapping("/orders/deletebook")
    public void deleteBook(@RequestParam("orderId") Long orderId, @RequestParam("bookId") Long bookId) {
        try {
            orderService.deleteBookFromOrder(orderId, bookId);
        } catch (OrderNotFoundException | BookNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }

    @GetMapping("/orders")
    @ResponseBody
    public List<OrderDto> getAllOrders() {

        return orderService.getAllOrdersAsOrderDTO();
    }

    @DeleteMapping("/orders/{orderId}")
    public void delete(@PathVariable("orderId") Long orderId) {
        try {
            orderService.delete(orderId);
        } catch (OrderNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }

    @PostMapping("/orders/gift")
    public void create(@RequestBody GiftDto giftDto) {

        orderService.sendBookAsaGift(giftDto);
    }

    @PostMapping("/orders")
    public void create(@RequestBody OrderDto orderDto) {

        orderService.create(orderDto);

    }

    @GetMapping("/orders/{orderId}")
    @ResponseBody
    public OrderDto getOrderbyId(@PathVariable("orderId") Long orderId) {
        try {
            return orderService.getOrderById(orderId);
        } catch (OrderNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }

    @PutMapping("/orders/{orderId}")
    public void update(@PathVariable("orderId") Long orderId, @RequestBody OrderDto orderDto) {
        try {
            orderService.update(orderId, orderDto);
        } catch (OrderNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }
}
