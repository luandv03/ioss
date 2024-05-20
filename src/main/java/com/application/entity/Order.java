package com.application.entity;

public class Order {
    private String orderId;
    private String siteId;
    private String status;

    public Order(String orderId, String siteId, String status) {
        this.orderId = orderId;
        this.siteId = siteId;
        this.status = status;
    }

    public Order() {

    }

    public String getOrderId() {
        return orderId;
    }

    public String getSiteId() {
        return siteId;
    }

    public String getStatus() {
        return status;
    }
}
