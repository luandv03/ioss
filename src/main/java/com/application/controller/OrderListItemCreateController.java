package com.application.controller;

import com.application.entity.FlowHolder;
import com.application.entity.OrderItem;
import com.application.entity.OrderListItem;
import com.application.model.Model;
import com.application.subsystemsql.OrderItemSubsystem;
import com.application.subsystemsql.OrderListItemSubsystem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderListItemCreateController implements Initializable {
    private static OrderListItemCreateController instance;
    Notifications notificationBuilder;
    Node graphic;
    public Button btnBackOrderListsItemSaleView;

    public Button btnCreateOrderListItem;

    public Button btnSearch;
    public TextField searchValueInput;

    public TableView<OrderItem> tableView;
    public TableColumn<OrderItem, Integer> indexCol;
    public TableColumn<OrderItem, String> itemIdCol;
    public TableColumn<OrderItem, String> itemNameCol;
    public TableColumn<OrderItem, String> unitCol;
    public TableColumn<OrderItem, String>  desiredDeliveryDateCol;
    public TableColumn<OrderItem, Integer> quantityOrderedCol;

    public TableColumn<OrderItem, HBox> actionColumn;

    ObservableList<OrderItem> orderItemLists;

    List<OrderItem> listDataItem = new ArrayList<>();

    public OrderListItemCreateController() {

    }

    public static OrderListItemCreateController getInstance() throws SQLException {
        if (instance == null) {
            instance = new OrderListItemCreateController();
        }
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBackOrderListsItemSaleView.setOnAction(event -> {
            try {
                backOrderListsItemView();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        renderListOrderItem();

        btnCreateOrderListItem.setOnAction(event -> {
            try {
                saveListOrderItem();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        btnSearch.setOnAction(event -> searchItemByName(searchValueInput.getText()));

    }

    public void renderListOrderItem() {
        // Thiết lập dữ liệu cho TableView
        orderItemLists = FXCollections.observableArrayList(listDataItem);

        indexCol.setCellValueFactory(param -> new SimpleObjectProperty<>(orderItemLists.indexOf(param.getValue()) + 1));
        itemIdCol.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("itemId"));
        itemNameCol.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("itemName"));
        unitCol.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("unit"));
        desiredDeliveryDateCol.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("desiredDeliveryDate"));
        quantityOrderedCol.setCellValueFactory(new PropertyValueFactory<OrderItem, Integer>("quantityOrdered"));

        // cach moi
        actionColumn.setCellFactory(column -> {
            return new TableCell<OrderItem, HBox>() {
                @Override
                protected void updateItem(HBox item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        Button editBtn = new Button("Sửa");
                        Button deleteBtn = new Button("Xóa");

                        HBox hbox = new HBox(editBtn, deleteBtn);
                        hbox.setSpacing(2);

                        // Lưu trữ index của dòng hiện tại vào TableCell
                        int rowIndex = getIndex();

                        editBtn.setOnAction(event -> {
                            // Truy cập index của dòng hiện tại
                            System.out.println("Index của dòng: " + rowIndex);

                            OrderItem selectedItem = getTableView().getItems().get(rowIndex);


                            if (selectedItem != null) {
                                // Thực hiện logic hiển thị chi tiết 1 dsmhcd ở đây
                                String itemId = selectedItem.getItemId();

                                OrderItem o = new OrderItem();

                                for (OrderItem od: listDataItem) {
                                    if (od.getItemId().equals(itemId)) {
                                        o = od;
                                        break;
                                    }
                                }

                                showModalUpdateOrderItem(o);
                            }
                        });

                        deleteBtn.setOnAction(event -> {
                            // Truy cập index của dòng hiện tại
                            OrderItem selectedItem = getTableView().getItems().get(rowIndex);

                            if (selectedItem != null) {
                                // Thực hiện logic hiển thị chi tiết 1 dsmhcd ở đây
                                String itemId = selectedItem.getItemId();

                                deleteOrderItemById(itemId);
                            }
                        });

                        setGraphic(hbox);
                    }
                }
            };
        });

        tableView.setItems(orderItemLists);
    }

    public void backOrderListsItemView() throws SQLException {
        Model.getInstance().getViewFactory().resetOrderListItemView();
        OrderListsItemSaleController.getInstance().getListOrderItemAll();
        OrderListsItemSaleController.getInstance().renderTable();
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListsItemSale");
    }

    public void searchItemByName(String value) {
        Scene scene = null;
        AnchorPane itemSearchResult = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/application/fxml/" + "ItemSearchResult" + ".fxml"));

            ItemSearchResultController ctl = new ItemSearchResultController();

            ctl.setSearchValue(value);
            ctl.searchItemByName(value);

            fxmlLoader.setController(ctl);

            itemSearchResult = fxmlLoader.load();
            scene = new Scene(itemSearchResult);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public void addOrderItem(OrderItem orderItem) {

        for (OrderItem o: listDataItem){
            if (o.getItemId().equals(orderItem.getItemId())) {
                o.setQuantityOrdered(orderItem.getQuantityOrdered());
                o.setDesiredDeliveryDate(orderItem.getDesiredDeliveryDate());
                renderListOrderItem();
                return;
            }
        }

        listDataItem.add(orderItem);

        renderListOrderItem();
    }

    public void saveListOrderItem() throws SQLException {

        if (listDataItem.isEmpty()) return;

        OrderListItemSubsystem s1 = new OrderListItemSubsystem();
        String orderListItemId = s1.createOrderListItem("pending");

        OrderItemSubsystem s2 = new OrderItemSubsystem();
        for (OrderItem o: listDataItem) {
            s2.createOrderItemSite(o, orderListItemId);
        }

        // to do
        graphic = null;
        notification(Pos.TOP_RIGHT, null, "Tạo danh sách mặt hàng thành công!");
        notificationBuilder.show();

        // reset data ?
        listDataItem = new ArrayList<>();
        tableView.refresh();
        renderListOrderItem();
    }

    public void deleteOrderItemById(String id) {
        listDataItem.removeIf(o -> o.getItemId().equals(id));

        tableView.refresh();
        renderListOrderItem();
    }

    public void updateOrderItemById(OrderItem oldOrderItem, Integer newQuantity, String newDate) {
        for (OrderItem o: listDataItem){
            if (o.getItemId().equals(oldOrderItem.getItemId())) {
                o.setQuantityOrdered(newQuantity);
                o.setDesiredDeliveryDate(newDate);
                renderListOrderItem();
                return;
            }
        }
    }

    public void showModalUpdateOrderItem(OrderItem oldOrderItem) {
        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Mã mặt hàng: "), 0, 0);
        gridPane.add(new Label("Số lượng: "), 0, 1);
        gridPane.add(new Label("Ngày nhận mong muốn: "), 0, 2);

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        Text itemIdText = new Text(oldOrderItem.getItemId());
        TextField quantityText = new TextField();
        DatePicker d = new DatePicker();
        TextField desiredDeliveryDateText = new TextField();
        Button saveBtn = new Button("Lưu");
        Button cancelBtn = new Button("Hủy");


        d.setOnAction(e -> desiredDeliveryDateText.setText(d.getValue().toString()));

        // Thêm sự kiện ActionEvent cho nút "Lưu"
        saveBtn.setOnAction(event -> {
            // Lấy giá trị từ các TextField
            String quantity = quantityText.getText();
            String desiredDeliveryDate = desiredDeliveryDateText.getText();

           updateOrderItemById(oldOrderItem, Integer.parseInt(quantity), desiredDeliveryDate);

            // Đóng Dialog (nếu cần)
            Model.getInstance().getViewFactory().closeStage((Stage) saveBtn.getScene().getWindow());
        });

        gridPane.add(itemIdText, 1, 0);
        gridPane.add(quantityText, 1, 1);
        gridPane.add(d, 1, 2);
        gridPane.add(saveBtn, 1, 3);
        gridPane.add(cancelBtn, 1, 4);


        Dialog<Void> dialog = new Dialog<>();

        dialog.setTitle("Cập nhật sản phẩm thành công");

        cancelBtn.setOnAction(event -> Model.getInstance().getViewFactory().closeStage((Stage) cancelBtn.getScene().getWindow()));

        dialog.getDialogPane().setContent(gridPane);

        dialog.showAndWait();
    }

    private void notification(Pos pos, Node graphic, String text) {
        notificationBuilder = Notifications.create()
                .title("Thong bao")
                .text(text)
                .graphic(graphic)
                .hideAfter(Duration.seconds(30))
                .position(pos)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        System.out.println("Notification is Clicked");
                    }
                });
    }

}
