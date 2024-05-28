package com.application.entity;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

public class FindSiteItem extends ItemSite{
    private TextField quantityOrdered;
//    private BooleanProperty selectedSite;
    private CheckBox selectedSite;
    private ComboBox<String> deliveryType;

    public FindSiteItem(String siteId, String siteName, int quantity, String desiredDeliveryByShipDate, String desiredDeliveryByAirDate) {
        super(siteId, siteName, quantity, desiredDeliveryByShipDate, desiredDeliveryByAirDate);
//        this.selectedSite = new SimpleBooleanProperty(false);
        this.selectedSite = new CheckBox();
        this.quantityOrdered = new TextField();
        this.deliveryType = new ComboBox<>();
        this.deliveryType.getItems().addAll("Ship", "Air"); // Thêm các lựa chọn vào ComboBox

        this.quantityOrdered.setDisable(true);
        this.deliveryType.setDisable(true);

        // Liên kết nội dung của ComboBox với selectedDeliveryType
        this.deliveryType.valueProperty().bindBidirectional(new SimpleStringProperty("Ship"));
    }

    public TextField getQuantityOrdered() {
        return quantityOrdered;
    }

//    public BooleanProperty selectedSiteProperty() {
//        return selectedSite;
//    }

    public void selectedSiteProperty(CheckBox checkBox) {
        this.selectedSite = checkBox;
    }

//    public CheckBox getSelectedSite() {
//        CheckBox checkBox = new CheckBox();
//        checkBox.selectedProperty().bindBidirectional(selectedSite);
//        return checkBox;
//    }

    public CheckBox getSelectedSite() {
//        CheckBox checkBox = new CheckBox();
//        checkBox.selectedProperty().bindBidirectional(selectedSite);
        return selectedSite;
    }

    public void setSelectedSite(CheckBox checkBox) {
        this.selectedSite = checkBox;
    }

    public ComboBox<String> getDeliveryType() {
        return deliveryType;
    }

    public void setQuantityOrdered(TextField quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

//    public void setSelectedSite(CheckBox selectedSite) {
//        this.selectedSite.bind(selectedSite.selectedProperty());
//    }

    public void setDeliveryType(ComboBox<String> deliveryType) {
        this.deliveryType = deliveryType;
    }

}
