package be.fpluquet.chatfx.server;

import be.fpluquet.chatfx.common.network.ObjectSocketCreationException;
import be.fpluquet.chatfx.common.network.Protocol;
import be.fpluquet.chatfx.server.repositories.UsersRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static void main(String[] args) {
        new Server().go();
    }

    List<ClientThread> clients = new ArrayList<>();
    UsersRepository usersRepository = new UsersRepository();

    public void go() {
        try {
            ServerSocket server = new ServerSocket(Protocol.SERVER_PORT);
            System.out.println("Serveur démarré sur le port " + Protocol.SERVER_PORT);
            while (true) {
                System.out.println("En attente d'un client...");
                Socket socket = server.accept();
                startClientThread(socket);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du serveur. Est-ce que le port 1099 est déjà utilisé ?");
            e.printStackTrace();
        }

    }

    private void startClientThread(Socket socket) {
        try {
            ClientThread clientThread = new ClientThread(socket, this, this.usersRepository);
            Thread thread = new Thread(clientThread);
            clients.add(clientThread);
            thread.start();
        } catch (ObjectSocketCreationException e) {
            // le client est mort, on laisse tomber...
        }
    }

    public synchronized void broadcast(Object message) {
        for(ClientThread client : clients) {
            try {
                System.out.println("Envoi du message " + message + " au client " + client);
                client.write(message);
            } catch (IOException e) {
                System.err.println("Erreur lors de l'envoi du message au client.");
                e.printStackTrace();
                // on va retirer le client de la liste
                clients.remove(client);
            }
        }
    }
}
