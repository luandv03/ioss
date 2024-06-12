package com.application.controller;

import com.application.model.Model;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public Text usernameElement;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String username = Model.getInstance().getViewFactory().getUser().getUsername();

        usernameElement.setText("Chào " + username + ", chúc bạn 1 ngày tốt lành!");
    }
}
