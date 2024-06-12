package com.application.controller;

import com.application.entity.*;
import com.application.model.Model;
import com.application.subsystemsql.OrderItemSiteSubsystem;
import com.application.subsystemsql.OrderSubsystem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CheckOrdersController implements Initializable {
    Notifications notificationBuilder;
    Node graphic;
    @FXML
    public TableView<OrderItemSite> tableView;
    @FXML
    public TableColumn<OrderItemSite,Integer> idColumn;
    @FXML
    Label StatusOrder;
    @FXML
    Label OrderID;
    public Button btnBackPrevPage;
    public Button btnSave;
    @FXML
    public TableColumn<OrderItemSite,String> itemCodeColumn;
    @FXML
    public TableColumn<OrderItemSite,String> itemNameColumn;
    @FXML
    public TableColumn<OrderItemSite,String> unitColumn;
    @FXML
    public TableColumn<OrderItemSite,Integer> quantityColumn;
    @FXML
    public TableColumn<OrderItemSite,Integer> quantityOrderColumn;

    public TableColumn<OrderItemSite, Button> editColumn;

    ObservableList<OrderItemSite> orderLists;
    List<OrderItemSite> listOrderItemSite = new ArrayList<>();

    private static CheckOrdersController instance ;
    private String orderId;
    private String status;

    public static CheckOrdersController getInstance() {
        if (instance == null) {
            instance = new CheckOrdersController();
        }
        return instance;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set orderId
        OrderID.setText(this.orderId);
        StatusOrder.setText(this.status);

        btnBackPrevPage.setOnAction(event -> {
            try {
                goBack();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        btnSave.setOnAction(event -> {
            try {
                save();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        // init table view data
        orderLists = FXCollections.observableArrayList(
                listOrderItemSite
        );

        idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(orderLists.indexOf(param.getValue()) + 1));
        itemCodeColumn.setCellValueFactory(new PropertyValueFactory<OrderItemSite,String>("itemId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<OrderItemSite,String>("itemName"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<OrderItemSite,String>("unit"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<OrderItemSite,Integer>("quantityOrdered"));
        quantityOrderColumn.setCellValueFactory(new PropertyValueFactory<OrderItemSite,Integer>("recievedQuantity"));

        editColumn.setCellFactory(column -> {
            return new TableCell<OrderItemSite, Button>() {
                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        Button btn = new Button("Cập nhật");
                        btn.getStyleClass().add("view__item__button");

                        // Lưu trữ index của dòng hiện tại vào TableCell
                        int rowIndex = getIndex();

                        btn.setOnAction(event -> {

                            OrderItemSite selectedItem = getTableView().getItems().get(rowIndex);

                            if (selectedItem != null) {
                                TextInputDialog dialog = new TextInputDialog();
                                dialog.setTitle("Cập nhật mặt hàng đã nhận");
                                dialog.setHeaderText(null);
                                dialog.setContentText("Số lượng nhận:");
                                Optional<String> result = dialog.showAndWait();

                                result.ifPresent(quantity -> {
                                    listOrderItemSite.stream()
                                            .filter(p -> p.getItemId().equals(selectedItem.getItemId()))
                                            .findFirst().ifPresent(o -> {
                                                o.setRecievedQuantity(Integer.parseInt(quantity));
                                                selectedItem.setRecievedQuantity(Integer.parseInt(quantity));
                                                tableView.refresh();
                                            });
                                });
                            }

                        });

                        setGraphic(btn);
                    }
                }
            };
        });

        tableView.setItems(orderLists);
    }

    public void goBack() throws SQLException {
        instance = null;
        Model.getInstance().getViewFactory().resetCheckOrdersView();
        // reset data prev page
        InventoryManagementController.getInstance().getListOrder();
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("InventoryManagementView");
    }

    public void getListOrderSiteItem(String orderId) throws SQLException {
        OrderItemSiteSubsystem orderItemSiteSubsystem = new OrderItemSiteSubsystem();

        listOrderItemSite = orderItemSiteSubsystem.getOrderItemSiteByOrderId(orderId);
    }

    public void save() throws SQLException {
        OrderItemSiteSubsystem sb = new OrderItemSiteSubsystem();

        for (OrderItemSite o : listOrderItemSite) {
//            System.out.println(o.getItemId() + ": " + o.getRecievedQuantity());
            sb.updateOrderItemSiteById(this.orderId, o.getItemSiteId(), o.getRecievedQuantity());
        }

        OrderSubsystem os = new OrderSubsystem();
        os.updateOrderStatus(orderId, "done");

        StatusOrder.setText("done");

        graphic = null;
        notification(Pos.TOP_RIGHT, null, "Lưu danh sách thành công!");
        notificationBuilder.show();
    }

    private void notification(Pos pos, Node graphic, String text) {
        notificationBuilder = Notifications.create()
                .title("Thong bao")
                .text(text)
                .graphic(graphic)
                .hideAfter(Duration.seconds(8))
                .position(pos)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        System.out.println("Notification is Clicked");
                    }
                });
    }

}