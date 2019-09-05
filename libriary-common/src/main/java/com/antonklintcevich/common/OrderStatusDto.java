package com.antonklintcevich.common;

public enum OrderStatusDto {
    INPROGRESS("inprogress"), COMPLETED("completed"), GIFT("gift");
    public final String status;

    private OrderStatusDto(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
