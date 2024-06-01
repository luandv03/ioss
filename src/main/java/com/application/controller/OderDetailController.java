package com.application.controller;
import com.application.entity.OrderItemDetail;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OderDetailController implements Initializable {
    private static OderDetailController instance;


    public TableView<OrderItemDetail> tableView;

    public TableColumn<OrderItemDetail, Integer> idColumn;

    public TableColumn<OrderItemDetail, Integer> orderItemIdColumn;

    public TableColumn<OrderItemDetail, String> orderItemNameColumn;

    public TableColumn<OrderItemDetail, Integer> orderItemQuantityColumn;

    public TableColumn<OrderItemDetail, String> orderItemDesiredDeliveryDate;

    public TableColumn<OrderItemDetail, Integer> orderItemPickedQuantity;

    public TableColumn<OrderItemDetail, Integer> orderItemRemainQuantity;


    public TableColumn<OrderItemDetail, Button> findSiteColumn;

    ObservableList<OrderItemDetail> orderItemDetail;

    public static OderDetailController getInstance() throws SQLException {
        if (instance == null) {
            instance = new OderDetailController();
        }
        return instance;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Thiết lập dữ liệu cho TableView
        orderItemDetail = FXCollections.observableArrayList(
                new OrderItemDetail("0001", "Bao cao su Durex", "Hộp", 100, "22-05-2024",0,100),
                new OrderItemDetail("0002", "Bao cao su Invisible", "Hộp", 120, "22-05-2024",0,100)
        );
        idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(orderItemDetail.indexOf(param.getValue()) + 1));
        orderItemIdColumn.setCellValueFactory(new PropertyValueFactory<OrderItemDetail, Integer>("itemId"));
        orderItemNameColumn.setCellValueFactory(new PropertyValueFactory<OrderItemDetail, String>("itemName"));
        orderItemQuantityColumn.setCellValueFactory(new PropertyValueFactory<OrderItemDetail, Integer>("quantityOrdered"));
        orderItemDesiredDeliveryDate.setCellValueFactory(new PropertyValueFactory<OrderItemDetail, String>("desiredDeliveryDate"));
        orderItemPickedQuantity.setCellValueFactory(new PropertyValueFactory<OrderItemDetail, Integer>("pickedQuantity"));
        orderItemRemainQuantity.setCellValueFactory(new PropertyValueFactory<OrderItemDetail, Integer>("remainQuantity"));



        tableView.setItems(orderItemDetail);
        findSiteColumn.setCellValueFactory(param -> {
            Button button = new Button("Tìm Site");
            button.getStyleClass().add("view__button");
            return new SimpleObjectProperty<>(button);
        });


    }


}
