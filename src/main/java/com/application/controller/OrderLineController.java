package com.application.controller;

import com.application.entity.OrderDetail;
import com.application.entity.OrderItem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderLineController implements Initializable {
    public Text siteIdElement;
    public Text siteNameElement;
    public  Text orderIdElement;

    public TableView<OrderItem> tableView;
    public TableColumn<OrderItem, Integer> indexColumn;
    public TableColumn<OrderItem, String> itemIdColumn;
    public TableColumn<OrderItem, String> itemNameColumn;
    public TableColumn<OrderItem, String> unitColumn;
    public TableColumn<OrderItem, Integer> quantityOrderedColumn;
    public TableColumn<OrderItem, String> desiredDeliveryDateColumn;

    ObservableList<OrderItem> listOrderItem;

    private OrderDetail orderDetail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        renderOrder();
        renderOrderItem();
    }

    public void renderOrder() {
        siteIdElement.setText(orderDetail.getSiteId());
        siteNameElement.setText(orderDetail.getSiteName());
        orderIdElement.setText(orderDetail.getOrderId());
    }

    public void renderOrderItem(){
        listOrderItem = FXCollections.observableArrayList(orderDetail.getListOrderItem());

        indexColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(listOrderItem.indexOf(param.getValue()) + 1));
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("itemId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("itemName"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("unit"));
        quantityOrderedColumn.setCellValueFactory(new PropertyValueFactory<OrderItem, Integer>("quantityOrdered"));
        desiredDeliveryDateColumn.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("desiredDeliveryDate"));

        tableView.setItems(listOrderItem);
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
        System.out.println("size of list order: " + orderDetail.getListOrderItem().size());
    }
}
