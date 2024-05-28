package com.application.helper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Notification {
    Notifications notificationBuilder;

    public void notification(Pos pos, Node graphic, String text, String title) {
        notificationBuilder = Notifications.create()
                .title(title)
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
