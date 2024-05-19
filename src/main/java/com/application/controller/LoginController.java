package com.application.controller;

import com.application.model.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Button btn__login;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn__login.setOnAction(event -> onLogin());
    }

    public void onLogin() {
        Stage stage = (Stage) btn__login.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showMainLayoutWindow();
    }

}
