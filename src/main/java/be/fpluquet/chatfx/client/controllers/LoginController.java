package be.fpluquet.chatfx.client.controllers;

import be.fpluquet.chatfx.client.exceptions.ConnectionException;
import be.fpluquet.chatfx.client.services.NetworkService;
import be.fpluquet.chatfx.client.views.LoginViewController;
import be.fpluquet.chatfx.common.network.ObjectSocketCreationException;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController extends AbstractStageController<LoginController.Listener> implements LoginViewController.Listener {

    LoginViewController viewController;

    public LoginController(NetworkService networkService, Stage stage) {
        super(networkService, stage);
        this.viewController = new LoginViewController();
        this.viewController.setListener(this);
        stage.setTitle("Miahoo !");
    }

    public void show() throws IOException {
        viewController.openInStage(this.stage);
        this.stage.show();
    }


    @Override
    public void askToConnect(String pseudo) {
        try {
            viewController.clearError();
            networkService.startCommunication();
            networkService.signIn(pseudo);
            this.listener.onLoginSuccess();
        } catch (IOException e) {
            viewController.showNetworkError();
            e.printStackTrace();
        } catch (ConnectionException e) {
            viewController.showConnectionError();
        } catch (ObjectSocketCreationException e) {
            viewController.showServerError();
        }
    }

    public interface Listener {
        void onLoginSuccess();
    }
}
