package be.fpluquet.chatfx.client.services;

import be.fpluquet.chatfx.client.exceptions.ConnectionException;
import be.fpluquet.chatfx.client.repositories.NetworkRepository;
import be.fpluquet.chatfx.common.models.Message;
import be.fpluquet.chatfx.common.models.User;
import be.fpluquet.chatfx.common.network.ObjectSocketCreationException;

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

    private void startChatCommunication() {
        this.networkRepository.startChatCommunication();
    }

    public Message sendMessage(String message) throws IOException {
        Message msg = new Message(message, this.user);
        this.networkRepository.sendMessage(msg);
        return msg;
    }

    public boolean isCurrentUser(User sender) {
        return this.user.getId().equals(sender.getId());
    }

    public interface Listener {
        void onChatMessageReceived(Message message);
    }

}
