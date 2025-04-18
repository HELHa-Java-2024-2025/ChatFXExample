package be.fpluquet.chatfx.client.repositories;

import be.fpluquet.chatfx.client.exceptions.ConnectionException;
import be.fpluquet.chatfx.common.models.Message;
import be.fpluquet.chatfx.common.models.User;
import be.fpluquet.chatfx.common.network.ObjectSocket;
import be.fpluquet.chatfx.common.network.ObjectSocketCreationException;
import be.fpluquet.chatfx.common.network.Protocol;

import java.io.IOException;
import java.net.Socket;

public class NetworkRepository implements ReadMessagesThread.Listener {

    private Listener listener;
    private ObjectSocket objectSocket;
    private ReadMessagesThread readMessagesThread;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void startCommunication() throws IOException, ObjectSocketCreationException {
        Socket socket = new Socket(Protocol.SERVER_IP, Protocol.SERVER_PORT);
        this.objectSocket = new ObjectSocket(socket);
    }

    public void addListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onMessageReceived(Message message) {
        if(listener != null) {
            listener.onMessageReceived(message);
        }
    }

    public void disconnect() {
        try {
            readMessagesThread.stop();
            if (objectSocket != null) {
                objectSocket.close();
            }
        } catch (Exception e) {
            System.err.println("Error while closing socket: " + e.getMessage());
        }
    }

    public void startChatCommunication() {
        readMessagesThread = new ReadMessagesThread(objectSocket);
        readMessagesThread.setListener(this);
        Thread thread = new Thread(readMessagesThread);
        thread.start();
    }

    public User signIn(User user) throws ConnectionException, IOException {
        try {
            this.objectSocket.write(user);
            User userWithId = this.objectSocket.read();
            if (userWithId == null) {
                throw new ConnectionException("User " + user.getPseudo() + " not connected");
            }
            return userWithId;
        }catch(IOException | ClassNotFoundException e) {
            throw new IOException(e);
        }
    }

    public void sendMessage(Message msg) throws IOException {
        this.objectSocket.write(msg);
    }


    public interface Listener {
        void onMessageReceived(Message message);
    }

}
