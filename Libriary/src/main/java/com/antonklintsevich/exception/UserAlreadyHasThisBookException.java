package com.antonklintsevich.exception;

public class UserAlreadyHasThisBookException extends MyResourceNotFoundException{
    public UserAlreadyHasThisBookException() {
        this("You already have this book");
     }
     
     public UserAlreadyHasThisBookException(String message) {
         super(message);
         
     }
}
