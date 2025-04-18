module be.fpluquet.chatfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;

    opens be.fpluquet.chatfx.client.views to javafx.fxml;
    exports be.fpluquet.chatfx.client.controllers;
    opens be.fpluquet.chatfx.client.controllers to javafx.fxml;
    exports be.fpluquet.chatfx.client.views;
    exports be.fpluquet.chatfx.client.exceptions;
    opens be.fpluquet.chatfx.client.exceptions to javafx.fxml;
    exports be.fpluquet.chatfx.client.repositories;
    opens be.fpluquet.chatfx.client.repositories to javafx.fxml;
    exports be.fpluquet.chatfx.client.services;
    opens be.fpluquet.chatfx.client.services to javafx.fxml;
    exports be.fpluquet.chatfx.common.models;
    opens be.fpluquet.chatfx.common.models to javafx.fxml;
    exports be.fpluquet.chatfx.common.network;
    opens be.fpluquet.chatfx.common.network to javafx.fxml;

}