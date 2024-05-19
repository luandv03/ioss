package com.application.entity;

// Mặt hàng trong hệ thống mà site đang kinh doanh.
public class ItemSite extends Item {
    private int quantity;

    public ItemSite(String itemId, String itemName, String unit, int quantity) {
        super(itemId, itemName, unit);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
