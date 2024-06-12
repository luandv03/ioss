package com.application.view;

import com.application.controller.*;
import com.application.entity.User;
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

    private User user;

    public final StringProperty selectedMenuItem;
    private AnchorPane homeView;
    private AnchorPane orderListsItemView;
    private AnchorPane orderListsItemSaleView;

    private AnchorPane orderListItemView;
    private AnchorPane orderListItemCreateView;
    private AnchorPane findSiteView;
    private AnchorPane orderListInActiveView;
    private FindSiteController findSiteController;
    private AnchorPane OrderListItemLoadingAndDone;
    private AnchorPane CorrespondingListOrderItemSite;
    private VBox OrderChild;

    private AnchorPane CheckOrdersView;
    private AnchorPane ReportView;
    private AnchorPane InventoryManagementView;

    private AnchorPane orderDetailView;


    public ViewFactory() {
        this.selectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getSelectedMenuItem() {
        return selectedMenuItem;
    }

    public AnchorPane getReportView() {
        if (orderListsItemView == null) {
            try {
                ReportView = (AnchorPane) loadFXML("Report");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return ReportView;
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

    public AnchorPane getOrderListsItemSaleView() {
        if (orderListsItemSaleView == null) {
            try {
//                orderListsItemSaleView = (AnchorPane) loadFXML("OrderListsItemSale");

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/application/fxml/" + "OrderListsItemSale" + ".fxml"));
                OrderListsItemSaleController c = OrderListsItemSaleController.getInstance();
                fxmlLoader.setController(c);

                orderListsItemSaleView = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return orderListsItemSaleView;
    }

    //
    public AnchorPane getOrderDetailView() {
        if (orderDetailView == null) {
            try {
                orderDetailView = (AnchorPane) loadFXML("OrderDetail");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/application/fxml/" + "OrderDetail" + ".fxml"));
                OrderDetailController orderDetailController = OrderDetailController.getInstance();
                fxmlLoader.setController(orderDetailController);

                orderDetailView = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return orderDetailView;
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

    public AnchorPane getOrderListItemCreateView() {
        if (orderListItemCreateView == null) {
            try {
//                orderListItemCreateView = (AnchorPane) loadFXML("OrderListItem");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/application/fxml/" + "OrderListItemCreate" + ".fxml"));
                OrderListItemCreateController c = OrderListItemCreateController.getInstance();
                fxmlLoader.setController(c);

                orderListItemCreateView = fxmlLoader.load();
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return orderListItemCreateView;
    }

    public void resetOrderListItemView() {
        orderListItemView = null;
    }

    public void resetOrderListItemCreateView() {
        orderListItemCreateView = null;
    }

    public void resetOrderCanceledView() {
        orderListItemView = null;
    }

    public void resetOrderDetail() {
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

    public AnchorPane getOrderListItemLoadingAndDone() {
        //Bắt scene phải load lại do trong javafx hơi khó lấy controller của nó
        //if (OrderListItemLoadingAndDone == null) {
            try {
                OrderListItemLoadingAndDone = (AnchorPane) loadFXML("OrderListItemLoadingAndDone");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        //}
        return OrderListItemLoadingAndDone;
    }

    public AnchorPane getCorrespondingListOrderItemSite() {
        //Bắt scene phải load lại do trong javafx hơi khó lấy controller của nó
        //if (CorrespondingListOrderItemSite == null) {
            try {
                CorrespondingListOrderItemSite = (AnchorPane) loadFXML("CorrespondingListOrderItemSite");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        //}

        return CorrespondingListOrderItemSite;
    }

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

    public void resetHomeView() {
        homeView = null;
    }

    public AnchorPane getInventoryManagementView() {
        if (InventoryManagementView == null) {
            try {
//                InventoryManagementView = (AnchorPane) loadFXML("InventoryManagement");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/application/fxml/" + "InventoryManagement" + ".fxml"));
                InventoryManagementController c = InventoryManagementController.getInstance();
                fxmlLoader.setController(c);
                InventoryManagementView = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return InventoryManagementView;
    }

    public void resetInventoryManagementView() {
        InventoryManagementView = null;
    }


    public AnchorPane getCheckOrdersView() {
        if (CheckOrdersView == null) {
            try {
//                CheckOrdersView = (AnchorPane) loadFXML("CheckOrders");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/application/fxml/" + "CheckOrders" + ".fxml"));
                CheckOrdersController c = CheckOrdersController.getInstance();
                fxmlLoader.setController(c);
                CheckOrdersView = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return CheckOrdersView;
    }

    public void resetCheckOrdersView() {
        CheckOrdersView = null;
    }

    public AnchorPane getOrderListInActiveView() {
        if (orderListInActiveView == null) {
            try {
                orderListInActiveView = (AnchorPane) loadFXML("OrderListInActive");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return orderListInActiveView;
    }

    public void showLoginWindow() {
        createStage("Login");
    }

    public void showMainLayoutWindow() {
        createStage("MainLayout");
    }

    public void createStage(String fxml) {
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


    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
       return this.user;
    }
}
