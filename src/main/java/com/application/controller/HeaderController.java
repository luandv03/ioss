package com.application.controller;

import com.application.model.Model;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class HeaderController implements Initializable {
    public Text usernameElement;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameElement.setText(Model.getInstance().getViewFactory().getUser().getUsername());
    }
}
