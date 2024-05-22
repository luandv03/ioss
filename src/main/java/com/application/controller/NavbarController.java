package com.application.controller;

import com.application.model.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class NavbarController implements Initializable {
    public Button btn__home__view;
    public Button btn__order__lists__item__view;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        btn__home__view.setOnAction(event -> onHomeView());
        btn__order__lists__item__view.setOnAction(event -> onOrderListsItemView());
    }

    private void onHomeView() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("Home");
    }

    private void onOrderListsItemView() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListsItem");
    }


}
