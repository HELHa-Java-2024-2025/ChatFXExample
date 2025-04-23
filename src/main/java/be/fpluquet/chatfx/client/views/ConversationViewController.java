package be.fpluquet.chatfx.client.views;

import be.fpluquet.chatfx.common.models.Message;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ConversationViewController extends AbstractViewController<ConversationViewController.Listener> {

    @FXML
    private TextField messageTextField;

    @FXML
    private VBox messagesVBox;

    @FXML
    private Button sendButton;

    @FXML
    private ScrollPane scrollPane;


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
        if (!message.isEmpty()) {
            if (message.length() > 1 && message.startsWith("/")) {
                // login change
                String newUsername = message.substring(1);
                this.listener.askToChangeUsername(newUsername);
            } else {
                this.listener.askToAddMessage(message);
            }
            messageTextField.clear();
            messageTextField.requestFocus();
        }
    }

    public void addMessageToChat(Message message, boolean isCurrentUser) throws IOException {
        double oldVvalue = scrollPane.getVvalue();
        System.out.println("oldVvalue : " + oldVvalue + (oldVvalue == 1.0 ? " (bottom)" : ""));

        MessageViewController messageViewController = new MessageViewController();
        Parent root = messageViewController.getRoot();
        messageViewController.setMessage(message, isCurrentUser);
        messagesVBox.getChildren().add(root);

        // scroll to bottom after 200ms if the scroll is at the bottom
        if (oldVvalue == 1.0 || oldVvalue == 0.0) {
            scrollToBottom();
        }
    }

    private void scrollToBottom() {
        new Thread(() -> {
            try {
                Thread.sleep(200);
                Platform.runLater(() -> {
                    scrollPane.setVvalue(1.0);
                    System.out.println("scroll to bottom");
                });
            } catch (InterruptedException e) {
            }
        }).start();
    }

    public void showChangePseudoNotification(String newPseudo, String oldPseudo, int userId) {
        Label notificationLabel = new Label();
        notificationLabel.setText("User " + oldPseudo + " changed his name to " + newPseudo);
        notificationLabel.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
        messagesVBox.getChildren().add(notificationLabel);
    }


    public interface Listener {
        void askToAddMessage(String message);
        void askToChangeUsername(String newUsername);
    }

}
