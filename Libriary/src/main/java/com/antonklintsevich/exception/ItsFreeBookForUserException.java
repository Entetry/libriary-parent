package com.antonklintsevich.exception;

public class ItsFreeBookForUserException extends MyResourceNotFoundException{
    public ItsFreeBookForUserException() {
        this("It is free book for this User");
     }
     
     public ItsFreeBookForUserException(String message) {
         super(message);
         
     }
     public ItsFreeBookForUserException(String message,String username) {
         super(message + username);
         
     }
}
