package com.application.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Stack;

public class ViewFactory {

    public final StringProperty selectedMenuItem;
    private AnchorPane homeView;
    private AnchorPane orderListsItemView;
    private AnchorPane orderListItemView;
    private AnchorPane findSiteView;
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

    public AnchorPane getOrderListItemView() {
        if (orderListItemView == null) {
            try {
                orderListItemView = (AnchorPane) loadFXML("OrderListItem");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return orderListItemView;
    }

    public AnchorPane getFindSiteView() {
        if (findSiteView == null) {
            try {
                findSiteView = (AnchorPane) loadFXML("FindSite");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return findSiteView;
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
