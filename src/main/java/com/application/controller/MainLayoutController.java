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
            System.out.println(newVal);
            switch (newVal) {
                case "OrderListsItem"
                        -> content__parent.setCenter(Model.getInstance().getViewFactory().getOrderListsItemView());
                case "OrderListsItemSale"
                        -> content__parent.setCenter(Model.getInstance().getViewFactory().getOrderListsItemSaleView());
                case "OrderListItem"
                        -> content__parent.setCenter(Model.getInstance().getViewFactory().getOrderListItemView());
                case "OrderListItemCreate"
                        -> content__parent.setCenter(Model.getInstance().getViewFactory().getOrderListItemCreateView());
                case "FindSite"
                        -> content__parent.setCenter(Model.getInstance().getViewFactory().getFindSiteView());
                case "OrderListItemLoadingAndDone"
                        -> content__parent.setCenter(Model.getInstance().getViewFactory().getOrderListItemLoadingAndDone());
                case "CorrespondingListOrderItemSite"
                        -> content__parent.setCenter(Model.getInstance().getViewFactory().getCorrespondingListOrderItemSite());
                case "InventoryManagementView" -> content__parent.setCenter(Model.getInstance().getViewFactory().getInventoryManagementView());
                case "CheckOrders" -> content__parent.setCenter(Model.getInstance().getViewFactory().getCheckOrdersView());
                case "ReportView" -> content__parent.setCenter(Model.getInstance().getViewFactory().getReportView());
                case "OrderListInActive" -> content__parent.setCenter(Model.getInstance().getViewFactory().getOrderListInActiveView());
                case "OrderDetail"
                        -> content__parent.setCenter(Model.getInstance().getViewFactory().getOrderDetailView());
                default
                        -> content__parent.setCenter(Model.getInstance().getViewFactory().getHomeView());
            }
        }));
    }

}
