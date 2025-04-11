package be.fpluquet.chatfx.network.client;

import be.fpluquet.chatfx.network.common.ObjectSocket;

public class ReadMessagesThread implements Runnable {

    private ObjectSocket objectSocket;

    public interface Listener {
        void onMessageReceived(String message);
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public ReadMessagesThread(ObjectSocket objectSocket) {
        this.objectSocket = objectSocket;
    }
    @Override
    public void run() {
        try {
            while(true) {
                System.out.println("En attente d'un message...");
                String string = objectSocket.read();
                System.out.println("Nouveau message depuis le serveur :" + string);
                if (listener != null) {
                    listener.onMessageReceived(string);
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du message.");

        }

    }
}
