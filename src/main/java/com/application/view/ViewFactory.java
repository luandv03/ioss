package com.application.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Stack;

public class ViewFactory {

    public final StringProperty selectedMenuItem;
    private AnchorPane homeView;
    private AnchorPane orderListsItemView;
    private AnchorPane orderListItemView;

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
