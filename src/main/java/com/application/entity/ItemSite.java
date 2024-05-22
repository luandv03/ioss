package com.application.entity;

// Mặt hàng trong hệ thống mà site đang kinh doanh.
public class ItemSite extends Item {

    private String siteId;
    private String siteName;
    private int quantity;
    private  String desiredDeliveryByShipDate;
    private  String desiredDeliveryByAirDate;

    public ItemSite(String siteId, String siteName, int quantity, String desiredDeliveryByShipDate, String desiredDeliveryByAirDate) {
        this.siteId = siteId;
        this.siteName = siteName;
        this.quantity = quantity;
        this.desiredDeliveryByShipDate = desiredDeliveryByShipDate;
        this.desiredDeliveryByAirDate = desiredDeliveryByAirDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSiteId() {
        return siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getDesiredDeliveryByShipDate() {
        return desiredDeliveryByShipDate;
    }

    public String getDesiredDeliveryByAirDate() {
        return desiredDeliveryByAirDate;
    }
}
