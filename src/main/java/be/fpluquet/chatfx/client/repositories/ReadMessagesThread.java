package be.fpluquet.chatfx.client.repositories;

import be.fpluquet.chatfx.common.models.Message;
import be.fpluquet.chatfx.common.network.ObjectSocket;
import be.fpluquet.chatfx.common.network.commands.AbstractCommand;
import be.fpluquet.chatfx.common.network.commands.ChangeUsernameCommand;
import be.fpluquet.chatfx.common.network.commands.CommandDispatch;
import be.fpluquet.chatfx.common.network.commands.SendMessageCommand;

public class ReadMessagesThread implements Runnable, CommandDispatch {

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
                AbstractCommand command = objectSocket.read();
                command.executeOn(this);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du message.");
            e.printStackTrace();
        }

    }

    public void stop() {
        running = false;
    }

    @Override
    public void onCommand(ChangeUsernameCommand c) {
        if(this.listener != null)
            this.listener.onChangePseudo(c.getNewUsername(), c.getOldUsername(), c.getUserId());
    }

    @Override
    public void onCommand(SendMessageCommand c) {
        if(this.listener != null)
            this.listener.onMessageReceived(c.getMessage());
    }


    public interface Listener {
        void onMessageReceived(Message message);
        void onChangePseudo(String newPseudo, String oldPseudo, int userId);
    }
}
