package be.fpluquet.chatfx.controllers;
import java.net.Socket;
import java.util.Scanner;

import be.fpluquet.chatfx.network.client.ReadMessagesThread;
import be.fpluquet.chatfx.network.common.ObjectSocket;
import be.fpluquet.chatfx.network.common.ObjectSocketCreationException;
import be.fpluquet.chatfx.views.ConversationViewController;
import be.fpluquet.chatfx.views.LoginViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;

public class ChatApplication extends Application
        implements
        LoginViewController.Listener, ConversationController.Listener {
    private ConversationController conversationController;
    private LoginViewController loginViewController;
    private Stage loginStage;
    private String currentUser;
    private ObjectSocket objectSocket;

    @Override
    public void start(Stage stage) throws IOException, ObjectSocketCreationException {
        loginStage = stage;
        openLoginView();
        Stage conversationStage = new Stage();
        conversationStage.setTitle("Conversation");
        conversationController = new ConversationController(conversationStage, this);

        Socket socket = new Socket("localhost", 1099);
        objectSocket = new ObjectSocket(socket);
        ReadMessagesThread readMessagesThread = new ReadMessagesThread(objectSocket);
        readMessagesThread.setListener(conversationController);
        Thread thread = new Thread(readMessagesThread);
        thread.start();
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

    @Override
    public void onAskToAddMessage(String message) {
        try {
            objectSocket.write(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}