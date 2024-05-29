package com.application.entity;

public class CheckOrder extends Item{
    private int quantity;
    private int quantityOrder;

    public CheckOrder(String itemId, String itemName, String unit, int quantity, int quantityOrder) {
        super(itemId, itemName, unit);
        this.quantity = quantity;
        this.quantityOrder = quantityOrder;
    }
    public CheckOrder(String itemId, String itemName, String unit, int quantity) {
        super(itemId, itemName, unit);
        this.quantity = quantity;
    }

    public CheckOrder() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantityOrder() {
        return quantityOrder;
    }

    public void setQuantityOrder(int quantityOrder) {
        this.quantityOrder = quantityOrder;
    }
}
