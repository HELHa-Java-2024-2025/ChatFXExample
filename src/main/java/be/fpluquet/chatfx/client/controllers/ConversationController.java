package be.fpluquet.chatfx.client.controllers;

import be.fpluquet.chatfx.client.repositories.ReadMessagesThread;
import be.fpluquet.chatfx.client.views.ConversationViewController;
import be.fpluquet.chatfx.common.models.Message;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;

// all things related to the conversation view controller
public class ConversationController implements ConversationViewController.Listener {

    ConversationViewController conversationViewController;
    Stage stage;
    Listener listener;

    public ConversationController(Stage stage, Listener listener) {
        conversationViewController = new ConversationViewController();
        conversationViewController.setListener(this);
        this.stage = stage;
        this.listener = listener;
        this.stage.setOnCloseRequest(windowEvent -> {
            listener.onConversationViewClose();
        });
    }

    public void open() throws IOException {
        conversationViewController.openInStage(this.stage);
        this.stage.show();
    }

    @Override
    public void askToAddMessage(String message) {
        this.listener.onAskToAddMessage(message);
    }

    public interface Listener {
        void onConversationViewClose();
        void onAskToAddMessage(String message);
    }

    public void addMessageToChat(Message message, boolean isCurrentUser) {
        Platform.runLater(() -> {
            try {
                conversationViewController.addMessageToChat(message, isCurrentUser);
            } catch (IOException e) {
                e.printStackTrace();
                // TODO : Handle error
            }
        });
    }
}
