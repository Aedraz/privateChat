package univ_lorraine.iut.java.privatechat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Classe représentant la communication client avec le serveur
 */
public class ClientCommunication implements Runnable {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    /**
     * Constructeur de la classe
     * @param socket socket de communication
     */
    public ClientCommunication(Socket socket) {
        super();
        this.socket = socket;
    }

    /**
     * Fonction exécutée lors du démarrage du thread
     */
    @Override
    public void run() {
        try {
            // Initialisation des flux de communication
            this.ois = new ObjectInputStream(socket.getInputStream());
            this.oos = new ObjectOutputStream(socket.getOutputStream());

            // Récupération du premier message
            String message = (String) ois.readObject();
            System.out.println("Message reçu : " + message);

            // Saisie d'un message par l'utilisateur
            Scanner scanner = new Scanner(System.in);
            System.out.println("Saisie d'un message : ");
            String message2 = scanner.nextLine();

            // Envoi du message saisi
            oos.writeObject(message2);
        } catch (Exception e) {
            // En cas d'erreur, on ne fait rien
        }
    }

    /**
     * Fonction pour fermer les ressources
     */
    public void close() {
        try {
            // Fermeture des flux de communication et de la socket
            ois.close();
            oos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
