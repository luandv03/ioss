package com.application.controller;

import com.application.entity.Item;
import com.application.entity.OrderItem;
import com.application.entity.Item;
import com.application.model.Model;
import com.application.subsystemsql.ItemSubsystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ItemSearchResultController implements Initializable {

    private String searchValue;
    public Label searchValueText;
    public Button closeBtn;

    public TableView<Item> tableView;
    public TableColumn<Item, String> itemIdColumn;
    public TableColumn<Item, String> itemNameColumn;
    public TableColumn<Item, String> unitColumn;
    public TableColumn<Item, Button> addBtn;

    ObservableList<Item> tableData;

    private List<Item> listItem = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchValueText.setText('"' + searchValue + '"');

        closeBtn.setOnAction(event -> closeModal());

        renderTable();
    }

    public void renderTable() {
        tableData = FXCollections.observableArrayList(listItem);

        itemIdColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("unit"));

        addBtn.setCellFactory(column -> {
            return new TableCell<Item, Button>() {
                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        Button btn = new Button("Thêm");
                        btn.getStyleClass().add("view__item__button");


                        // Lưu trữ index của dòng hiện tại vào TableCell
                        int rowIndex = getIndex();

                        btn.setOnAction(event -> {

                            Item selectedItem = getTableView().getItems().get(rowIndex);

                            if (selectedItem != null) {
                                System.out.println(selectedItem.getItemId());
                                String itemId = selectedItem.getItemId();
                                String itemName = selectedItem.getItemName();
                                String unit = selectedItem.getUnit();

                                Item i = new Item();

                                for (Item it: listItem) {
                                    if (it.getItemId().equals(itemId)) {
                                        i = it;
                                        break;
                                    }
                                }

                                showBoxAdd(i);

                            }

                        });

                        setGraphic(btn);
                    }
                }
            };
        });

        tableView.setItems(tableData);
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public void searchItemByName(String value) throws SQLException {
        // to do
        ItemSubsystem its = new ItemSubsystem();
        listItem = its.getItemByName(value);
    }

    public void closeModal() {
        Stage stage = (Stage) searchValueText.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
    }

    public void showBoxAdd(Item it) {
        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Mã mặt hàng: "), 0, 0);
        gridPane.add(new Label("Số lượng: "), 0, 1);
        gridPane.add(new Label("Ngày nhận mong muốn: "), 0, 2);

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        Text itemIdText = new Text(it.getItemId());
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

            // Thực hiện các xử lý tiếp theo với dữ liệu đã lấy
            System.out.println("Quantity: " + quantity);
            System.out.println("Desired Delivery Date: " + desiredDeliveryDate);

            OrderItem o = new OrderItem(it.getItemId(), it.getItemName(), it.getUnit(), Integer.parseInt(quantity), desiredDeliveryDate);

            try {
                OrderListItemCreateController.getInstance().addOrderItem(o);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Đóng Dialog (nếu cần)
            Model.getInstance().getViewFactory().closeStage((Stage) saveBtn.getScene().getWindow());
        });

        gridPane.add(itemIdText, 1, 0);
        gridPane.add(quantityText, 1, 1);
        gridPane.add(d, 1, 2);
        gridPane.add(saveBtn, 1, 3);
        gridPane.add(cancelBtn, 1, 4);


        Dialog<Void> dialog = new Dialog<>();

        dialog.setTitle("Thêm sản phẩm vào danh sách");

        cancelBtn.setOnAction(event -> Model.getInstance().getViewFactory().closeStage((Stage) cancelBtn.getScene().getWindow()));

       dialog.getDialogPane().setContent(gridPane);

       dialog.showAndWait();
    }

}
