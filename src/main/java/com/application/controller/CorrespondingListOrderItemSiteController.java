package com.application.controller;

import com.application.entity.FlowHolder;
import com.application.entity.OrderItemSite;
import com.application.entity.CorrespondOrderList;
import com.application.model.Model;
import com.application.subsystemsql.CorrespondOrderSubsystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CorrespondingListOrderItemSiteController implements Initializable {
    public Label MaMatHang;
    public Label TrangThaiMatHang;
    public Label MaDanhSach;
    public Label TrangThaiDanhSach;
    public VBox childContainer;
    public Button btnBackToSite;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBackToSite.setOnAction(event -> backOrderListsItemView());
        Reload();
    }

    public void Reload()
    {
        try {
            if (FlowHolder.flowHolder.getItemId() == "")
                getOrderByListOrderId(FlowHolder.flowHolder.getOrderListItemId());
            else
                getOrderByListOrderIdAndItemId(FlowHolder.flowHolder.getOrderListItemId(), FlowHolder.flowHolder.getItemId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadChild(CorrespondOrderList list) {
        loadChild(list.getSiteId(),list.getSiteName(),list.getOrderId(), list.getStatus(),
                list.getDesiredDeliveryDate(), FXCollections.observableArrayList(list.getList()));
    }
    private void loadChild(String maSite, String tenStie, String maDonHang, String trangThaiDon, String ngayGiaoDuKien, ObservableList<OrderItemSite> List_OrderItemSite) {

        FXMLLoader child = Model.getInstance().getViewFactory().getOrderChild();
        try {
            VBox childV = child.load();
            childContainer.getChildren().add(childV);
        }
        catch (IOException e) {}

        OrderChildController orderChildController = child.getController();
        orderChildController.setValue(maSite,tenStie,maDonHang,trangThaiDon,ngayGiaoDuKien, List_OrderItemSite);
    }

    public void backOrderListsItemView() {
        FlowHolder.flowHolder.UpdateId("","",1);
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListItemLoadingAndDone");
    }

    public void getOrderByListOrderId(String listOrderId) throws SQLException
    {
        CorrespondOrderSubsystem sub = new CorrespondOrderSubsystem();
        for (var orderId: sub.getOrderIdByOrderListId(listOrderId))
        {
            loadChild(sub.getCorrespondOrderListByOrderId(orderId));
        }
    }

    public void getOrderByListOrderIdAndItemId(String listOrderId, String itemId) throws SQLException
    {
        MaMatHang.setText(FlowHolder.flowHolder.getItemId());
        MaDanhSach.setText(FlowHolder.flowHolder.getOrderListItemId());
        TrangThaiDanhSach.setText(FlowHolder.flowHolder.getStatus());

        CorrespondOrderSubsystem sub = new CorrespondOrderSubsystem();
        for (var orderId: sub.getOrderIdByOrderListIdAndItemId(listOrderId,itemId))
        {
            loadChild(sub.getCorrespondOrderListByOrderId(orderId));
            System.out.println(orderId);
        }
    }
}
