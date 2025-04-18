package be.fpluquet.chatfx.client.repositories;

import be.fpluquet.chatfx.common.models.Message;
import be.fpluquet.chatfx.common.network.ObjectSocket;

public class ReadMessagesThread implements Runnable {

    private ObjectSocket objectSocket;


    private Listener listener;

    boolean running = true;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public ReadMessagesThread(ObjectSocket objectSocket) {
        this.objectSocket = objectSocket;
    }
    @Override
    public void run() {
        try {
            while(running) {
                System.out.println("En attente d'un message...");
                Message message = objectSocket.read();
                System.out.println("Nouveau message depuis le serveur :" + message);
                if (listener != null) {
                    listener.onMessageReceived(message);
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du message.");
            e.printStackTrace();
        }

    }

    public void stop() {
        running = false;
    }


    public interface Listener {
        void onMessageReceived(Message message);
    }
}
