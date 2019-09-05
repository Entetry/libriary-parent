package com.antonklintsevich.entity;

public enum OrderStatus {
    INPROGRESS("inprogress"), COMPLETED("completed"),GIFT("gift");
    public final String status;

    private OrderStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return this.status;
    }
}
