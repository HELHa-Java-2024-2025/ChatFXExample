package be.fpluquet.chatfx.network.server;
import be.fpluquet.chatfx.network.common.ObjectSocket;
import be.fpluquet.chatfx.network.common.ObjectSocketCreationException;

import java.io.IOException;
import java.net.Socket;

public class ClientThread implements Runnable {

    private ObjectSocket os;
    private Server server;

    public ClientThread(Socket socket, Server server) throws ObjectSocketCreationException {
        this.os = new ObjectSocket(socket);
        this.server = server;
    }
    @Override
    public void run() {
        try {
            System.out.println("Un client s'est connecté");

            while(true) {
                String string = os.read();
                System.out.println("Le client a dit : " + string);
                // on va faire un broadcast
                server.broadcast(string);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors de la comminucation avec le client.");
            e.printStackTrace();
        }
    }

    public void write(Object object) throws IOException {
        this.os.write(object);
    }
}
