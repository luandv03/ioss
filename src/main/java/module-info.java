module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires fontawesomefx;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;
    requires org.postgresql.jdbc;

    opens com.application to javafx.fxml;
    opens com.application.controller to javafx.fxml;
    exports com.application;
    exports com.application.controller;
    exports com.application.entity;
    exports com.application.view;
    opens com.application.view to javafx.fxml;
    exports com.application.connection;
    exports com.application.dao;
    exports com.application.subsystemsql;
//    exports com.application.view;
//    opens com.application.view to javafx.fxml;
}