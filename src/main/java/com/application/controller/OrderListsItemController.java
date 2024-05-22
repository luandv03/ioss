package com.application.controller;

import com.application.entity.*;
import com.application.model.Model;
import com.application.subsystemsql.ItemSiteSubsystem;
import com.application.subsystemsql.OrderListItemSubsystem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderListsItemController implements Initializable {
    public TableView<OrderListItem> tableView;

    public TableColumn<OrderListItem, Integer> idColumn;

    public TableColumn<OrderListItem, String> orderListItemIdColumn;

    public TableColumn<OrderListItem, String> statusColumn;

    public TableColumn<OrderListItem, Integer> countColumn;

    public TableColumn<OrderListItem, Button> viewColumn;

    ObservableList<OrderListItem> orderListsItem;

    List<OrderListItem> orderListItemData = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getListOrderItemAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Thiết lập dữ liệu cho TableView
        orderListsItem = FXCollections.observableArrayList(
               orderListItemData
        );

        idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(orderListsItem.indexOf(param.getValue()) + 1));
        orderListItemIdColumn.setCellValueFactory(new PropertyValueFactory<OrderListItem, String>("orderListItemId"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<OrderListItem, String>("status"));
        countColumn.setCellValueFactory(new PropertyValueFactory<OrderListItem, Integer>("countItem"));

        tableView.setItems(orderListsItem);

        viewColumn.setCellValueFactory(param -> {
            Button button = new Button("Xem chi tiết");
            button.getStyleClass().add("view__button");

            button.setOnAction(event -> {

                OrderListItem selectedItem = tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    // Thực hiện logic hiển thị chi tiết ở đây
                    System.out.println("Xem chi tiết đơn hàng: " + selectedItem.getOrderListItemId());
                    System.out.println("Xem chi tiết đơn hàng: " + selectedItem.getStatus());
                    try {
                        onOrderListItem(selectedItem.getOrderListItemId(), selectedItem.getStatus());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            return new SimpleObjectProperty<>(button);
        });
    }

    public void onOrderListItem(String orderListItemId, String status) throws SQLException {
        if (status.equals("pending")) {
            getListOrderItemPending(orderListItemId, status);
            Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListItem");
        } else {
            Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListItem_DaXuLi");
        }

    }

    public void getListOrderItemAll() throws SQLException {
        orderListItemData = new ArrayList<>();

        OrderListItemSubsystem orderListItemSubsystem = new OrderListItemSubsystem();
        List<OrderListItem> orderListsItemSite = orderListItemSubsystem.getListOrderItemAll();

        for (OrderListItem orderListItemSite : orderListsItemSite) {
            orderListItemData.add(new OrderListItem(orderListItemSite.getOrderListItemId(), orderListItemSite.getStatus(), orderListItemSite.getCountItem()));
        }
    }

    // Lay ra 1 danh sach mat hang can dat
    public void getListOrderItemPending(String orderListItemId, String status) throws SQLException {
        OrderListItemController orderListItemController =  OrderListItemController.getInstance();
        orderListItemController.getOrderItemList(orderListItemId, status);
    }

}
