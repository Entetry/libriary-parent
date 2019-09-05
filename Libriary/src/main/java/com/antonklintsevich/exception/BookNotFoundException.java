package com.antonklintsevich.exception;

public class BookNotFoundException extends MyResourceNotFoundException{
    public BookNotFoundException() {
        this("Book not found");
     }
     
     public BookNotFoundException(String message) {
         super(message);
         
     }
}
