package com.application.controller;
import com.application.entity.OrderItem;
import com.application.entity.OrderItemDetail;
import com.application.entity.OrderItemPending;
import com.application.model.Model;
import com.application.subsystemsql.OrderSubsystem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderDetailController implements Initializable  {
    private static OrderDetailController instance;
    private String orderListItemId;
    private String getOrderListItemStatus;
    private String orderId;
    private String orderStatus;
    private String siteId;
    private String siteName;

    public Text orderListItemIdElement;
    public Text orderListItemStatusElement;
    public Text orderIdElement;
    public Text orderStatusElement;
    public Text siteIdElement;
    public Text siteNameElement;

    public Button requestOrderBtn;
    public Button backToPrevPageBtn;

    public TableView<OrderItemPending> tableView;

    public TableColumn<OrderItemPending, Integer> idColumn;

    public TableColumn<OrderItemPending, String> orderItemIdColumn;

    public TableColumn<OrderItemPending, String> orderItemNameColumn;
    public TableColumn<OrderItemPending, String> orderItemUnitColumn;

    public TableColumn<OrderItemPending, Integer> orderItemQuantityColumn;

    public TableColumn<OrderItemPending, String> orderItemDesiredDeliveryDate;

    public TableColumn<OrderItemPending, Integer> orderItemPickedQuantity;

    public TableColumn<OrderItemPending, Integer> orderItemRemainQuantity;


    public TableColumn<OrderItemPending, Button> findSiteColumn;
    private List<OrderItemPending> listOrderItem = new ArrayList<>();

    ObservableList<OrderItemPending> orderItemDetail;

    public OrderDetailController() {

    }

    public static OrderDetailController getInstance() {
        if (instance == null) {
            instance = new OrderDetailController();
        }
        return instance;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderIdElement.setText(orderId);
        orderStatusElement.setText(orderStatus);
        siteIdElement.setText(siteId);
        siteNameElement.setText(siteName);

        requestOrderBtn.setOnAction((event) -> handleOrder());

        backToPrevPageBtn.setOnAction((event) -> backToPrevPage());

        renderTable();
    }

    public void renderTable() {
        // Thiết lập dữ liệu cho TableView
        orderItemDetail = FXCollections.observableArrayList(listOrderItem);
        idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(orderItemDetail.indexOf(param.getValue()) + 1));
        orderItemIdColumn.setCellValueFactory(new PropertyValueFactory<OrderItemPending, String>("itemId"));
        orderItemNameColumn.setCellValueFactory(new PropertyValueFactory<OrderItemPending, String>("itemName"));
        orderItemUnitColumn.setCellValueFactory(new PropertyValueFactory<OrderItemPending, String>("unit"));
        orderItemQuantityColumn.setCellValueFactory(new PropertyValueFactory<OrderItemPending, Integer>("quantityOrdered"));
        orderItemDesiredDeliveryDate.setCellValueFactory(new PropertyValueFactory<OrderItemPending, String>("desiredDeliveryDate"));
        orderItemPickedQuantity.setCellValueFactory(new PropertyValueFactory<OrderItemPending, Integer>("selectedQuantity"));
        orderItemRemainQuantity.setCellValueFactory(new PropertyValueFactory<OrderItemPending, Integer>("pendingQuantity"));

        tableView.setItems(orderItemDetail);

        findSiteColumn.setCellFactory(column -> {
            return new TableCell<OrderItemPending, Button>() {
                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        Button btn = new Button("Tìm site");
                        btn.getStyleClass().add("view__item__button");

                        // Lưu trữ index của dòng hiện tại vào TableCell
                        int rowIndex = getIndex();

                        btn.setOnAction(event -> {

                            OrderItemPending selectedItem = getTableView().getItems().get(rowIndex);

                            if (selectedItem != null) {
                                try {
                                    System.out.println(selectedItem.getItemId());
                                    String itemId = selectedItem.getItemId();
                                    String itemName = selectedItem.getItemName();
                                    String unit = selectedItem.getUnit();
                                    int quantity = selectedItem.getQuantityOrdered();
                                    String desiredDeliveryDate = selectedItem.getDesiredDeliveryDate();
                                    int selectedQuantity = selectedItem.getSelectedQuantity();

                                    OrderItem orderItem = new OrderItem(itemId, itemName, unit, quantity, desiredDeliveryDate);
                                    findSite(orderItem, selectedQuantity);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        });

                        setGraphic(btn);
                    }
                }
            };
        });
    }

    public void renderContent() {
        orderIdElement.setText(orderId);
        orderStatusElement.setText(orderStatus);
        siteIdElement.setText(siteId);
        siteNameElement.setText(siteName);
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;

    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setOrderListItemId(String orderListItemId) {
        this.orderListItemId = orderListItemId;
    }

    public void setGetOrderListItemStatus(String getOrderListItemStatus) {
        this.getOrderListItemStatus = getOrderListItemStatus;
    }

    public void getOrderItemDetail(String orderId) throws SQLException {
        OrderSubsystem orderSubsystem = new OrderSubsystem();
        listOrderItem = orderSubsystem.getOrderCanceled(orderId);
    }

    public void findSite(OrderItem orderItem, int selectedQuantity) throws SQLException {
        FindSiteController findSiteController =  FindSiteController.getInstance(orderItem.getItemId());
        findSiteController.findItemSiteByItemId("OL001", orderItem, selectedQuantity);
        findSiteController.setPreView("OrderDetail");
        findSiteController.setOrderParentId(orderId);
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("FindSite"); // thực hiện load FXML của file FindSite.FXML
    }

    public void handleOrder() {
        Scene scene = null;
        AnchorPane orderListInActiveElement = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/application/fxml/" + "OrderListInActive" + ".fxml"));

//            OrderListInActiveController ctl = fxmlLoader.getController();
            OrderListInActiveController ctl = new OrderListInActiveController();

            ctl.setOrderTypePage("OrderCanceled");
            ctl.setOrderListItemId("OL001");
            ctl.setOrderParentId(this.orderId);
            ctl.getListOrder(this.orderListItemId, this.orderId);

            fxmlLoader.setController(ctl);

            orderListInActiveElement = fxmlLoader.load();
            scene = new Scene(orderListInActiveElement);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

    }

    public void backToPrevPage() {
        Model.getInstance().getViewFactory().resetOrderDetail();
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("CorrespondingListOrderItemSite");
        // quay lại trạng trước đó
    }

}
