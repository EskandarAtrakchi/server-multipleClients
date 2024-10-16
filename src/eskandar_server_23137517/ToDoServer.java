package eskandar_server_23137517;

import java.io.*;
import java.net.*;
import java.util.*;

class IncorrectActionException extends Exception {
    public IncorrectActionException(String message) {
        super(message);
    } 
}

class ToDoServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8081)) {
            System.out.println("Server started, waiting for clients...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start(); // Handle each client with a new thread
            }
        } catch (IOException e) {
            System.out.println("Error starting the server: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private List<String> clientTasks; // Each client will have its own task list

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            this.clientTasks = Collections.synchronizedList(new ArrayList<>()); // Initialize a new task list for each client
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String clientMessage;
                while ((clientMessage = in.readLine()) != null) {
                    if (clientMessage.equals("STOP")) {
                        out.println("TERMINATE");
                        break;
                    }
                    try {
                        String response = handleClientMessage(clientMessage);
                        out.println(response);
                    } catch (IncorrectActionException e) {
                        out.println(e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.out.println("Error handling client: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Error closing client socket: " + e.getMessage());
                }
            }
        }

        private String handleClientMessage(String message) throws IncorrectActionException {
            if (message == null || message.trim().isEmpty()) {
                throw new IncorrectActionException("Invalid message format! Message cannot be empty.");
            }

            String[] parts = message.split(";", 2); // Split into action and description
            if (parts.length < 2) {
                throw new IncorrectActionException("Invalid message format! Correct format: action; description");
            }

            String action = parts[0].trim();
            String description = parts[1].trim();

            if (action.equals("add")) {
                synchronized (clientTasks) {
                    clientTasks.add(description);
                }
                return "Task added: " + description;
            } else if (action.equals("list")) {
                synchronized (clientTasks) {
                    return clientTasks.isEmpty() ? "No tasks available" : String.join("; ", clientTasks);
                }
            } else if (action.equals("remove")) {
                synchronized (clientTasks) {
                    if (clientTasks.remove(description)) {
                        return "Task removed: " + description;
                    } else {
                        return "Task not found: " + description;
                    }
                }
            } else {
                throw new IncorrectActionException("Incorrect action: " + action);
            }
        }
    }
}
