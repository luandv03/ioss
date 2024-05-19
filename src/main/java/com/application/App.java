package com.application;

import com.application.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Model.getInstance().getViewFactory().showLoginWindow();

    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/" + fxml + ".css")).toExternalForm());

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}