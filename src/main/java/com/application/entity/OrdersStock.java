package com.application.entity;

public class OrdersStock extends Order {
    private String ReceivedDate;

    public OrdersStock(String orderId, String siteId, String status, String receivedDate) {
        super(orderId,siteId,status);
        ReceivedDate = receivedDate;
    }
    public OrdersStock(String orderId, String status, String receivedDate) {
        super(orderId, status);
        ReceivedDate = receivedDate;
    }

    public String getReceivedDate() {
        return ReceivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        ReceivedDate = receivedDate;
    }

}
