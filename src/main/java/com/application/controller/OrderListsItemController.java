package com.application.controller;

import com.application.entity.OrderListItem;
import com.application.model.Model;
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
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderListsItemController implements Initializable {
    public TableView<OrderListItem> tableView;

    public TableColumn<OrderListItem, Integer> idColumn;

    public TableColumn<OrderListItem, String> orderListItemIdColumn;

    public TableColumn<OrderListItem, String> statusColumn;

    public TableColumn<OrderListItem, Integer> countColumn;

    public TableColumn<OrderListItem, Button> viewColumn;

    ObservableList<OrderListItem> orderListsItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Thiết lập dữ liệu cho TableView
        orderListsItem = FXCollections.observableArrayList(
                new OrderListItem( "List 1", "Pending", 10),
                new OrderListItem("List 2", "Approved", 5)
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
                onOrderListItem();

                OrderListItem selectedItem = tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    // Thực hiện logic hiển thị chi tiết ở đây
                    System.out.println("Xem chi tiết đơn hàng: " + selectedItem.getOrderListItemId());

                }
            });

            return new SimpleObjectProperty<>(button);
        });
    }

    public void onOrderListItem() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListItem");
    }

}
