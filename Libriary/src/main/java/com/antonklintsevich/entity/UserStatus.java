package com.antonklintsevich.entity;

public enum UserStatus {
    BASIC("Basic"), INVALID("Invalid");
    public final String status;

    private UserStatus(String status) {
        this.status = status;
    }
    public String getUserStatus() {
        return this.status;
    }
}
