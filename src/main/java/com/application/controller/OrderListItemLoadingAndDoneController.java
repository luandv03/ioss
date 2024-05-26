package com.application.controller;

import com.application.entity.FlowHolder;
import com.application.entity.OrderItem;
import com.application.entity.OrderItemLoadingAndDone;
import com.application.entity.OrderListItem;
import com.application.model.Model;
import com.application.subsystemsql.OrderItemLoadAndDoneSubsytem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Flow;

public class OrderListItemLoadingAndDoneController implements Initializable {
    public Label MaDanhSach;
    public Label TrangThaiDanhSach;
    public Button btnBackOrderListsItemView;
    public Button btnViewCorrespondingListOrderItemSite;
    public TableView<OrderItemLoadingAndDone> tableView;
    public TableColumn<OrderItemLoadingAndDone, Integer> idColumn;
    public TableColumn<OrderItemLoadingAndDone, String> itemIdColumn;
    public TableColumn<OrderItemLoadingAndDone, String> itemNameColumn;
    public TableColumn<OrderItemLoadingAndDone, String> unitColumn;
    public TableColumn<OrderItemLoadingAndDone, Integer> totalQuantityColumn;
    public TableColumn<OrderItemLoadingAndDone, Integer> revicedQuantityColumn;
    public TableColumn<OrderItemLoadingAndDone, Integer> orderedQuantityColumn;
    public TableColumn<OrderItemLoadingAndDone, String> desiredDeliveryDateColumn;
    public TableColumn<OrderItemLoadingAndDone, Button> viewColumn;
    ArrayList<OrderItemLoadingAndDone> orderData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBackOrderListsItemView.setOnAction(event -> backOrderListsItemView());

        ///Xem danh sách các đơn hàng tương ứng
        btnViewCorrespondingListOrderItemSite.setOnAction(actionEvent -> viewCorrespondingListOrderItemSite());

        Reload();

        setValue(orderData);
    }

    public void Reload()
    {
        try {
            getAllOrderItemByListId(FlowHolder.flowHolder.getOrderListItemId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setValue(ArrayList<OrderItemLoadingAndDone> data)
    {
        ObservableList<OrderItemLoadingAndDone> orderItems = FXCollections.observableArrayList(
                data
        );

        tableView.getItems().clear();
        idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(orderItems.indexOf(param.getValue()) + 1));
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<OrderItemLoadingAndDone, String>("itemId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<OrderItemLoadingAndDone, String>("itemName"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<OrderItemLoadingAndDone, String>("unit"));
        totalQuantityColumn.setCellValueFactory(new PropertyValueFactory<OrderItemLoadingAndDone, Integer>("totalQuantity"));
        orderedQuantityColumn.setCellValueFactory(new PropertyValueFactory<OrderItemLoadingAndDone, Integer>("orderedQuantity"));
        revicedQuantityColumn.setCellValueFactory(new PropertyValueFactory<OrderItemLoadingAndDone, Integer>("revicedQuantity"));
        desiredDeliveryDateColumn.setCellValueFactory(new PropertyValueFactory<OrderItemLoadingAndDone, String>("desiredDeliveryDate"));

        tableView.setItems(orderItems);

        viewColumn.setCellValueFactory(param -> {
            Button button = new Button("Xem chi tiết");
            button.getStyleClass().add("view__button");

            button.setOnAction(event -> {

                OrderItem selectedItem = tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    // Thực hiện logic hiển thị chi tiết ở đây
                    System.out.println("Xem chi tiết đơn hàng: " + selectedItem.getItemId());

                    //Thực hiện update flow
                    FlowHolder.flowHolder.UpdateId(selectedItem.getItemId(),"",1);
                }

                viewCorrespondingListOrderItemSite();
            });

            return new SimpleObjectProperty<>(button);
        });
    }

    public void backOrderListsItemView() {
        FlowHolder.flowHolder.UpdateId("","",0);
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListsItem");
    }

    public void viewCorrespondingListOrderItemSite() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("CorrespondingListOrderItemSite");
    }

    public void getAllOrderItemByListId(String listId) throws SQLException {
        orderData = new ArrayList<>();
        OrderItemLoadAndDoneSubsytem sub = new OrderItemLoadAndDoneSubsytem();
        orderData.addAll(sub.getAllOrderItemByListId(listId));
    }
}
