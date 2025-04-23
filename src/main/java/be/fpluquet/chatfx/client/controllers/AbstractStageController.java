package be.fpluquet.chatfx.client.controllers;

import be.fpluquet.chatfx.client.services.NetworkService;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbstractStageController<Listener> {
    NetworkService networkService;
    Stage stage;
    Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public AbstractStageController(NetworkService networkService, Stage stage) {
        this.networkService = networkService;
        this.stage = stage;
    }

    public void show() throws IOException {
        this.stage.show();
    }

    public void close() {
        this.stage.close();
    }

}
