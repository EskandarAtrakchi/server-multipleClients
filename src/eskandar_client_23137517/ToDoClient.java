package eskandar_client_23137517;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ToDoClient {
    public static void main(String[] args) {
        // Try-with-resources statement to automatically close the resources
        try (Socket socket = new Socket(InetAddress.getLocalHost(), 8081);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {
            // Print a message to the user
            System.out.println("Connected to server. Type your commands.");
            String userInput;
            // Loop to keep the client running until the user types "STOP"
            while (true) {
                System.out.print("Enter action and description (or STOP to quit): ");
                userInput = scanner.nextLine().trim();  // Trim any leading or trailing whitespace

                if (userInput.isEmpty()) {
                    System.out.println("Input cannot be empty. Please enter a valid action and description.");
                    continue;  // Skip to the next iteration if the input is empty
                }
                // This is an instance of PrintStream type
                out.println(userInput);
                // If the user types "STOP", break out of the loop
                if (userInput.equals("STOP")) break;
                // Read the server's response and print it to the console
                String response = in.readLine();
                System.out.println("Server response: " + response);
            }
            // Catch any exceptions that may occur
        // If the host is unknown, print an error message, I will use the UnknownHostException on the client side only since this is client related issue
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error communicating with the server: " + e.getMessage());
        }
    }
}
