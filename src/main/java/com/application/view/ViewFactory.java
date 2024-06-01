package com.application.view;

import com.application.controller.FindSiteController;
import com.application.controller.MainLayoutController;
import com.application.controller.OrderListItemController;
import com.application.controller.OderDetailController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Stack;


public class ViewFactory {

    public final StringProperty selectedMenuItem;
    private AnchorPane homeView;
    private AnchorPane orderListsItemView;
    private AnchorPane orderListItemView;
    private AnchorPane findSiteView;

    private AnchorPane orderItemDetailView;

    private FindSiteController findSiteController;
    private AnchorPane OrderListItem_DaXuLi;
    private AnchorPane CorrespondingListOrderItemSite;
    private VBox OrderChild;

    public ViewFactory() {
        this.selectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getSelectedMenuItem() {
        return selectedMenuItem;
    }

    public AnchorPane getOrderListsItemView() {
        if (orderListsItemView == null) {
            try {
              orderListsItemView = (AnchorPane) loadFXML("OrderListsItem");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return orderListsItemView;
    }



    public AnchorPane getOrderItemDetailView() {
        if (orderItemDetailView == null) {
            try {
                orderItemDetailView = (AnchorPane) loadFXML("OrderDetail");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/application/fxml/" + "OrderDetail" + ".fxml"));
                OderDetailController oderDetailController = OderDetailController.getInstance();
                fxmlLoader.setController(oderDetailController);

                orderItemDetailView = fxmlLoader.load();
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return orderListItemView;
    }

    public AnchorPane getOrderListItemView() {
        if (orderListItemView == null) {
            try {
                orderListItemView = (AnchorPane) loadFXML("OrderListItem");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/application/fxml/" + "OrderListItem" + ".fxml"));
                OrderListItemController orderListItemController = OrderListItemController.getInstance();
                fxmlLoader.setController(orderListItemController);

                orderListItemView = fxmlLoader.load();
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return orderListItemView;
    }

    public void resetOrderListItemView() {
        orderListItemView = null;
    }

    public AnchorPane getFindSiteView() {
        if (findSiteView == null) {
            try {
//                findSiteView = (AnchorPane) loadFXML("FindSite");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/application/fxml/" + "FindSite" + ".fxml"));
                FindSiteController findSiteController = FindSiteController.getInstance("");
                fxmlLoader.setController(findSiteController);
                findSiteView = fxmlLoader.load();
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return findSiteView;
    }

    public void resetFindSiteView() {
        findSiteView = null;
    }

    public AnchorPane getOrderListItem_DaXuLi() {
        if (OrderListItem_DaXuLi == null) {
            try {
                OrderListItem_DaXuLi = (AnchorPane) loadFXML("OrderListItem_DaXuLi");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return OrderListItem_DaXuLi;
    }

    public AnchorPane getCorrespondingListOrderItemSite() {
        if (CorrespondingListOrderItemSite == null) {
            try {
                CorrespondingListOrderItemSite = (AnchorPane) loadFXML("CorrespondingListOrderItemSite");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return CorrespondingListOrderItemSite;
    }

    ///Lấy các thằng con của danh sách đơn hàng tương ứng
/*    public VBox getOrderChild() {
        if (OrderChild == null) {
            try {
                OrderChild = (VBox) loadFXML("OrderChild");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return OrderChild;
    }*/

    public FXMLLoader getOrderChild()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/application/fxml/" + "OrderChild" + ".fxml"));
        return  fxmlLoader;
    }

    public AnchorPane getHomeView() {
        if (homeView == null) {
            try {
                homeView = (AnchorPane) loadFXML("Home");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return homeView;
    }

    public void showLoginWindow() {
        createStage("Login");
    }

    public void showMainLayoutWindow() {
        createStage("MainLayout");
    }

    private void createStage(String fxml) {
        Scene scene = null;

        try {
            scene = new Scene(loadFXML(fxml));

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("My App I2SO");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/application/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
