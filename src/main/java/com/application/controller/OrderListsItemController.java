package com.application.controller;

import com.application.entity.Item;
import com.application.entity.OrderListItem;
import com.application.model.Model;
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
        testData();
    }

    private void testData()
    {
        // Thiết lập dữ liệu cho TableView
        orderListsItem = FXCollections.observableArrayList(
                new OrderListItem( "DS001", "Pending", 10),
                new OrderListItem("DS002", "Loading", 5)
        );

        // Set bộ dữ liệu cho bảng
        setValue(orderListsItem);
    }

    public void onOrderListItem() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListItem");
    }public void onOrderListItem_DaXuLi() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListItem_DaXuLi");
    }

    ///SetUp cho thằng tableView hiện dữ liệu
    private void setValue(ObservableList<OrderListItem> orderListsItem)
    {
        tableView.getItems().clear();

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

                    ///Hiển thị các khung khác nhau khi bấm vào các đơn hàng có trạng thái khác nhau
                    switch (selectedItem.getStatus())
                    {
                        case "Loading":
                        case "Done":
                            onOrderListItem_DaXuLi(); break;
                        case "Pending":
                            onOrderListItem(); break;
                        default:
                            System.out.println("Trang thái đơn hàng " + selectedItem.getOrderListItemId() + " không hợp lệ");
                    }
                }

            });

            return new SimpleObjectProperty<>(button);
        });
    }

}
