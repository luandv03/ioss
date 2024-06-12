package com.application.controller;

import com.application.entity.FlowHolder;
import com.application.entity.Order;
import com.application.entity.OrderListItem;
import com.application.entity.OrdersStock;
import com.application.model.Model;
import com.application.subsystemsql.OrderSubsystem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InventoryManagementController implements Initializable {
    private static InventoryManagementController instance;

    @FXML
    private TableView<OrdersStock> table;
    @FXML
    private TableColumn<OrdersStock, Integer> idColumn;
    @FXML
    private TableColumn<OrdersStock, String> OrderIdColumn;
    @FXML
    private TableColumn<OrdersStock,String> StatusColumn;
    @FXML
    private TableColumn<OrdersStock,String> DateColumn;

    public TableColumn<OrdersStock, Integer> SumItemColumn;
    @FXML
    public TableColumn<OrdersStock, Button> viewColumn;
    ObservableList<OrdersStock> orderLists;
    List<OrdersStock> ordersStockList = new ArrayList<>();


    public static InventoryManagementController getInstance() {
        if (instance == null) {
            instance = new InventoryManagementController();
        }
        return instance;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getListOrder();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        orderLists = FXCollections.observableArrayList(ordersStockList);

        idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(orderLists.indexOf(param.getValue()) + 1));
        OrderIdColumn.setCellValueFactory(new PropertyValueFactory<OrdersStock,String>("orderId"));
        StatusColumn.setCellValueFactory(new PropertyValueFactory<OrdersStock,String>("status"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<OrdersStock,String>("orderDate"));
        SumItemColumn.setCellValueFactory(new PropertyValueFactory<OrdersStock,Integer>("sumItem"));

        table.setItems(orderLists);

        viewColumn.setCellFactory(column -> {
            return new TableCell<OrdersStock, Button>() {
                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        Button button = new Button("Xem chi tiết");
                        button.getStyleClass().add("view__item__button");

                        // Lưu trữ index của dòng hiện tại vào TableCell
                        int rowIndex = getIndex();

                        button.setOnAction(event -> {
                            // Truy cập index của dòng hiện tại
                            System.out.println("Index của dòng: " + rowIndex);

                            OrdersStock selectedItem = getTableView().getItems().get(rowIndex);

                            if (selectedItem != null) {
                                // Thực hiện logic hiển thị chi tiết 1 dsmhcd ở đây
                                String orderId = selectedItem.getOrderId();
                                String status = selectedItem.getStatus();

                                try {
                                    showListOrderItemSite(orderId, status);

                                    Model.getInstance().getViewFactory().resetInventoryManagementView();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });

                        setGraphic(button);
                    }
                }
            };
        });
    }

    public void showListOrderItemSite(String orderId, String status) throws SQLException {
        CheckOrdersController c = CheckOrdersController.getInstance();
        c.setOrderId(orderId);
        c.setStatus(status);
        c.getListOrderSiteItem(orderId);

        Model.getInstance().getViewFactory().getSelectedMenuItem().set("CheckOrders");
    }

    public void getListOrder() throws SQLException {
        OrderSubsystem orderSubsystem = new OrderSubsystem();

        ordersStockList = orderSubsystem.getListOrderByStatus("delivering");
    }

}