package com.antonklintsevich.exception;

public class NotEnoughMoneyException extends MyResourceNotFoundException {
    public NotEnoughMoneyException() {
        this("Not enough money");
    }

    public NotEnoughMoneyException(String message) {
        super(message);

    }
}