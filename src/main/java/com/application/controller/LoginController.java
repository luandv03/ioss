package com.application.controller;

import com.application.entity.User;
import com.application.model.Model;
import com.application.subsystemsql.UserSubsystem;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Button btn__login;

    public TextField emailElement;
    public PasswordField passwordElement;
    public Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn__login.setOnAction(event -> {
            try {
                validate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void validate() throws SQLException {
        String email = emailElement.getText();
        String password = passwordElement.getText();

        UserSubsystem userSubsystem = new UserSubsystem();
        User user = userSubsystem.login(email, password);

        if (user == null) {
            errorLabel.setText("Email hoặc mật khẩu không chính xác");
        } else {
            errorLabel.setManaged(false);
            Model.getInstance().getViewFactory().setUser(user);
            // navigate to home
            onLogin();
        }
    }

    public void onLogin() {
        Stage stage = (Stage) btn__login.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showMainLayoutWindow();
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("HomeView");
    }

}
