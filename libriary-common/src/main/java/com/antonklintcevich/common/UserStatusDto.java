package com.antonklintcevich.common;

public enum UserStatusDto {
    BASIC("Basic"), INVALID("Invalid");
    public final String status;

    private UserStatusDto(String status) {
        this.status = status;
    }

    public String getUserStatus() {
        return this.status;
    }
}
