package com.application.controller;

import com.application.entity.CheckOrder;
import com.application.model.Model;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
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

    ObservableList<CheckOrder> orderLists;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderLists = FXCollections.observableArrayList(
                new CheckOrder("MH001","Máy hút bụi","máy",45),
                new CheckOrder("MH002","Iphone","máy",23)
        );
        idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(orderLists.indexOf(param.getValue()) + 1));
        itemCodeColumn.setCellValueFactory(new PropertyValueFactory<CheckOrder,String>("itemId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<CheckOrder,String>("itemName"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<CheckOrder,String>("unit"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<CheckOrder,Integer>("quantity"));
        tableView.setItems(orderLists);
    }

    public void goBack(ActionEvent e) {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("CheckOrders");
    }



}