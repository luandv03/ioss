package com.application.entity;

// Mặt hàng được tạo ra trong hệ thống
public class Item {
    // Ma mat hang
    private String itemId;

    // Ten mat hang
    private String itemName;

    // Don vi
    private String unit;

    public Item(String itemId, String itemName, String unit) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.unit = unit;
    }

    public Item() {
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getUnit() {
        return unit;
    }
}
