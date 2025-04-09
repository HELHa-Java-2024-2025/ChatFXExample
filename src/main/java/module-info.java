module be.fpluquet.chatfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;

    opens be.fpluquet.chatfx.views to javafx.fxml;
    exports be.fpluquet.chatfx.controllers;
    opens be.fpluquet.chatfx.controllers to javafx.fxml;
    exports be.fpluquet.chatfx.views;
}