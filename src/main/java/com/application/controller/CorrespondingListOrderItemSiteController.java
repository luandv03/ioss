package com.application.controller;

import com.application.entity.OrderItemSite;
import com.application.entity.CorrespondOrder;
import com.application.model.Model;
import eu.hansolo.tilesfx.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CorrespondingListOrderItemSiteController implements Initializable {

    public VBox childContainer;
    public Button btnBackToSite;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TestDataGenerate();

        btnBackToSite.setOnAction(event -> backOrderListsItemView());
    }

    private void TestDataGenerate() {
        //Tạo mới
        ObservableList<OrderItemSite> list = FXCollections.observableArrayList(
                new OrderItemSite("MH005","Dieu Hoa","Chiec",5,"25/5/2024"),
                new OrderItemSite("MH006","Thit Bo","Cai",30,"28/5/2024"),
                new OrderItemSite("MH007","Roboto","Cai",90,"29/5/2024")
        );

        ObservableList<OrderItemSite> list2 = FXCollections.observableArrayList(
                new OrderItemSite("MH005","Dieu Hoa","Chiec",15,"25/5/2024"),
                new OrderItemSite("MH008","May Loc Nuoc Pikachu","Cai",10,"28/5/2024"),
                new OrderItemSite("MH007","Roboto","Cai",10,"29/5/2024")
        );


        loadChild("S0005", "taobao.com", "DH001","delevering", "25/05/2024", list);
        loadChild("S0006", "amazon.com", "DH0002", "canceled", "#####", list2);
    }
    private void loadChild(CorrespondOrder list) {
        loadChild(list.getSiteId(),list.getSiteName(),list.getCorrespondOrderId(), list.getStatus(), list.getDesiredDeliveryDate(), list.getList());
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
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListItem_DaXuLi");
    }
}
