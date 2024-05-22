package com.application.entity;

public class OrderItemOther extends OrderItem{
    private int receivedQuantity;// so luong da nhan
    private int requestedQuantity;// so luong da dat

    public OrderItemOther(String itemId, String itemName, String unit, int quantityOrdered, String desiredDeliveryDate, int receivedQuantity, int requestedQuantity) {
        super(itemId, itemName, unit, quantityOrdered, desiredDeliveryDate);
        this.receivedQuantity = receivedQuantity;
        this.requestedQuantity = requestedQuantity;
    }

    public OrderItemOther(int receivedQuantity, int requestedQuantity) {
        this.receivedQuantity = receivedQuantity;
        this.requestedQuantity = requestedQuantity;
    }

    public int getReceivedQuantity() {
        return receivedQuantity;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }
}
