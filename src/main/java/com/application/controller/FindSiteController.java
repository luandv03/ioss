package com.application.controller;

import com.application.entity.FindSiteItem;
import com.application.entity.ItemSite;
import com.application.model.Model;
import com.application.subsystemsql.ItemSiteSubsystem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FindSiteController implements Initializable {

    private static FindSiteController instance;

//    private String itemId;

    public String itemId;

    public Button btnAddItemSite;

    public Button btnCancelItemSite;
    public TableView<FindSiteItem> tableView;
    public TableColumn<FindSiteItem, CheckBox> selectedItemColumn;
    public TableColumn<FindSiteItem, Integer> indexColumn;
    public TableColumn<FindSiteItem, String> siteIdColumn;
    public TableColumn<FindSiteItem, String> siteNameColumn;
    public TableColumn<FindSiteItem, String> quantityColumn;
    public TableColumn<FindSiteItem, String> desiredDeliveryByShipDateColumn;
    public TableColumn<FindSiteItem, String> desiredDeliveryByAirDateColumn;
    public TableColumn<FindSiteItem, TextField> quantityOrderedColumn;
    public TableColumn<FindSiteItem, MenuButton> deliveryTypeColumn;

    ObservableList<FindSiteItem> listItem;

    public Button btnBackOrderListItemView;

    private List<FindSiteItem> findSiteItems = new ArrayList<>();

    public FindSiteController(String itemId) {
        this.itemId = itemId;
    }

    public static FindSiteController getInstance(String itemId) throws SQLException {
        if (instance == null) {
            System.out.println("create controller");
            instance = new FindSiteController(itemId);
        }
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            findItemSiteByItemId(this.itemId);
            renderListSite();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        btnBackOrderListItemView.setOnAction(event -> backOrderListItemView());
        btnAddItemSite.setOnAction(event -> getSelectedItems());

        btnCancelItemSite.setOnAction(event -> {
            try {
                findItemSiteByItemId("MH002");
                renderListSite();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void backOrderListItemView() {
        Model.getInstance().getViewFactory().resetFindSiteView();
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListItem");
    }

    public void renderListSite() throws SQLException {
        System.out.println("Re-render");

        // Thiết lập dữ liệu cho TableView
        listItem = FXCollections.observableArrayList(
               findSiteItems
        );

        indexColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(listItem.indexOf(param.getValue()) + 1));
        siteIdColumn.setCellValueFactory(new PropertyValueFactory<FindSiteItem, String>("siteId"));
        siteNameColumn.setCellValueFactory(new PropertyValueFactory<FindSiteItem, String>("siteName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<FindSiteItem, String>("quantity"));
        desiredDeliveryByShipDateColumn.setCellValueFactory(new PropertyValueFactory<FindSiteItem, String>("desiredDeliveryByShipDate"));
        desiredDeliveryByAirDateColumn.setCellValueFactory(new PropertyValueFactory<FindSiteItem, String>("desiredDeliveryByAirDate"));

        tableView.setItems(listItem);

        selectedItemColumn.setCellValueFactory(param -> {
            CheckBox checkBox = new CheckBox();

            checkBox.setOnAction(event -> {
                FindSiteItem selectedItem = param.getValue();
                if (checkBox.isSelected()) {
                    System.out.println("Checked " + selectedItem.getSiteId());
                    selectedItem.getSelectedSite().setSelected(true);
                    selectedItem.getQuantityOrdered().setDisable(false);
                    selectedItem.getDeliveryType().setDisable(false);
                } else {
                    System.out.println("UnCheck");
                    selectedItem.getQuantityOrdered().setDisable(true);
                    selectedItem.getDeliveryType().setDisable(true);
                }
            });

            return new SimpleObjectProperty<>(checkBox);
        });

        quantityOrderedColumn.setCellValueFactory(new PropertyValueFactory<FindSiteItem, TextField>("quantityOrdered"));

        deliveryTypeColumn.setCellValueFactory(new PropertyValueFactory<FindSiteItem, MenuButton>("deliveryType"));
    }

    // Phương thức để lấy ra dữ liệu của các hàng được chọn
    public void getSelectedItems() {
        List<FindSiteItem> selectedItems = new ArrayList<>();

        for (FindSiteItem item : listItem) {
            if (item.getSelectedSite().isSelected()) {
                selectedItems.add(item);
                System.out.println(item.getSiteId());
                System.out.println(item.getSiteName());
                System.out.println(item.getQuantityOrdered().getText());
                System.out.println(item.getDeliveryType().getValue());
            }
        }

    }

    public String getItemId() {
        return itemId;
    }

    public void findItemSiteByItemId(String itemId) throws SQLException {
        System.out.println("Update table "+ itemId);
        findSiteItems = new ArrayList<>();

        ItemSiteSubsystem itemSiteSubsystem = new ItemSiteSubsystem();
           List<ItemSite> listItemSite = itemSiteSubsystem.findItemSiteByItemId(itemId);

           for (ItemSite item : listItemSite) {
               findSiteItems.add(new FindSiteItem(item.getSiteId(), item.getSiteName(), item.getQuantity(), item.getDesiredDeliveryByShipDate(), item.getDesiredDeliveryByAirDate()));
           }

    }

}
