package be.fpluquet.chatfx.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbstractViewController<ListenerType> {
    public void openInStage(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(this.getFXMLPath()));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    protected abstract String getFXMLPath();

    protected ListenerType listener;

    public void setListener(ListenerType listener) {
        this.listener = listener;
    }



}

