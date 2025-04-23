package be.fpluquet.chatfx.client.controllers;

import be.fpluquet.chatfx.client.services.NetworkService;
import be.fpluquet.chatfx.client.views.ConversationViewController;
import be.fpluquet.chatfx.common.models.Message;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;

// all things related to the conversation view controller
public class ConversationController extends AbstractStageController<ConversationController.Listener> implements ConversationViewController.Listener, NetworkService.Listener {

    ConversationViewController conversationViewController;

    public ConversationController(NetworkService networkService, Stage stage) {
        super(networkService, stage);
        conversationViewController = new ConversationViewController();
        conversationViewController.setListener(this);
        networkService.setListener(this);
        this.stage.setOnCloseRequest(windowEvent -> {
            listener.onConversationViewClose();
        });
    }

    public void show() throws IOException {
        conversationViewController.openInStage(this.stage);
        super.show();
    }

    @Override
    public void askToAddMessage(String message) {
        try {
            Message sentMessage = networkService.sendMessage(message);
            System.out.println("Message sent: " + sentMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void askToChangeUsername(String newUsername) {
        try {
            networkService.changeUsername(newUsername);
            System.out.println("Username changed: " + newUsername);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onChatMessageReceived(Message message) {
        this.addMessageToChat(message, networkService.isCurrentUser(message.getSender()));

    }

    @Override
    public void onChangePseudo(String newPseudo, String oldPseudo, int userId) {
        this.showChangePseudoNotification(newPseudo, oldPseudo, userId);
    }

    private void showChangePseudoNotification(String newPseudo, String oldPseudo, int userId) {
        Platform.runLater(() -> {
            conversationViewController.showChangePseudoNotification(newPseudo, oldPseudo, userId);
        });
    }

    public interface Listener {
        void onConversationViewClose();
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
