package com.application.entity;

public class OrderItemPending extends OrderItem{
    private int selectedQuantity; // so luong da chon
    private int pendingQuantity; // so luong con lai


    public OrderItemPending(String itemId, String itemName, String unit, int quantityOrdered, String desiredDeliveryDate, int selectedQuantity, int pendingQuantity) {
        super(itemId, itemName, unit, quantityOrdered, desiredDeliveryDate);
        this.selectedQuantity = selectedQuantity;
        this.pendingQuantity = pendingQuantity;
    }

    public OrderItemPending(int selectedQuantity, int pendingQuantity) {
        this.selectedQuantity = selectedQuantity;
        this.pendingQuantity = pendingQuantity;
    }

    public int getSelectedQuantity() {
        return selectedQuantity;
    }

    public int getPendingQuantity() {
        return pendingQuantity;
    }
}
