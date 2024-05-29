package com.application.controller;

import com.application.entity.OrderListItem;
import com.application.entity.OrdersStock;
import com.application.model.Model;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class InventoryManagementController implements Initializable {
    @FXML
    private TableView<OrdersStock> table;
    @FXML
    private TableColumn<OrdersStock, Integer> idColumn;
    @FXML
    private TableColumn<OrdersStock, String> OrderIdColumn;
    @FXML
    private TableColumn<OrdersStock, String> SiteIDColumn;
    @FXML
    private TableColumn<OrdersStock,String> StatusColumn;
    @FXML
    private TableColumn<OrdersStock,String> DateColumn;
    @FXML
    public TableColumn<OrdersStock, Button> viewColumn;
    ObservableList<OrdersStock> orderLists;
    @Override



    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderLists = FXCollections.observableArrayList(
                new OrdersStock("DH001","S001","chưa kiểm tra", "20/4/2024"),
                new OrdersStock("DH002","S002","nhập kho", "23/4/2024")
        );
        idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(orderLists.indexOf(param.getValue()) + 1));
        OrderIdColumn.setCellValueFactory(new PropertyValueFactory<OrdersStock,String>("orderId"));
        StatusColumn.setCellValueFactory(new PropertyValueFactory<OrdersStock,String>("status"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<OrdersStock,String>("ReceivedDate"));
        SiteIDColumn.setCellValueFactory(new PropertyValueFactory<OrdersStock,String>("siteId"));

        table.setItems(orderLists);

        viewColumn.setCellValueFactory(param -> {
            Button button = new Button("Xem chi tiết");
            button.getStyleClass().add("view__button");

            button.setOnAction(event -> {
                Model.getInstance().getViewFactory().getSelectedMenuItem().set("CheckOrders");

            });

            return new SimpleObjectProperty<>(button);
        });
    }

    public void onOrderListItem() {
        Model.getInstance().getViewFactory().getCheckOrdersView();
    }

}