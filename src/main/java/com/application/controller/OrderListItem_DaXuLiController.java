package com.application.controller;

import com.application.model.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderListItem_DaXuLiController implements Initializable {
    public Button btnBackOrderListsItemView;
    public Button btnViewCorrespondingListOrderItemSite;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBackOrderListsItemView.setOnAction(event -> backOrderListsItemView());

        ///Xem danh sách các đơn hàng tương ứng
        btnViewCorrespondingListOrderItemSite.setOnAction(actionEvent -> viewCorrespondingListOrderItemSite());
    }

    public void backOrderListsItemView() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListsItem");
    }

    public void viewCorrespondingListOrderItemSite() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("CorrespondingListOrderItemSite");
    }
}
