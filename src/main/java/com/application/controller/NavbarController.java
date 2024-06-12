package com.application.controller;

import com.application.model.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NavbarController implements Initializable {
    public Button btn__home__view;
    public Button btn__order__lists__item__view;
    public Button btn___InventoryManagementView___view;

    public Button btnQuanLyMatHang;
    public Button btnQuanLyDonHang;

    public Button btnQuanLyNhapHangSale;

    public Button btnLogout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();

        btnLogout.setOnAction(event -> logout());
    }

    private void addListeners() {
        String userRole = checkRole();
        switch (userRole) {
            case "ORDER":
                btn___InventoryManagementView___view.setManaged(false);
                btnQuanLyMatHang.setManaged(false);
                btnQuanLyDonHang.setManaged(false);
                btnQuanLyNhapHangSale.setManaged(false);
                break;
            case "INVENTORY":
                btnQuanLyMatHang.setManaged(false);
                btnQuanLyDonHang.setManaged(false);
                btn__order__lists__item__view.setManaged(false);
                btnQuanLyNhapHangSale.setManaged(false);
                break;
            case "SITE":
                btn___InventoryManagementView___view.setManaged(false);
                btnQuanLyMatHang.setManaged(false);
                btn__order__lists__item__view.setManaged(false);
                btnQuanLyNhapHangSale.setManaged(false);
                break;
            case "SALER":
                btn___InventoryManagementView___view.setManaged(false);
                btnQuanLyDonHang.setManaged(false);
                btn__order__lists__item__view.setManaged(false);
                break;
        }


        btn__home__view.setOnAction(event -> onHomeView());
        btn__order__lists__item__view.setOnAction(event -> onOrderListsItemView());
        btnQuanLyNhapHangSale.setOnAction(event -> onOrderListsItemSaleView());
        btn___InventoryManagementView___view.setOnAction(event -> onInventoryManagementView());
    }

    private void onHomeView() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("Home");
    }

    private void onOrderListsItemView() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListsItem");
    }

    private void onOrderListsItemSaleView() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListsItemSale");
    }

    private void onInventoryManagementView() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("InventoryManagementView");
    }

    private String checkRole() {
        return Model.getInstance().getViewFactory().getUser().getRole();
    }

    private void logout() {
        Model.getInstance().getViewFactory().setUser(null);
        Model.getInstance().getViewFactory().showLoginWindow();

        // close main layout
        Stage stage = (Stage) btn__home__view.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().resetHomeView();
    }
}
