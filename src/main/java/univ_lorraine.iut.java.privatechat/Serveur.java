package univ_lorraine.iut.java.privatechat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public final class Serveur implements Runnable{
    private static String id;
    private static ServerSocket server;
    private static int port = 12345;
    private static Boolean running = true;

    /**
     * Vérifie si les arguments de la ligne de commande sont passés et crée le socket pour écouter les connexions
     * @param args Arguments de la ligne de commande
     */
    private static void handleArgs(String[] args) {
        id = "1";
        server = null;
        int port  = 12345;
        // Essaie de créer un ServerSocket
        try {
            server = new ServerSocket(port);
        } catch (Exception e) {
            try {
                server = new ServerSocket(port + 1);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println(e.getMessage());
        }
    }

    /**
     * Fonction qui gère un client particulier
     * Ouvre un client et retourne un thread
     * @param socket Socket représentant un client
     * @return Thread créé pour gérer le client
     */
    private static Thread handleConnection(Socket socket) {
        System.out.println("Demande de connexion depuis : " + socket.getRemoteSocketAddress().toString());
        Thread th = new ClientThread(socket, id);
        try {
            th.start();
        } catch (Exception e) {
            return null;
        }
        return th;
    }

    public void run() {
        String[] args = {""};
        handleArgs(args);
        if (server == null) {
            System.out.println("Impossible d'initier un serveur");
            System.exit(1);
        }
        ArrayList<Thread> clientThreads = new ArrayList<>();
        // Le serveur est prêt
        /* Documentation */
        System.out.println("Serveur démarré à : " + server.getLocalPort());
        int connections = 1;
        // La boucle principale pour écouter les connexions
        while (connections > 0) {
            Socket socket = null;
            try {
                socket = server.accept();
            } catch (Exception e) {
                System.out.println("Impossible d'accepter la demande");
            }
            if (socket != null) {
                Thread newClient = handleConnection(socket);
                if(newClient != null)
                    clientThreads.add(newClient);
                else
                    System.out.println("La connexion du client a été refusée, impossible d'allouer un nouveau thread");
                connections--;
            } else {
                System.out.println("Erreur de connexion");
            }
        }
        for (Thread clientThread : clientThreads) {
            try {
                clientThread.join();
            } catch (InterruptedException e) {
                System.out.println("Un thread client a été interrompu");
            }
        }
        // Ferme le serveur/ le socket d'écoute
        if (server != null) {
            try {
                server.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }
    }

    /**
     * Fonction principale pour démarrer un client
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        //rien
    }
}
