package be.fpluquet.chatfx.client.appController;

import be.fpluquet.chatfx.client.controllers.ConversationController;
import be.fpluquet.chatfx.client.controllers.LoginController;
import be.fpluquet.chatfx.client.services.NetworkService;
import be.fpluquet.chatfx.common.network.ObjectSocketCreationException;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatApplication extends Application
        implements
        ConversationController.Listener, LoginController.Listener {

    private ConversationController conversationController;
    private LoginController loginController;

    private NetworkService networkService;


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException, ObjectSocketCreationException {
        // services setup
        networkService = new NetworkService();

        // controllers setup
        Stage conversationStage = new Stage();

        loginController = new LoginController(networkService, stage);
        loginController.setListener(this);
        conversationController = new ConversationController(networkService, conversationStage);
        conversationController.setListener(this);

        // view setup
        loginController.show();
    }



    @Override
    public void onLoginSuccess() {
        try {
            loginController.close();
            conversationController.show();
        } catch (IOException e) {
            e.printStackTrace(); // TODO : Handle error
        }
    }

    @Override
    public void onConversationViewClose()  {
        try {
            loginController.show();
            networkService.disconnect();
        } catch (IOException e) {
            e.printStackTrace(); // TODO : Handle error
        }
    }

}