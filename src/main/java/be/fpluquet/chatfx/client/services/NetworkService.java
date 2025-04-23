package be.fpluquet.chatfx.client.services;

import be.fpluquet.chatfx.client.exceptions.ConnectionException;
import be.fpluquet.chatfx.client.repositories.NetworkRepository;
import be.fpluquet.chatfx.common.models.Message;
import be.fpluquet.chatfx.common.models.User;
import be.fpluquet.chatfx.common.network.ObjectSocketCreationException;
import be.fpluquet.chatfx.common.network.commands.ChangeUsernameCommand;
import be.fpluquet.chatfx.common.network.commands.SendMessageCommand;

import java.io.IOException;

public class NetworkService implements NetworkRepository.Listener {

    private User user;
    private NetworkRepository networkRepository;
    private Listener listener;


    public NetworkService() {
        this.user = null;
        this.networkRepository = new NetworkRepository();
        this.networkRepository.setListener(this);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public boolean isConnected() {
        return this.user != null;
    }

    public void startCommunication() throws ObjectSocketCreationException, IOException {
        this.networkRepository.startCommunication();
    }

    public void signIn(String pseudo) throws ConnectionException, IOException {
        User user = new User(pseudo);
        this.user = this.networkRepository.signIn(user);
        this.startChatCommunication();
    }

    public void disconnect() {
        this.user = null;
        this.networkRepository.disconnect();
    }

    @Override
    public void onMessageReceived(Message message) {
        if (listener != null) {
            listener.onChatMessageReceived(message);
        }
    }

    @Override
    public void onChangePseudo(String newPseudo, String oldPseudo, int userId) {
        if (listener != null) {
            listener.onChangePseudo(newPseudo, oldPseudo, userId);
        }
    }

    private void startChatCommunication() {
        this.networkRepository.startChatCommunication();
    }

    public Message sendMessage(String messageContent) throws IOException {
        Message message = new Message(messageContent, this.user);
        this.networkRepository.sendCommand(new SendMessageCommand(message));
        return message;
    }

    public boolean isCurrentUser(User sender) {
        return this.user.getId() == sender.getId();
    }

    public void changeUsername(String newUsername) throws IOException {
        try {
            this.networkRepository.sendCommand(new ChangeUsernameCommand(newUsername, user.getName(), user.getId()));
            user.setName(newUsername);
        } catch (IOException e) {
            System.err.println("Error while sending change username command: " + e.getMessage());
            throw e;
        }
    }

    public interface Listener {
        void onChatMessageReceived(Message message);

        void onChangePseudo(String newPseudo, String oldPseudo, int userId);
    }

}
