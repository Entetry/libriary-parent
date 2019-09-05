package com.antonklintsevich.exception;

public class SubgenreNotFoundException extends MyResourceNotFoundException {
    public SubgenreNotFoundException() {
       this("Subgenre not found");
    }
    
    public SubgenreNotFoundException(String message) {
        super(message);
        
    }
}
