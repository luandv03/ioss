package com.application.controller;

import com.application.model.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OrderListItemController implements Initializable {
    public Button btnBackOrderListsItemView;

    public Button btnFindSite;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBackOrderListsItemView.setOnAction(event -> {
            try {
                findSite("MH001");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        btnFindSite.setOnAction(event -> {
            try {
                findSite("MH001");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void backOrderListsItemView() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListsItem");
    }

    public void findSite(String itemId) throws SQLException {
        FindSiteController findSiteController =  FindSiteController.getInstance(itemId);
        findSiteController.findItemSiteByItemId(itemId);
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("FindSite"); // thực hiện load FXML của file FindSite.FXML
    }
}
