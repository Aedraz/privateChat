package univ_lorraine.iut.java.privatechat.controller;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        String serverAddress = "127.0.0.1";
        int port = 12345;

        Socket socket = new Socket(serverAddress, port);
        System.out.println("Connexion établie avec le serveur.");

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        String userInput;
        while (true) {
            System.out.print("Entrez un message (tapez 'quit' pour quitter) : ");
            userInput = keyboard.readLine();

            output.println(userInput);
            if (userInput.equalsIgnoreCase("quit")) {
                break;
            }

            String serverResponse = input.readLine();
            System.out.println("Réponse du serveur: " + serverResponse);
        }

        System.out.println("Fermeture de la connexion.");
        keyboard.close();
        input.close();
        output.close();
        socket.close();
    }
}
