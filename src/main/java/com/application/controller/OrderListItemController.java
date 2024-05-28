package com.application.controller;

import com.application.entity.*;
import com.application.model.Model;
import com.application.subsystemsql.ItemSiteSubsystem;
import com.application.subsystemsql.OrderListItemSubsystem;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.List;

public class OrderListItemController implements Initializable {

    private static OrderListItemController instance;
    private String orderListItemId;
    private String orderListItemStatus;
    public Button btnBackOrderListsItemView;

    public Button btnOrder;
    public Text orderListItemIdValue;
    public Text orderListItemStatusValue;

    public TableView<OrderItemPending> tableView;
    public TableColumn<OrderItemPending, Integer> indexCol;
    public TableColumn<OrderItemPending, String> itemIdCol;
    public TableColumn<OrderItemPending, String> itemNameCol;
    public TableColumn<OrderItemPending, String> unitCol;
    public TableColumn<OrderItemPending, String>  desiredDeliveryDateCol;
    public TableColumn<OrderItemPending, Integer> quantityOrderedCol;

    public TableColumn<OrderItemPending, Integer> selectedQuantityCol;
    public TableColumn<OrderItemPending, Integer> pendingQuantityCol;
    public TableColumn<OrderItemPending, Button> btnFindSite;


    ObservableList<OrderItemPending> orderItemLists;

    List<OrderItemPending> listDataItem = new ArrayList<>();

    public OrderListItemController() {

    }

    public static OrderListItemController getInstance() throws SQLException {
        if (instance == null) {
            instance = new OrderListItemController();
        }
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBackOrderListsItemView.setOnAction(event -> {
            backOrderListsItemView();
        });

        orderListItemIdValue.setText(orderListItemId);
        orderListItemStatusValue.setText(orderListItemStatus);

        renderListOrderItem();
    }

    public void renderListOrderItem() {
        // Thiết lập dữ liệu cho TableView
        orderItemLists = FXCollections.observableArrayList(listDataItem);

        indexCol.setCellValueFactory(param -> new SimpleObjectProperty<>(orderItemLists.indexOf(param.getValue()) + 1));
        itemIdCol.setCellValueFactory(new PropertyValueFactory<OrderItemPending, String>("itemId"));
        itemNameCol.setCellValueFactory(new PropertyValueFactory<OrderItemPending, String>("itemName"));
        unitCol.setCellValueFactory(new PropertyValueFactory<OrderItemPending, String>("unit"));
        desiredDeliveryDateCol.setCellValueFactory(new PropertyValueFactory<OrderItemPending, String>("desiredDeliveryDate"));
        quantityOrderedCol.setCellValueFactory(new PropertyValueFactory<OrderItemPending, Integer>("quantityOrdered"));
        selectedQuantityCol.setCellValueFactory(new PropertyValueFactory<OrderItemPending, Integer>("selectedQuantity"));
        pendingQuantityCol.setCellValueFactory(new PropertyValueFactory<OrderItemPending, Integer>("pendingQuantity"));
//
//        btnFindSite.setCellValueFactory(param -> {
//            Button btn = new Button("Tìm site");
//            btn.getStyleClass().add("view__item__button");
//
//            btn.setOnAction(event -> {
//
//                OrderItemPending selectedItem = tableView.getSelectionModel().getSelectedItem();
//                if (selectedItem != null) {
//                    try {
//                        System.out.println(selectedItem.getItemId());
//                        String itemId = selectedItem.getItemId();
//                        String itemName = selectedItem.getItemName();
//                        String unit = selectedItem.getUnit();
//                        int quantity = selectedItem.getQuantityOrdered();
//                        String desiredDeliveryDate = selectedItem.getDesiredDeliveryDate();
//                        int selectedQuantity = selectedItem.getSelectedQuantity();
//
//                        OrderItem orderItem = new OrderItem(itemId, itemName, unit, quantity, desiredDeliveryDate);
//                        findSite(orderItem, selectedQuantity);
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//
//            });
//
//            return new SimpleObjectProperty<>(btn);
//        });

        btnFindSite.setCellFactory(column -> {
                    return new TableCell<OrderItemPending, Button>() {
                        @Override
                        protected void updateItem(Button item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty) {
                                setGraphic(null);
                            } else {
                                Button btn = new Button("Tìm site");
                                btn.getStyleClass().add("view__item__button");


                                // Lưu trữ index của dòng hiện tại vào TableCell
                                int rowIndex = getIndex();

                                btn.setOnAction(event -> {

                                    OrderItemPending selectedItem = getTableView().getItems().get(rowIndex);

                                    if (selectedItem != null) {
                                        try {
                                            System.out.println(selectedItem.getItemId());
                                            String itemId = selectedItem.getItemId();
                                            String itemName = selectedItem.getItemName();
                                            String unit = selectedItem.getUnit();
                                            int quantity = selectedItem.getQuantityOrdered();
                                            String desiredDeliveryDate = selectedItem.getDesiredDeliveryDate();
                                            int selectedQuantity = selectedItem.getSelectedQuantity();

                                            OrderItem orderItem = new OrderItem(itemId, itemName, unit, quantity, desiredDeliveryDate);
                                            findSite(orderItem, selectedQuantity);
                                        } catch (SQLException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }

                                });

                                setGraphic(btn);
                            }
                        }
                    };
        });


        tableView.setItems(orderItemLists);
    }

    public void backOrderListsItemView() {
        Model.getInstance().getViewFactory().resetOrderListItemView();
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListsItem");
    }

    public void findSite(OrderItem orderItem, int selectedQuantity) throws SQLException {
        FindSiteController findSiteController =  FindSiteController.getInstance(orderItem.getItemId());
        findSiteController.findItemSiteByItemId(orderListItemId, orderItem, selectedQuantity);
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("FindSite"); // thực hiện load FXML của file FindSite.FXML
    }

    public void getOrderItemList(String orderListItemId, String status) throws SQLException {
        this.orderListItemId = orderListItemId;
        this.orderListItemStatus = status;

        OrderListItemSubsystem orderListItemSubsystem = new OrderListItemSubsystem();
        listDataItem = orderListItemSubsystem.getListOrderItemById(orderListItemId, status);
    }

}