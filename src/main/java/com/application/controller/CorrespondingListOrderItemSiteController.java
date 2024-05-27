package com.application.controller;

import com.application.entity.FlowHolder;
import com.application.entity.OrderItemLoadingAndDone;
import com.application.entity.OrderItemSite;
import com.application.entity.CorrespondOrderList;
import com.application.model.Model;
import com.application.subsystemsql.CorrespondOrderSubsystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CorrespondingListOrderItemSiteController implements Initializable {
    public Label MaMatHang;
    public Label TrangThaiMatHang;
    public Label MaDanhSach;
    public Label TrangThaiDanhSach;
    public VBox childContainer;
    public Button btnBackToSite;
    private ArrayList<CorrespondOrderList> orderData;
    public MenuButton menuButton;
    public TextField keywordsearch;
    public Button btn_Search;
    public static int OptionMenu;

    private String[] menuItemLabel = {
            "Tìm theo mã site",
            "Tìm theo tên site",
            "Tìm theo mã đơn hàng",
            "Tìm theo trạng thái đơn hàng",
            "Tìm theo ngày mong muốn nhận",
            "Tìm theo mã mặt hàng",
            "Tìm theo tên mặt hàng",
            "Tìm theo đơn vị",
            "Tìm theo số lượng"
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBackToSite.setOnAction(event -> backOrderListsItemView());

        btn_Search.setOnAction(actionEvent->filterOrderData());
        MenuItem[] filter = new MenuItem[menuItemLabel.length];
        for (int i = 0; i<menuItemLabel.length; i++) {
            final int index = i;
            filter[i] = new MenuItem(menuItemLabel[i]);
            filter[i].setOnAction(event -> {
                filter[OptionMenu].setStyle("-fx-background-fill:black");
                OptionMenu = index;
                filter[index].setStyle("-fx-text-fill:purple");
            });
            menuButton.getItems().add(filter[i]);
        }

        Reload();
    }


    public void Reload()
    {
        TrangThaiDanhSach.setText(FlowHolder.flowHolder.getStatus());
        switch (TrangThaiDanhSach.getText())
        {
            case "loading":TrangThaiDanhSach.getStyleClass().add("label-delivering"); break;
            case "pending":TrangThaiDanhSach.getStyleClass().add("label-pending"); break;
            case "done":TrangThaiDanhSach.getStyleClass().add("label-done"); break;
            default:
        }

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
        orderData = new ArrayList<>();

        MaDanhSach.setText(FlowHolder.flowHolder.getOrderListItemId());

        CorrespondOrderSubsystem sub = new CorrespondOrderSubsystem();
        for (var orderId: sub.getOrderIdByOrderListId(listOrderId))
        {
            var order = sub.getCorrespondOrderListByOrderId(orderId);
            orderData.add(order);
            loadChild(order);
        }
    }

    public void getOrderByListOrderIdAndItemId(String listOrderId, String itemId) throws SQLException
    {
        orderData = new ArrayList<>();

        MaDanhSach.setText(FlowHolder.flowHolder.getOrderListItemId());
        MaMatHang.setText(FlowHolder.flowHolder.getItemId());

        CorrespondOrderSubsystem sub = new CorrespondOrderSubsystem();
        for (var orderId: sub.getOrderIdByOrderListIdAndItemId(listOrderId,itemId))
        {
            var order = sub.getCorrespondOrderListByOrderId(orderId);
            orderData.add(order);
            loadChild(order);
            //System.out.println(orderId);
        }
    }

    public void filterOrderData() {
        ArrayList<CorrespondOrderList> filterData = new ArrayList<>();
        for (var data : orderData) {
            if (keywordsearch.getText().isEmpty() || keywordsearch.getText().isBlank() || keywordsearch.getText() == null)
                filterData.add(data);
            else
            if (OptionMenu == 0 && data.getSiteId().contains(keywordsearch.getText()))
                filterData.add(data);
            else
            if (OptionMenu == 1 && data.getSiteName().contains(keywordsearch.getText()))
                filterData.add(data);
            else
            if (OptionMenu == 2 && data.getOrderId().contains(keywordsearch.getText()))
                filterData.add(data);
            else
            if (OptionMenu == 3 && data.getStatus().contains(keywordsearch.getText()))
                filterData.add(data);
            else
            if (OptionMenu == 4 && data.getDesiredDeliveryDate().contains(keywordsearch.getText()))
                filterData.add(data);
            else
            if (OptionMenu > 4) {
                for (var item : data.getList()) {
                    if (OptionMenu == 5 && item.getItemId().contains(keywordsearch.getText())) {
                        filterData.add(data);
                        break;
                    }
                    else
                    if (OptionMenu == 6 && item.getItemName().contains(keywordsearch.getText())) {
                        filterData.add(data);
                        break;
                    }
                    else
                    if (OptionMenu == 7 && item.getUnit().contains(keywordsearch.getText())) {
                        filterData.add(data);
                        break;
                    }
                    else
                    if (OptionMenu == 8 && item.getQuantityOrdered() == Integer.parseInt(keywordsearch.getText())) {
                        filterData.add(data);
                        break;
                    }
                }
            }
        }

        childContainer.getChildren().clear();
        //In ra phần tử
        for (var orderId : filterData) {
            loadChild(orderId);
        }

    }
}
