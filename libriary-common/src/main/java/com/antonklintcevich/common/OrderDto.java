package com.antonklintcevich.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderDto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -7870350184689366168L;
    @JsonProperty
    private Long id;
    @JsonProperty
    private UserDto userDto;
    @JsonProperty
    private Date orderdate;
    @JsonProperty
    private Set<BookDto> bookDtos=new HashSet<>();
    @JsonProperty
    private BigDecimal price;
    @JsonProperty
    private OrderStatusDto orderStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void addBook(BookDto bookDto) {
        this.bookDtos.add(bookDto);
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public Set<BookDto> getBookDtos() {
        return bookDtos;
    }

    public void setBookDtos(Set<BookDto> bookDtos) {
        this.bookDtos = bookDtos;
    }

    public OrderStatusDto getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatusDto orderStatus) {
        this.orderStatus = orderStatus;
    }
}
