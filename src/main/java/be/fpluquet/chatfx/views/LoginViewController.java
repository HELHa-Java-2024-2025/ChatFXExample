package be.fpluquet.chatfx.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginViewController extends AbstractViewController<LoginViewController.Listener> {

    @FXML
    private Button connectButton;

    @FXML
    private TextField pseudoTextField;


    @Override
    protected String getFXMLPath() {
        return "login-view.fxml";
    }

    @FXML
    void initialize() {
        connectButton.setOnAction(event -> {
            String pseudo = pseudoTextField.getText();
            if (!pseudo.isEmpty()) {
                listener.askToConnect(pseudo);
            }
        });
    }


    public interface Listener {
        void askToConnect(String pseudo);
    }

}
