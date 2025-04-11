package be.fpluquet.chatfx.network.client;

import be.fpluquet.chatfx.network.common.ObjectSocket;
import be.fpluquet.chatfx.network.common.ObjectSocketCreationException;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        new Client().go();
    }

    public void go() {
        try {
            Socket socket = new Socket("localhost", 1099);
            ObjectSocket objectSocket = new ObjectSocket(socket);
            Thread thread = new Thread(new ReadMessagesThread(objectSocket));
            thread.start();
            String message = "";
            while(!message.equals("quit")) {
                System.out.print("Votre message : ");
                Scanner scanner = new Scanner(System.in);
                message = scanner.nextLine();

                if(!message.equals("quit")) {
                    objectSocket.write(message);
                }
            }
            System.out.println("Fermeture de la connexion...");
            objectSocket.close();


        } catch (IOException e) {
            System.err.println("Erreur lors de la connexion au serveur. Est-ce que le serveur est démarré ?");
        } catch (ObjectSocketCreationException e) {
            System.err.println("Erreur lors de la création du ObjectSocket.");
        }
    }
}
