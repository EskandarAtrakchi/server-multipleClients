package eskandar_client_23137517;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ToDoClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket(InetAddress.getLocalHost(), 8081);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to server. Type your commands.");
            String userInput;
            while (true) {
                System.out.print("Enter action and description (or STOP to quit): ");
                userInput = scanner.nextLine().trim();  // Trim any leading or trailing whitespace

                if (userInput.isEmpty()) {
                    System.out.println("Input cannot be empty. Please enter a valid action and description.");
                    continue;  // Skip to the next iteration if the input is empty
                }

                out.println(userInput);

                if (userInput.equals("STOP")) break;

                String response = in.readLine();
                System.out.println("Server response: " + response);
            }

        } catch (IOException e) {
            System.out.println("Error communicating with the server: " + e.getMessage());
        }
    }
}
