package com.application.controller;

import com.application.entity.OrderDetail;
import com.application.model.Model;
import com.application.subsystemsql.OrderListItemSubsystem;
import com.application.subsystemsql.OrderSubsystem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderListInActiveController implements Initializable {
    private String orderTypePage; // Xu ly dsmhcd, Xu ly don hang bi huy
    // Type = "OrderCanceled"
    // Type = "OrderListItem"

    private String orderParentId;

    Notifications notificationBuilder;
    Node graphic;
    public Button btnClose;
    public Button requestOrderBtn;

    private String orderListItemId;
    public VBox orderListElement;
    private List<OrderDetail> listOrderDetail = new ArrayList<>();

    OrderSubsystem orderSubsystem = new OrderSubsystem();
    OrderListItemSubsystem orderListItemSubsystem = new OrderListItemSubsystem();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnClose.setOnAction(event->{
            closeThisView();
        });

        try {
            displayListOrder();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

        requestOrderBtn.setOnAction(event -> {
            try {
                if (this.orderTypePage.equals("OrderCanceled")) {
                    requestOrderForOrderCanceled();
                } else if (this.orderTypePage.equals("OrderListItem")) {
                    requestOrderForOrderListItem();
                }
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void closeThisView() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
    }

    public void setOrderListItemId(String orderListItemId) {
        this.orderListItemId = orderListItemId;
    }

    public void setOrderParentId(String orderParentId) {
        this.orderParentId = orderParentId;
    }

    public void setOrderTypePage(String orderTypePage) {
        this.orderTypePage = orderTypePage;
    }

    public void displayListOrder() throws IOException, SQLException {
//        getListOrder(orderListItemId);

        if (listOrderDetail.isEmpty()) {
            Label t = new Label("Hiện chưa có đơn hàng nào được tạo !");
            orderListElement.setMinWidth(400);
            orderListElement.getChildren().add(t);
            return;
        }

        for (OrderDetail order: listOrderDetail) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/application/fxml/" + "OrderLine" + ".fxml"));

//            OrderDetailController ctl = fxmlLoader.getController();
            OrderLineController ctl = new OrderLineController();

            ctl.setOrderDetail(order);

            fxmlLoader.setController(ctl);

            AnchorPane orderListInActiveElement = fxmlLoader.load();

            orderListElement.getChildren().add(orderListInActiveElement);
        }
    }

    public void getListOrder(String orderListItemId) throws SQLException {
        listOrderDetail = orderSubsystem.getListOrderByOrderListItemIdAndStatus(orderListItemId, "inActive");
    }

    public void getListOrder(String orderListItemId, String orderParentId) throws SQLException {
        System.out.println(orderListItemId + " " + orderParentId);
        listOrderDetail = orderSubsystem.getListOrderByOrderListItemIdAndStatus(this.orderListItemId, "inActive", orderParentId);
        System.out.println(listOrderDetail.size());
    }

    public void requestOrderForOrderListItem() throws SQLException, IOException {
        if (listOrderDetail.isEmpty()) return;

        for (OrderDetail orderDetail: listOrderDetail) {
            orderSubsystem.updateOrderStatus(orderDetail.getOrderId(), "active");
        }

        orderListItemSubsystem.updateStatusOrderListItem(orderListItemId, "loading");

        // send noti request success
        graphic = null;
        notification(Pos.TOP_RIGHT, null, "Gửi yêu cầu đặt hàng thành công!");
        notificationBuilder.show();

        displayListOrder();

        OrderListItemController.getInstance().setOrderListItemStatus("loading");
        OrderListItemController.getInstance().updateOrderListItemStatusText("loading");
    }

    public void requestOrderForOrderCanceled() throws SQLException, IOException {
        if (listOrderDetail.isEmpty()) return;

        for (OrderDetail orderDetail: listOrderDetail) {
            orderSubsystem.updateOrderStatus(orderDetail.getOrderId(), "active");
        }

        // reset đơn hàng cha (bị hủy) này về 1 trạng thái mới
        orderSubsystem.updateOrderStatus(this.orderParentId, "deleted");

        // send noti request success
        graphic = null;
        notification(Pos.TOP_RIGHT, null, "Gửi yêu cầu đặt hàng thành công!");
        notificationBuilder.show();


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
