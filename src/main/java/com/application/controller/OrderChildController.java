package com.application.controller;

import com.application.entity.OrderItemSite;
import com.application.entity.OrderListItem;
import com.application.model.Model;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderChildController implements Initializable {

    public HBox btnCancelLayout;
    @FXML
    public Label MaSite;
    @FXML
    public Label TenSite;
    @FXML
    public Label MaDonHang;
    @FXML
    public Label TrangThaiDon;
    @FXML
    public Label NgayGiaoDuKien;

    public TableView<OrderItemSite> tableView;
    public TableColumn<OrderItemSite, Integer> idColumn;
    public TableColumn<OrderItemSite, String> idProductColumn;
    public TableColumn<OrderItemSite, String> nameColumn;
    public TableColumn<OrderItemSite, String> unitColumn;
    public TableColumn<OrderItemSite, Integer> quantityColumn;
    @FXML
    public Button reOrder_Button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setValue(String maSite, String tenStie, String maDonHang, String trangThaiDon, String ngayGiaoDuKien, ObservableList<OrderItemSite> List_OrderItemSite) {
        MaSite.setText(maSite);
        TenSite.setText(tenStie);
        MaDonHang.setText(maDonHang);
        TrangThaiDon.setText(trangThaiDon);
        switch (trangThaiDon)
        {
            case "delivering":TrangThaiDon.getStyleClass().add("label-delivering"); break;
            case "canceled":
                TrangThaiDon.getStyleClass().add("label-canceled");
                ngayGiaoDuKien = "N/A";
                reOrder_Button.setOnAction(event -> reOrder());
                reOrder_Button.setVisible(true);
                break;
            case "done":TrangThaiDon.getStyleClass().add("label-done"); break;
            case "pending":TrangThaiDon.getStyleClass().add("label-pending"); break;
            default:TrangThaiDon.getStyleClass().add("label-default"); break;
        }

        NgayGiaoDuKien.setText(ngayGiaoDuKien);

        idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(List_OrderItemSite.indexOf(param.getValue()) + 1));
        idProductColumn.setCellValueFactory(new PropertyValueFactory<OrderItemSite, String>("itemId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<OrderItemSite, String>("itemName"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<OrderItemSite, String>("unit"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<OrderItemSite, Integer>("quantityOrdered"));

        tableView.setItems(List_OrderItemSite);
    }

    //Đặt lại đơn hàng
    public void reOrder()
    {
        System.out.println("Dat Lai Don Hang: " + MaDonHang.getText());
//        Model.getInstance().getViewFactory().getSelectedMenuItem().set("...");
    }
}
