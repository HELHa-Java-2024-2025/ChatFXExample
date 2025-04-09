package be.fpluquet.chatfx.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConversationViewController extends AbstractViewController<ConversationViewController.Listener> {

    @FXML
    private TextField messageTextField;

    @FXML
    private VBox messagesVBox;

    @FXML
    private Button sendButton;


    @Override
    protected String getFXMLPath() {
        return "conversation-view.fxml";
    }

    @FXML
    void initialize() {
        sendButton.setOnAction(event -> {
            askToAddMessage();
        });

        messageTextField.setOnKeyTyped(keyEvent -> {
            // TODO : Check with keyEvent.getCode() == KeyCode.ENTER
            if (keyEvent.getCharacter().equals("\r")) {
                askToAddMessage();
            }
        });
    }

    private void askToAddMessage() {
        String message = messageTextField.getText();
        this.listener.askToAddMessage(message);
    }

    public void addMessageToChat(String message) {
        if (!message.isEmpty()) {
            Label messageLabel = new Label("Fréd : " + message);
            messagesVBox.getChildren().add(messageLabel);
            messageTextField.clear();
            messageTextField.requestFocus();
        }
    }



    public interface Listener {
        void askToAddMessage(String message);
    }

}
