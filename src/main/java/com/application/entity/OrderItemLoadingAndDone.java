package com.application.entity;

public class OrderItemLoadingAndDone extends OrderItem{

    private int totalQuantity;   // Tổng số lượng cần đặt
    private int revicedQuantity; // số lượng đã nhận
    private int orderedQuantity; // tổng số lượng đã đặt

    public OrderItemLoadingAndDone(String itemId, String itemName, String unit,int totalQuantity, int orderedQuantity,int revicedQuantity, String desiredDeliveryDate) {
        super(itemId, itemName, unit, orderedQuantity, desiredDeliveryDate);
        this.totalQuantity = totalQuantity;
        this.revicedQuantity = revicedQuantity;
        this.orderedQuantity = orderedQuantity;
    }

    public OrderItemLoadingAndDone(int selectedQuantity, int pendingQuantity) {
        this.revicedQuantity = selectedQuantity;
        this.orderedQuantity = pendingQuantity;
    }

    public int getTotalQuantity() {return totalQuantity;}
    public int getRevicedQuantity() {
        return revicedQuantity;
    }
    public int getOrderedQuantity() {
        return orderedQuantity;
    }
}
