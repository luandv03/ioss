package com.application.entity;

// Mặt hàng trong hệ thống mà site đang kinh doanh.
public class ItemSite extends Item {
    private int quantity;
    private  String desiredDeliveryByShipDate;
    private  String desiredDeliveryByAirDate;


    public ItemSite(String itemId, String itemName, String unit, int quantity, String desiredDeliveryByShipDate, String desiredDeliveryByAirDate) {
        super(itemId, itemName, unit);
        this.quantity = quantity;
        this.desiredDeliveryByShipDate = desiredDeliveryByShipDate;
        this.desiredDeliveryByAirDate = desiredDeliveryByAirDate;
    }

    public int getQuantity() {
        return quantity;
    }
}
