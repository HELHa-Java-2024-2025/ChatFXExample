package be.fpluquet.chatfx.client.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbstractViewController<ListenerType> {
    public void openInStage(Stage stage) throws IOException {
        Scene scene = new Scene(this.getRoot());
        stage.setScene(scene);
    }

    public Parent getRoot() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(this.getFXMLPath()));
        loader.setController(this);
        return loader.load();
    }

    protected abstract String getFXMLPath();

    protected ListenerType listener;

    public void setListener(ListenerType listener) {
        this.listener = listener;
    }



}

