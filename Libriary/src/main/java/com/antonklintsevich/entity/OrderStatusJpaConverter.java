package com.antonklintsevich.entity;

import javax.persistence.AttributeConverter;

public class OrderStatusJpaConverter implements AttributeConverter<OrderStatus, String> {
    
    @Override
    public String convertToDatabaseColumn(OrderStatus orderStatus) {
        if (orderStatus == null) {
            return null;
        }
        return orderStatus.toString();
    }
  
    @Override
    public OrderStatus convertToEntityAttribute(String string) {
        if (string == null) {
            return null;
        }
        try {
            return OrderStatus.valueOf(string);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
  
 }