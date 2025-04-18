package be.fpluquet.chatfx.client.views;

import be.fpluquet.chatfx.common.models.Message;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MessageViewController extends AbstractViewController<MessageViewController.MessageViewListener> {
    @FXML
    private Label messageLabel;

    @FXML
    private Text pseudoLabel;

    @FXML
    private VBox rootVBox;

    Message message;

    @FXML
    public void initialize() {
    }

    public void setMessage(Message message, boolean itsMine) {
        this.message = message;
        this.messageLabel.setText(message.getContent());
        this.pseudoLabel.setText(message.getSender().getPseudo());
        if (itsMine) {
            rootVBox.setAlignment(Pos.TOP_RIGHT);
        }
    }


    @Override
    protected String getFXMLPath() {
        return "message-view.fxml";
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        this.listener.onClick(messageLabel.getText());
    }

    public interface MessageViewListener {
        void onClick(String message);
    }
}
