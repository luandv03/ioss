package com.application.entity;

public class OrdersStock extends Order {
    private String orderDate; // ngay dat
    private int sumItem;

    public OrdersStock(String orderId, String siteId, String status, String orderDate, int sumItem) {
        super(orderId,siteId,status);
        this.orderDate = orderDate;
        this.sumItem = sumItem;
    }
    public OrdersStock(String orderId, String status, String orderDate) {
        super(orderId, status);
        this.orderDate = orderDate;
    }

    public String getOrderDate() {
        return this.orderDate;
    }

    public int getSumItem() {
        return sumItem;
    }
}