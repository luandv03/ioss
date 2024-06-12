package com.application.controller;

import com.application.entity.FindSiteItem;
import com.application.entity.ItemSite;
import com.application.entity.OrderItem;
import com.application.entity.OrderLine;
import com.application.model.Model;
import com.application.subsystemsql.ItemSiteSubsystem;
import com.application.subsystemsql.OrderSubsystem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FindSiteController implements Initializable {


    private String preView; // store previous of page;

    private String orderParentId = null; // đối với đơn hàng bị hủy

    Notifications notificationBuilder;
    Node graphic;

    private boolean flagErrorValidateInput = false;

    private static FindSiteController instance;

    private String orderListItemId;
    private OrderItem orderItem;
    private int selectedQuantity;

    public String itemId;

    public Text orderListItemIdText;

    public Text itemIdText;
    public Text itemNameText;
    public Text unitText;
    public Text quantityText;
    public Text desiredDeliveryDateText;
    public Text selectedQuantityText;

    public Button btnSaveItemSite;

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

    ObservableList<FindSiteItem> items;

    public Button btnBackOrderListItemView;

    private List<FindSiteItem> findSiteItems = new ArrayList<>();
//    private  List<FindSiteItem> selectedSiteItems = new ArrayList<>();

    private List<OrderLine> listOrderLine = new ArrayList<>();

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
            findItemSiteByItemId(this.orderListItemId, this.orderItem, this.selectedQuantity);
            renderListSite();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        btnBackOrderListItemView.setOnAction(event -> {
            System.out.println("Pre page = " + this.preView);
            if (this.preView.equals("OrderListItem")) {
                backOrderListItemView();
            } else if (this.preView.equals("OrderDetail")) {
                backOrderCanceledView();
            }
        });

        btnSaveItemSite.setOnAction(event -> {
            try {
                saveSelectedSites();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        btnCancelItemSite.setOnAction(event -> {

        });

        orderListItemIdText.setText(orderListItemId);
        itemIdText.setText(itemId);
        itemNameText.setText(orderItem.getItemName());
        unitText.setText(orderItem.getUnit());
        quantityText.setText(String.valueOf(orderItem.getQuantityOrdered()));
        desiredDeliveryDateText.setText(orderItem.getDesiredDeliveryDate());
        selectedQuantityText.setText(String.valueOf(selectedQuantity));

    }

    public void backOrderListItemView() {
        instance = null;
        Model.getInstance().getViewFactory().resetFindSiteView();
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderListItem");
    }

    public void backOrderCanceledView() {
        instance = null;
        Model.getInstance().getViewFactory().resetFindSiteView();
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("OrderDetail");
    }

    public void renderListSite() throws SQLException {
        // Thiết lập dữ liệu cho TableView
        listItem = FXCollections.observableArrayList(
               findSiteItems
        );

        indexColumn.setSortable(false);
        siteIdColumn.setSortable(false);
        siteNameColumn.setSortable(false);
        quantityColumn.setSortable(false);
        desiredDeliveryByAirDateColumn.setSortable(false);
        desiredDeliveryByShipDateColumn.setSortable(false);
        deliveryTypeColumn.setSortable(false);

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
                    System.out.println("Checked " + selectedItem.getSiteId() + " for " + selectedItem.getSelectedSite());
                    selectedItem.getSelectedSite().setSelected(true);
                    selectedItem.getQuantityOrdered().setDisable(false);
                    selectedItem.getDeliveryType().setDisable(false);
                } else {
                    System.out.println("UnCheck");
                    // reset checkbox
                    selectedItem.setSelectedSite(new CheckBox());

                    // reset input
                    selectedItem.getQuantityOrdered().setText("");
                    selectedItem.setQuantityOrdered(selectedItem.getQuantityOrdered());
                    selectedItem.getQuantityOrdered().setDisable(true);

                    // reset delivery type
                    selectedItem.getDeliveryType().setValue("Ship");
                    selectedItem.setDeliveryType(selectedItem.getDeliveryType());
                    selectedItem.getDeliveryType().setDisable(true);
                }
            });

            return new SimpleObjectProperty<>(checkBox);
        });

        quantityOrderedColumn.setCellValueFactory(new PropertyValueFactory<FindSiteItem, TextField>("quantityOrdered"));

        quantityOrderedColumn.setCellFactory(column -> {
            return new TableCell<FindSiteItem, TextField>() {
                private TextField textField;
                private Label errorLabel;

                @Override
                protected void updateItem(TextField item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        // Lấy index của dòng hiện tại
                        int rowIndex = getTableRow().getIndex();

                        if (textField == null) {
                            // Lấy giá trị quantity của dòng hiện tại
                            int quantity = listItem.get(rowIndex).getQuantity();

                            String siteId = listItem.get(rowIndex).getSiteId();

                            // Tạo một TextField mới cho mỗi cell
                            textField = listItem.get(rowIndex).getQuantityOrdered();
                            textField.setText(""); // Khoi tao gia tri cho input = ""

                            errorLabel = new Label();
                            errorLabel.setManaged(false);
                            errorLabel.setStyle("-fx-text-fill: red;");

                            // Thêm listener cho sự kiện focusedProperty
                            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                                if (newValue != null && !newValue.equals(oldValue)) { // Kiểm tra nếu focus ra khỏi TextField
                                    try {
                                        int quantityInputCurrent = Integer.parseInt(textField.getText());
                                        if (quantityInputCurrent > quantity) {
                                            errorLabel.setText("Vượt quá số lượng của site " + siteId);
                                            errorLabel.setManaged(true);
                                        } else {
                                            listItem.get(rowIndex).getQuantityOrdered().setText(String.valueOf(quantityInputCurrent));
                                            listItem.get(rowIndex).setQuantityOrdered(listItem.get(rowIndex).getQuantityOrdered());
                                            System.out.println(quantityInputCurrent);
                                            errorLabel.setManaged(false);
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            });
                        } else {
                            textField.setText(String.valueOf(listItem.get(rowIndex).getQuantityOrdered()));
                            textField.setDisable(listItem.get(rowIndex).getQuantityOrdered().isDisabled());
                            errorLabel.setManaged(false); // Reset errorLabel
                        }

                        // Tạo một VBox để chứa TextField và errorLabel
                        VBox vbox = new VBox(textField, errorLabel);
                        vbox.setAlignment(Pos.CENTER);
                        setGraphic(vbox);

                    }
                }
            };
        });

        deliveryTypeColumn.setCellValueFactory(new PropertyValueFactory<FindSiteItem, MenuButton>("deliveryType"));


    }

    // Phương thức để lấy ra dữ liệu của các hàng được chọn
    // Phương thức để lấy ra dữ liệu của các hàng được chọn
    public int getSelectedItems() {
        List<FindSiteItem> selectedItems = new ArrayList<>();
        listOrderLine = new ArrayList<>();

        int selectedQuantityInMoment = 0; // số lượng vừa chọn;

        for (FindSiteItem item : listItem) {
            if (item.getSelectedSite().isSelected()) {
                System.out.println("Site duoc chon: " + item.getSiteId());
                selectedItems.add(item); // lấy dữ liệu để cập nhật db

                String siteId = item.getSiteId();
                String deliveryType = item.getDeliveryType().getValue();

                // validate input is empty
                if (item.getQuantityOrdered().getText().isEmpty()) {
                    graphic = null;
                    notification(Pos.TOP_RIGHT, null, "Số lượng của site " + siteId + "không được để trống !");
                    notificationBuilder.show();
                    // đánh dấu lỗi ở site này
                    flagErrorValidateInput = true;

                    // bỏ qua site này
                    continue;
                }

                int quantityOrdered = Integer.parseInt(item.getQuantityOrdered().getText());
                selectedQuantityInMoment += quantityOrdered;

                String desiredDeliveryDate;
                if (deliveryType.equals("ship")) {
                    desiredDeliveryDate = item.getDesiredDeliveryByShipDate();
                } else {
                    desiredDeliveryDate = item.getDesiredDeliveryByAirDate();
                }

                OrderLine orderLine = new OrderLine(siteId, quantityOrdered, desiredDeliveryDate, deliveryType);
                listOrderLine.add(orderLine);
            }

        }

        return selectedQuantityInMoment;
    }

    public void resetTableView() {
        for (FindSiteItem item : listItem) {
            if (item.getSelectedSite().isSelected()) {
                item.getSelectedSite().setSelected(false);
            }
        }

        // Cập nhật lại giao diện của TableView
        tableView.refresh();
    }


    public void saveSelectedSites() throws SQLException {
        // reset cờ đánh dấu lỗi trước khi get
        flagErrorValidateInput = false;

        int val = getSelectedItems();

        // Nếu không có lỗi thì mới thực hiện các logic khác nhé!
        if (flagErrorValidateInput) return;

        int quantityRequested = Integer.parseInt(quantityText.getText());
        int selectedQuantity = Integer.parseInt(selectedQuantityText.getText());

        if (val > (quantityRequested - selectedQuantity)) {
            VBox newVbox = new VBox();
            HBox newHbox = new HBox();
            Label text = new Label("Số lượng chọn đã vượt quá số lượng cần đặt. Bạn có muốn tiếp tục không ?");
            newVbox.getChildren().add(text);

            Button btnOk = new Button("Ok");
            Button btnCancel = new Button("Huy");

            newHbox.getChildren().add(btnCancel);
            newHbox.getChildren().add(btnOk);

            newVbox.getChildren().add(newHbox);

            graphic = newVbox;
            notification(Pos.CENTER, graphic, "");
            notificationBuilder.show();

        } else {
            resetTableView();
            // sql to save into db
            createOrder(listOrderLine, orderListItemId, itemId);

            selectedQuantity += val;
            selectedQuantityText.setText(String.valueOf(selectedQuantity));
            graphic = null;
            notification(Pos.TOP_RIGHT, null, "Lưu danh sách Site thành công!");
            notificationBuilder.show();

        }


    }

    public void createOrder(List<OrderLine> newListOrderLine, String orderListItemId, String itemId) throws SQLException {
        OrderSubsystem orderSubsystem = new OrderSubsystem();

        for (OrderLine orderLine: newListOrderLine) {
            orderSubsystem.saveSelectedSite(orderListItemId, itemId, orderLine, this.orderParentId);
        }
    }

    public String getItemId() {
        return itemId;
    }

    public void setPreView(String preView) {
        this.preView = preView;
    }

    public void setOrderParentId(String orderParentId) {
        this.orderParentId = orderParentId;
    }

    public void findItemSiteByItemId(String orderListItemId, OrderItem orderItem, int selectedQuantity) throws SQLException {
        System.out.println("Update table "+ orderItem.getItemId());
        this.orderListItemId = orderListItemId;
        this.orderItem = orderItem;
        this.selectedQuantity = selectedQuantity;

        findSiteItems = new ArrayList<>();

        ItemSiteSubsystem itemSiteSubsystem = new ItemSiteSubsystem();

        List<ItemSite> listItemSite = itemSiteSubsystem.findItemSiteByItemId(orderItem.getItemId());

        for (ItemSite item : listItemSite) {
            findSiteItems.add(new FindSiteItem(item.getSiteId(), item.getSiteName(), item.getQuantity(), item.getDesiredDeliveryByShipDate(), item.getDesiredDeliveryByAirDate()));
        }
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
