package com.application.controller;

import com.application.model.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainLayoutController implements Initializable {
    public BorderPane content__parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getSelectedMenuItem().addListener(((observable, oldVal, newVal) -> {
            switch (newVal) {
                case "OrderListsItem" -> content__parent.setCenter(Model.getInstance().getViewFactory().getOrderListsItemView());
                case "OrderListItem" -> content__parent.setCenter(Model.getInstance().getViewFactory().getOrderListItemView());
                case "FindSite" -> content__parent.setCenter(Model.getInstance().getViewFactory().getFindSiteView());
                case "InventoryManagementView" -> content__parent.setCenter(Model.getInstance().getViewFactory().getInventoryManagementView());
                case "CheckOrders" -> content__parent.setCenter(Model.getInstance().getViewFactory().getCheckOrdersView());
                case "ReportView" -> content__parent.setCenter(Model.getInstance().getViewFactory().getReportView());
                default -> content__parent.setCenter(Model.getInstance().getViewFactory().getHomeView());
            }
        }));
    }

}
