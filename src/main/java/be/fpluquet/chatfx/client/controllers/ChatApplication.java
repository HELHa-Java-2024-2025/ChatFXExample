package be.fpluquet.chatfx.client.controllers;

import be.fpluquet.chatfx.client.exceptions.ConnectionException;
import be.fpluquet.chatfx.client.services.NetworkService;
import be.fpluquet.chatfx.common.models.Message;
import be.fpluquet.chatfx.common.network.ObjectSocketCreationException;
import be.fpluquet.chatfx.client.views.LoginViewController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatApplication extends Application
        implements
        LoginViewController.Listener, ConversationController.Listener, NetworkService.Listener {
    private ConversationController conversationController;
    private LoginViewController loginViewController;
    private Stage loginStage;

    private NetworkService networkService;

    @Override
    public void start(Stage stage) throws IOException, ObjectSocketCreationException {
        networkService = new NetworkService();
        networkService.setListener(this);
        loginStage = stage;
        openLoginView();
        Stage conversationStage = new Stage();
        conversationStage.setTitle("Conversation");
        conversationController = new ConversationController(conversationStage, this);
    }

    public void openLoginView() throws IOException {
        loginStage.setTitle("Miahoo !");
        loginViewController = new LoginViewController();
        loginViewController.setListener(this);
        loginViewController.openInStage(loginStage);
        loginStage.show();
    }


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void askToConnect(String pseudo) {
        try {
            loginViewController.clearError();
            networkService.startCommunication();
            networkService.signIn(pseudo);
            loginStage.close();
            try {
                conversationController.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            loginViewController.showNetworkError();
            e.printStackTrace();
        } catch (ConnectionException e) {
            loginViewController.showConnectionError();
        } catch (ObjectSocketCreationException e) {
            loginViewController.showServerError();
        }
    }

    @Override
    public void onConversationViewClose()  {
        loginStage.show();
        networkService.disconnect();
    }

    @Override
    public void onAskToAddMessage(String message) {
        try {
            Message sentMessage = networkService.sendMessage(message);
            System.out.println("Message sent: " + sentMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onChatMessageReceived(Message message) {
        conversationController.addMessageToChat(message, networkService.isCurrentUser(message.getSender()));
    }
}