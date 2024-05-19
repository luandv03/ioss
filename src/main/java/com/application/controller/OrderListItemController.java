package com.application.controller;

import com.application.model.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderListItemController implements Initializable {
    @FXML
    Button btnBackOrderListsItemView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBackOrderListsItemView.setOnAction(event -> backOrderListsItemView());
    }

    public void backOrderListsItemView() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListsItem");
    }
}
