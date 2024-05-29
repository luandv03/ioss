package com.application.entity;

// Đơn hàng: được tạo ra khi BPĐHQT gửi yêu cầu đặt hàng đến Site
public class Order {
    private String orderId;
    private String siteId;
    private String status;

    public Order(String orderId, String siteId, String id) {
        this.orderId = orderId;
        this.siteId = siteId;
        this.status = id;
    }

    public Order(String orderId, String status) {
        this.orderId = orderId;
        this.status = status;
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
