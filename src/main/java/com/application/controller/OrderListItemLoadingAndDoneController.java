package com.application.controller;

import com.application.entity.FlowHolder;
import com.application.entity.OrderItem;
import com.application.entity.OrderItemLoadingAndDone;
import com.application.entity.OrderListItem;
import com.application.model.Model;
import com.application.subsystemsql.OrderItemLoadAndDoneSubsytem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Flow;

public class OrderListItemLoadingAndDoneController implements Initializable {
    public Label MaDanhSach;
    public Label TrangThaiDanhSach;
    public Button btnBackOrderListsItemView;
    public Button btnViewCorrespondingListOrderItemSite;
    public TableView<OrderItemLoadingAndDone> tableView;
    public TableColumn<OrderItemLoadingAndDone, Integer> idColumn;
    public TableColumn<OrderItemLoadingAndDone, String> itemIdColumn;
    public TableColumn<OrderItemLoadingAndDone, String> itemNameColumn;
    public TableColumn<OrderItemLoadingAndDone, String> unitColumn;
    public TableColumn<OrderItemLoadingAndDone, Integer> totalQuantityColumn;
    public TableColumn<OrderItemLoadingAndDone, Integer> revicedQuantityColumn;
    public TableColumn<OrderItemLoadingAndDone, Integer> orderedQuantityColumn;
    public TableColumn<OrderItemLoadingAndDone, String> desiredDeliveryDateColumn;
    public TableColumn<OrderItemLoadingAndDone, HBox> viewColumn;
    ArrayList<OrderItemLoadingAndDone> orderData;

    MenuItem filterMaMatHang = new MenuItem("Tìm theo mã mặt hàng");
    MenuItem filterTenMatHang = new MenuItem("Tìm theo tên mặt hàng");
    MenuItem filterDonVi = new MenuItem("Tìm theo đơn vị");
    MenuItem filterSoLuong = new MenuItem("Tìm theo số lượng");
    MenuItem filterNgayMongMuonNhan = new MenuItem("Tìm theo ngày mong muốn nhận");
    public MenuButton menuButton;
    public TextField keywordsearch;
    public Button btn_Search;

    private String[] menuItemLabel = {
            "Tìm theo mã mặt hàng",
            "Tìm theo tên mặt hàng",
            "Tìm theo đơn vị",
            "Tìm theo số lượng",
            "Tìm theo ngày mong muốn nhận"
    };

    private static int OptionMenu = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBackOrderListsItemView.setOnAction(event -> backOrderListsItemView());
        btnViewCorrespondingListOrderItemSite.setOnAction(actionEvent -> viewCorrespondingListOrderItemSite());

        btn_Search.setOnAction(actionEvent->filterOrderData());
        MenuItem[] filter = new MenuItem[menuItemLabel.length];
        for (int i = 0; i<menuItemLabel.length; i++) {
            final int index = i;
            filter[i] = new MenuItem(menuItemLabel[i]);
            filter[i].setOnAction(event -> {
                filter[OptionMenu].setStyle("-fx-background-fill:black");
                OptionMenu = index;
                filter[index].setStyle("-fx-text-fill:purple");
            });
            menuButton.getItems().add(filter[i]);
        }

        Reload();
        setValue(orderData);
    }

    public void Reload()
    {
        MaDanhSach.setText((FlowHolder.flowHolder.getOrderListItemId()));
        TrangThaiDanhSach.setText(FlowHolder.flowHolder.getStatus());
        switch (TrangThaiDanhSach.getText())
        {
            case "loading": TrangThaiDanhSach.getStyleClass().add("label-delivering"); break;
            case "pending":TrangThaiDanhSach.getStyleClass().add("label-pending"); break;
            case "done":TrangThaiDanhSach.getStyleClass().add("label-done"); break;
            default:
        }

        try {
            getAllOrderItemByListId(FlowHolder.flowHolder.getOrderListItemId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setValue(ArrayList<OrderItemLoadingAndDone> data)
    {
        ObservableList<OrderItemLoadingAndDone> orderItems = FXCollections.observableArrayList(
                data
        );

        tableView.getItems().clear();
        idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(orderItems.indexOf(param.getValue()) + 1));
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<OrderItemLoadingAndDone, String>("itemId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<OrderItemLoadingAndDone, String>("itemName"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<OrderItemLoadingAndDone, String>("unit"));
        totalQuantityColumn.setCellValueFactory(new PropertyValueFactory<OrderItemLoadingAndDone, Integer>("totalQuantity"));
        orderedQuantityColumn.setCellValueFactory(new PropertyValueFactory<OrderItemLoadingAndDone, Integer>("orderedQuantity"));
        revicedQuantityColumn.setCellValueFactory(new PropertyValueFactory<OrderItemLoadingAndDone, Integer>("revicedQuantity"));
        desiredDeliveryDateColumn.setCellValueFactory(new PropertyValueFactory<OrderItemLoadingAndDone, String>("desiredDeliveryDate"));

        tableView.setItems(orderItems);

        viewColumn.setCellValueFactory(param -> {

            HBox hbox = new HBox();
            Button button = new Button("Xem chi tiết");
            button.getStyleClass().add("view__button");
            button.setOnAction(event -> {

                OrderItem selectedItem = tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    // Thực hiện logic hiển thị chi tiết ở đây
                    System.out.println("Xem chi tiết đơn hàng: " + selectedItem.getItemId());

                    //Thực hiện update flow
                    FlowHolder.flowHolder.UpdateId(selectedItem.getItemId(),"",1);
                }

                viewCorrespondingListOrderItemSite();
            });

            hbox.getChildren().add(button);

            ///Nếu số lượng đã đặt nhỏ hơn số lượng cần đặt
            if (param.getValue().getOrderedQuantity() < param.getValue().getTotalQuantity())
            {
                Button timSiteBtn = new Button("Tìm Site");
                timSiteBtn.getStyleClass().add("view__button");

                //thêm action ở đây...

                hbox.getChildren().add(timSiteBtn);
            }

            return new SimpleObjectProperty<>(hbox);
        });
    }

    public void backOrderListsItemView() {
        FlowHolder.flowHolder.UpdateId("","",0);
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListsItem");
    }

    public void viewCorrespondingListOrderItemSite() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("CorrespondingListOrderItemSite");
    }

    public void getAllOrderItemByListId(String listId) throws SQLException {
        orderData = new ArrayList<>();
        OrderItemLoadAndDoneSubsytem sub = new OrderItemLoadAndDoneSubsytem();
        orderData.addAll(sub.getAllOrderItemByListId(listId));
    }

    public void filterOrderData() {
        ArrayList<OrderItemLoadingAndDone> filterData = new ArrayList<>();
        for (var data: orderData) {
            if (keywordsearch.getText().isEmpty() || keywordsearch.getText().isBlank() || keywordsearch.getText() == null)
                filterData.add(data);
            else
            if (OptionMenu == 0 && data.getItemId().contains(keywordsearch.getText()))
                filterData.add(data);
            else
            if (OptionMenu == 1 && data.getItemName().contains(keywordsearch.getText()))
                filterData.add(data);
            else
            if (OptionMenu == 2 && data.getUnit().contains(keywordsearch.getText()))
                filterData.add(data);
            else
            if (OptionMenu == 3 && data.getOrderedQuantity() == Integer.parseInt(keywordsearch.getText()) )
                filterData.add(data);
            else
            if (OptionMenu == 4 && data.getDesiredDeliveryDate().contains(keywordsearch.getText()))
                filterData.add(data);
        }

        setValue(filterData);
    }
}
