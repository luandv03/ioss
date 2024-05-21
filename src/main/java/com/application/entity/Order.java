package com.application.entity;

// Đơn hàng: được tạo ra khi BPĐHQT gửi yêu cầu đặt hàng đến Site
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
