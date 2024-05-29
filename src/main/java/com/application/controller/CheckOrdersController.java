package com.application.controller;

import com.application.entity.CheckOrder;
import com.application.entity.OrderListItem;
import com.application.entity.OrdersStock;
import com.application.model.Model;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckOrdersController implements Initializable {
    @FXML
    public TableView<CheckOrder> tableView;
    @FXML
    public TableColumn<CheckOrder,Integer> idColumn;
    @FXML
    Label SiteID;
    @FXML
    Label OrderID;
    @FXML
    public TableColumn<CheckOrder,String> itemCodeColumn;
    @FXML
    public TableColumn<CheckOrder,String> itemNameColumn;
    @FXML
    public TableColumn<CheckOrder,String> unitColumn;
    @FXML
    public TableColumn<CheckOrder,Integer> quantityColumn;
    @FXML
    public TableColumn<CheckOrder,Integer> quantityOrderColumn;

    ObservableList<CheckOrder> orderLists;

    public void setID(OrdersStock x){
        SiteID.setText(x.getSiteId());
        OrderID.setText(x.getOrderId());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderLists = FXCollections.observableArrayList(
                new CheckOrder("MH001","Máy hút bụi","máy",45,45),
                new CheckOrder("MH002","Iphone","máy",23,21)
        );
        idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(orderLists.indexOf(param.getValue()) + 1));
        itemCodeColumn.setCellValueFactory(new PropertyValueFactory<CheckOrder,String>("itemId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<CheckOrder,String>("itemName"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<CheckOrder,String>("unit"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<CheckOrder,Integer>("quantity"));
        quantityOrderColumn.setCellValueFactory(new PropertyValueFactory<CheckOrder,Integer>("quantityOrder"));
        tableView.setItems(orderLists);
    }

    public void goBack(ActionEvent e) {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("InventoryManagementView");
    }

    public void Report(ActionEvent e) {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("ReportView");
    }


}