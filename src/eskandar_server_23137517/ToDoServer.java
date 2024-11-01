package eskandar_server_23137517;

import java.io.*;
import java.net.*;
import java.util.*;

// Server class

// Custom exception class to handle incorrect actions, I will be able to throw this exception in the handleClientMessage method
class IncorrectActionException extends Exception {
    // Constructor that accepts a message
    public IncorrectActionException(String message) {
        // Call the super class constructor
        super(message);
    } 
}

class ToDoServer {
    public static void main(String[] args) {
        // Try-with-resources statement to automatically close the resources
        try (ServerSocket serverSocket = new ServerSocket(8081)) {
            // Print a message to the console
            System.out.println("Server started, waiting for clients...");
            // Loop to keep the server running
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start(); // Handle each client with a new thread
            }
            // Catch any exceptions that may occur
        } catch (IOException e) {
            System.out.println("Error starting the server: " + e.getMessage());
        }
    }
    // Inner class to handle each client
    private static class ClientHandler extends Thread {
        // Instance variables
        private Socket clientSocket;
        private List<String> clientTasks; // Each client will have its own task list

        // Constructor that accepts a socket
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            this.clientTasks = Collections.synchronizedList(new ArrayList<>()); // Initialize a new task list for each client
        }

        // Run method that will be executed when the thread starts
        @Override
        public void run() {
            // Try-with-resources statement to automatically close the resources
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String clientMessage;
                // Loop to keep the client running until the client types "STOP"
                while ((clientMessage = in.readLine()) != null) {
                    if (clientMessage.equals("STOP")) {
                        out.println("TERMINATE");
                        break;
                    }
                    // Handle the client message and send a response back
                    try {
                        String response = handleClientMessage(clientMessage);
                        out.println(response);
                    }
                    // using IncorrectActionException to catch any exceptions that may occur
                    catch (IncorrectActionException e) {
                        out.println(e.getMessage());
                    }
                }
            }
            // Catch any exceptions that may occur
            catch (IOException e) {
                System.out.println("Error handling client: " + e.getMessage());
            }
            // Finally block to close the client socket, will be executed regardless of any exceptions
            finally {
                // Close the client socket
                try {
                    clientSocket.close();
                }
                // Catch any exceptions that may occur
                catch (IOException e) {
                    System.out.println("Error closing client socket: " + e.getMessage());
                }
            }
        }

        // Method to handle the client message and return a response
        private String handleClientMessage(String message) throws IncorrectActionException {
            // Check if the message is null or empty
            if (message == null || message.trim().isEmpty()) {
                throw new IncorrectActionException("Invalid message format! Message cannot be empty.");
            }

            // Split the message into action and description
            String[] parts = message.split(";", 2);
            // Check if the message has at least two parts
            if (parts.length < 2) {
                // Throw an IncorrectActionException if the message format is invalid
                throw new IncorrectActionException("Invalid message format! Correct format: action; description");
            }

            // Trim any leading or trailing whitespace
            String action = parts[0].trim();
            String description = parts[1].trim();

            // Check the action and perform the corresponding operation
            if (action.equals("add")) {
                // Add the description to the task list
                synchronized (clientTasks) {
                    clientTasks.add(description);
                }
                // Return a response to the client
                return "Task added: " + description;
            }
            // If the action is "list", return the list of tasks
            else if (action.equals("list")) {
                // Return the list of tasks to the client
                synchronized (clientTasks) {
                    // If the task list is empty, return a message
                    return clientTasks.isEmpty() ? "No tasks available" : String.join("; ", clientTasks);
                }
            }
            // If the action is "remove", remove the task from the list
            else if (action.equals("remove")) {
                // Remove the description from the task list
                synchronized (clientTasks) {
                    // Check if the task was removed successfully
                    if (clientTasks.remove(description)) {
                        // Return a response to the client
                        return "Task removed: " + description;
                    }
                    // If the task was not found, return a message
                    else {
                        return "Task not found: " + description;
                    }
                }
            }
            // If the action is not recognized, throw an IncorrectActionException
            else {
                throw new IncorrectActionException("Incorrect action: " + action);
            }
        }
    }
}
