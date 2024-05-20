package com.application.controller;

import com.application.model.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class FindSiteController implements Initializable {

    public Button btnBackOrderListItemView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBackOrderListItemView.setOnAction(event -> backOrderListItemView());
    }

    public void backOrderListItemView() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListItem");
    }
}
