package be.fpluquet.chatfx.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConversationViewController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}