package be.fpluquet.chatfx.controllers;

import be.fpluquet.chatfx.views.ConversationViewController;
import be.fpluquet.chatfx.views.LoginViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatApplication extends Application
        implements ConversationViewController.Listener,
                    LoginViewController.Listener {
    private ConversationViewController conversationViewController;
    private LoginViewController loginViewController;
    private Stage loginStage;
    private Stage conversationStage;
    private String currentUser;

    @Override
    public void start(Stage stage) throws IOException {
        loginStage = stage;
        openLoginView();
    }

    public void openLoginView() throws IOException {
        loginStage.setTitle("Miahoo !");
        loginViewController = new LoginViewController();
        loginViewController.setListener(this);
        loginViewController.openInStage(loginStage);
        loginStage.show();
    }

    public void openConversationView() throws IOException {
        if(conversationStage == null) {
            conversationStage = new Stage();
            conversationStage.setTitle("Conversation");
            conversationStage.setOnCloseRequest(windowEvent -> {
                conversationStage.close();
                loginStage.show();
            });
        }

        conversationViewController = new ConversationViewController();
        conversationViewController.setListener(this);
        conversationViewController.openInStage(conversationStage);
        conversationStage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void askToAddMessage(String message) {
        conversationViewController.addMessageToChat(message);
    }

    @Override
    public void askToConnect(String pseudo) {
        currentUser = pseudo;
        System.out.println("Pseudo : " + currentUser);
        loginStage.close();
        try {
            openConversationView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}