package univ_lorraine.iut.java.privatechat.controller;
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 12345;
        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("Serveur en attente de connexion...");
s
        Socket socket = serverSocket.accept();
        System.out.println("Client connecté : " + socket.getInetAddress());

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        String message;
        while ((message = input.readLine()) != null) {
            System.out.println("Message reçu: " + message);
            output.println("Serveur: " + message);
            if (message.equalsIgnoreCase("quit")) {
                break;
            }
        }

        System.out.println("Fermeture de la connexion.");
        input.close();
        output.close();
        socket.close();
        serverSocket.close();
    }
}

