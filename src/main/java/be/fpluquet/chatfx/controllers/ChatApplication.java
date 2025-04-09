package be.fpluquet.chatfx.controllers;

import be.fpluquet.chatfx.views.ConversationViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatApplication extends Application implements ConversationViewController.Listener {
    private ConversationViewController conversationViewController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ConversationViewController.class.getResource("conversation-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        
        conversationViewController = fxmlLoader.getController();
        conversationViewController.setListener(this);
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void askToAddMessage(String message) {
        conversationViewController.addMessageToChat(message);
    }
}