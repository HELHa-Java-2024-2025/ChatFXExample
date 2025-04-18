package be.fpluquet.chatfx.client.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginViewController extends AbstractViewController<LoginViewController.Listener> {

    @FXML
    private Button connectButton;

    @FXML
    private TextField pseudoTextField;

    @FXML
    private Label errorLabel;


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
        clearError();
    }

    public void clearError() {
        errorLabel.setText("");
    }

    public void showNetworkError() {
        errorLabel.setText("Une erreur réseau s'est produite. Vérifiez que le serveur est démarré.");
    }

    public void showConnectionError() {
        errorLabel.setText("Le serveur n'a pu vous connecter. Veuillez réessayer plus tard.");
    }

    public void showServerError() {
        this.showNetworkError();
    }


    public interface Listener {
        void askToConnect(String pseudo);
    }

}
