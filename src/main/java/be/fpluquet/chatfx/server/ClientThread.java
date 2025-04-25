package be.fpluquet.chatfx.server;
import be.fpluquet.chatfx.common.models.Message;
import be.fpluquet.chatfx.common.models.User;
import be.fpluquet.chatfx.common.network.ObjectSocket;
import be.fpluquet.chatfx.common.network.ObjectSocketCreationException;
import be.fpluquet.chatfx.common.network.Protocol;
import be.fpluquet.chatfx.common.network.commands.AbstractCommand;
import be.fpluquet.chatfx.common.network.commands.ChangeUsernameCommand;
import be.fpluquet.chatfx.common.network.commands.CommandDispatch;
import be.fpluquet.chatfx.common.network.commands.SendMessageCommand;
import be.fpluquet.chatfx.server.repositories.UsersRepository;

import java.io.IOException;
import java.net.Socket;

public class ClientThread implements Runnable, CommandDispatch {

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
                AbstractCommand command = os.read();
                System.out.println("Un client a envoyé une commande : " + command);
                if (command == null) {
                    System.out.println("Le client a fermé la connexion.");
                    break;
                }
                command.executeOn(this);

            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors de la comminucation avec le client.");
            e.printStackTrace();
        }
    }

    @Override
    public void onCommand(SendMessageCommand command) {
        System.out.println("Le client a dit : " + command.getMessage());
        // on va faire un broadcast
        server.broadcast(command);
    }

    @Override
    public void onCommand(ChangeUsernameCommand command) {
        // user.setName(changeUsernameCommand.getNewUsername());
        System.out.println("Le client a changé de pseudo : " + command.getNewUsername());
        server.broadcast(command);
    }

    public void write(Object object) throws IOException {
        this.os.write(object);
    }
}
