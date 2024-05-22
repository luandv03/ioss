package com.application.controller;

import com.application.entity.OrderItem;
import com.application.model.Model;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderListItem_DaXuLiController implements Initializable {
    public Button btnBackOrderListsItemView;
    public Button btnViewCorrespondingListOrderItemSite;
    public TableView<OrderItem> tableView;
    public TableColumn<OrderItem, Integer> idColumn;
    public TableColumn<OrderItem, String> itemIdColumn;
    public TableColumn<OrderItem, String> itemNameColumn;
    public TableColumn<OrderItem, String> unitColumn;
    public TableColumn<OrderItem, Integer> quantityColumn;
    public TableColumn<OrderItem, Integer> quantityOrderColumn;
    public TableColumn<OrderItem, Integer> quantityreceivedColumn;
    public TableColumn<OrderItem, String> desiredDeliveryDateColumn;
    public TableColumn<OrderItem, Button> viewColumn;

    ObservableList<OrderItem> orderItems;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBackOrderListsItemView.setOnAction(event -> backOrderListsItemView());

        ///Xem danh sách các đơn hàng tương ứng
        btnViewCorrespondingListOrderItemSite.setOnAction(actionEvent -> viewCorrespondingListOrderItemSite());

        testData();
    }

    private void testData()
    {
        orderItems = FXCollections.observableArrayList(
                new OrderItem("MH001","Máy Hút Bụi","Máy",45,"20/4/2024"),
                new OrderItem("MH002","Máy Lọc Nước","Máy",100,"20/4/2024"),
                new OrderItem("MH003","Robot Lau Sàn","Máy",100,"20/4/2024"),
                new OrderItem("MH004","Điều Hòa","Máy",30,"20/4/2024")
        );

        setValue(orderItems);
    }

    private void setValue(ObservableList<OrderItem> orderItems)
    {
        tableView.getItems().clear();

        idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(orderItems.indexOf(param.getValue()) + 1));
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("itemId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("itemName"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("unit"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<OrderItem, Integer>("quantityOrdered"));
        desiredDeliveryDateColumn.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("desiredDeliveryDate"));

        tableView.setItems(orderItems);

        viewColumn.setCellValueFactory(param -> {
            Button button = new Button("Xem chi tiết");
            button.getStyleClass().add("view__button");

            button.setOnAction(event -> {

                viewCorrespondingListOrderItemSite();

                OrderItem selectedItem = tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    // Thực hiện logic hiển thị chi tiết ở đây
                    System.out.println("Xem chi tiết đơn hàng: " + selectedItem.getItemId());
                }
            });

            return new SimpleObjectProperty<>(button);
        });
    }

    public void backOrderListsItemView() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListsItem");
    }

    public void viewCorrespondingListOrderItemSite() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("CorrespondingListOrderItemSite");
    }
}
