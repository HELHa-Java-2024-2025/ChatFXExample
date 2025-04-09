package be.fpluquet.chatfx.controllers;

import be.fpluquet.chatfx.views.ConversationViewController;
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
        conversationViewController.addMessageToChat(message);
    }

    public interface Listener {
        void onConversationViewClose();
    }
}
