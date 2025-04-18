package be.fpluquet.chatfx.server;
import be.fpluquet.chatfx.common.models.Message;
import be.fpluquet.chatfx.common.models.User;
import be.fpluquet.chatfx.common.network.ObjectSocket;
import be.fpluquet.chatfx.common.network.ObjectSocketCreationException;
import be.fpluquet.chatfx.common.network.Protocol;
import be.fpluquet.chatfx.server.repositories.UsersRepository;

import java.io.IOException;
import java.net.Socket;

public class ClientThread implements Runnable {

    private ObjectSocket os;
    private Server server;
    private UsersRepository usersRepository;

    public ClientThread(Socket socket, Server server, UsersRepository usersRepository) throws ObjectSocketCreationException {
        this.os = new ObjectSocket(socket);
        this.server = server;
        this.usersRepository = usersRepository;
    }
    @Override
    public void run() {
        try {
            System.out.println("Un client s'est connecté");
            User user = os.read();
            usersRepository.addUser(user);
            System.out.println("Le client a dit : " + user);
            // on va faire un broadcast
            os.write(user);

            while(true) {
                Message message = os.read();
                System.out.println("Le client a dit : " + message);
                // on va faire un broadcast
                server.broadcast(message);
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
