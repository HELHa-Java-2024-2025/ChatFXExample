package be.fpluquet.chatfx.controllers;

import be.fpluquet.chatfx.views.ConversationViewController;
import be.fpluquet.chatfx.views.LoginViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatApplication extends Application
        implements
        LoginViewController.Listener, ConversationController.Listener {
    private ConversationController conversationController;
    private LoginViewController loginViewController;
    private Stage loginStage;
    private String currentUser;

    @Override
    public void start(Stage stage) throws IOException {
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
        currentUser = pseudo;
        System.out.println("Pseudo : " + currentUser);
        loginStage.close();
        try {
            conversationController.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConversationViewClose()  {
        loginStage.show();
    }
}