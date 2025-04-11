package be.fpluquet.chatfx.controllers;

import be.fpluquet.chatfx.network.client.ReadMessagesThread;
import be.fpluquet.chatfx.views.ConversationViewController;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;

// all things related to the conversation view controller
public class ConversationController implements ConversationViewController.Listener, ReadMessagesThread.Listener {

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

    @Override
    public void onMessageReceived(String message) {
        Platform.runLater(() -> {
            conversationViewController.addMessageToChat(message);
        });
    }

    public interface Listener {
        void onConversationViewClose();
        void onAskToAddMessage(String message);
    }
}
