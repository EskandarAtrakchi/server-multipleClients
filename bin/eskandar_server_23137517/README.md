### Server Code: `ToDoServer`

```java
package eskandar_server_23137517;
```
- This line defines the package name for the Java class. A package is like a folder that helps organize related classes.

```java
import java.io.*;
import java.net.*;
import java.util.*;
```
- These lines import necessary classes from Java's libraries:
  - `java.io.*` allows input and output through data streams.
  - `java.net.*` is for networking, including sockets.
  - `java.util.*` is for utility classes, such as lists.

```java
class IncorrectActionException extends Exception {
    public IncorrectActionException(String message) {
        super(message);
    } 
}
```
- This block defines a custom exception class called `IncorrectActionException`, which extends Java’s built-in `Exception` class. It allows the program to throw an error when an invalid action is performed.
- The constructor takes a message as an argument and passes it to the superclass (Exception).

```java
class ToDoServer {
```
- This line declares the main class of the server application called `ToDoServer`.

```java
    private static List<String> tasks = Collections.synchronizedList(new ArrayList<>());
```
- Here, we create a synchronized list named `tasks` that will store TODO tasks. The `synchronizedList` method ensures that multiple threads can safely add or read from this list without causing data corruption.

```java
    public static void main(String[] args) {
```
- This is the entry point of the Java application. The `main` method is where the program starts executing.

```java
        try (ServerSocket serverSocket = new ServerSocket(8081)) {
```
- This line creates a new `ServerSocket` that listens for incoming connections on port `8081`. The `try-with-resources` statement ensures that the socket will be closed automatically when done.

```java
            System.out.println("Server started, waiting for clients...");
```
- This prints a message indicating that the server is running and ready to accept client connections.

```java
            while (true) {
```
- This begins an infinite loop to continuously accept client connections.

```java
                Socket clientSocket = serverSocket.accept();
```
- This line waits for a client to connect. When a client connects, it creates a new `Socket` object (`clientSocket`) that represents the connection to that client.

```java
                new ClientHandler(clientSocket).start();
```
- A new thread is created for handling the connected client, and it starts immediately. `ClientHandler` is a nested class defined later that manages communication with a specific client.

```java
        } catch (IOException e) {
            System.out.println("Error starting the server: " + e.getMessage());
        }
```
- This catch block handles any `IOException` that might occur when creating the server socket or during network communication, printing an error message.

### ClientHandler Class

```java
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
```
- This declares a nested class `ClientHandler` that extends `Thread`, allowing it to run concurrently. It has a `Socket` field to hold the client’s socket connection.

```java
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
```
- This is the constructor of `ClientHandler`. It initializes the `clientSocket` with the socket passed when the `ClientHandler` is created.

```java
        @Override
        public void run() {
```
- The `run` method is overridden from the `Thread` class and contains the code that will execute in the new thread for handling the client.

```java
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
```
- This creates a `BufferedReader` (`in`) to read messages from the client and a `PrintWriter` (`out`) to send messages back to the client. The `true` parameter enables auto-flushing, meaning data will be sent immediately without needing to call `flush()`.

```java
                String clientMessage;
                while ((clientMessage = in.readLine()) != null) {
```
- This initializes a variable `clientMessage` and continuously reads lines from the client until the connection is closed (i.e., `in.readLine()` returns `null`).

```java
                    if (clientMessage.equals("STOP")) {
                        out.println("TERMINATE");
                        break;
                    }
```
- If the client sends the message "STOP", the server responds with "TERMINATE" and breaks out of the loop, ending the communication.

```java
                    try {
                        String response = handleClientMessage(clientMessage);
                        out.println(response);
                    } catch (IncorrectActionException e) {
                        out.println(e.getMessage());
                    }
```
- This block tries to handle the client message using the `handleClientMessage` method. If that method throws an `IncorrectActionException`, the error message is sent back to the client.

```java
            } catch (IOException e) {
                System.out.println("Error handling client: " + e.getMessage());
            }
```
- This catch block handles any `IOException` that occurs while communicating with the client.

```java
            finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Error closing client socket: " + e.getMessage());
                }
            }
```
- The `finally` block is executed after the try-catch blocks, regardless of whether an exception occurred. It attempts to close the `clientSocket`, handling any exceptions that may occur during the closing process.

### Handle Client Messages

```java
        private String handleClientMessage(String message) throws IncorrectActionException {
```
- This method processes the client's message and may throw an `IncorrectActionException` if the message format is invalid.

```java
            if (message == null || message.trim().isEmpty()) {
                throw new IncorrectActionException("Invalid message format! Message cannot be empty.");
            }
```
- This checks if the message is empty or only whitespace. If so, it throws an exception indicating that the message is invalid.

```java
            String[] parts = message.split(";", 2);
```
- This splits the incoming message into two parts at the first semicolon (`;`): the action and the description. The `2` limits the split to two parts.

```java
            if (parts.length < 2) {
                throw new IncorrectActionException("Invalid message format! Correct format: action; description");
            }
```
- This checks if there are at least two parts (action and description). If not, it throws an exception indicating the correct format.

```java
            String action = parts[0].trim();
            String description = parts[1].trim();
```
- These lines extract the action and description from the parts, removing any extra whitespace.

```java
            if (action.equals("add")) {
                synchronized (tasks) {
                    tasks.add(description);
                }
                return "Task added: " + description;
```
- If the action is "add", the task description is added to the synchronized `tasks` list. The method returns a confirmation message.

```java
            } else if (action.equals("list")) {
                synchronized (tasks) {
                    return tasks.isEmpty() ? "No tasks for this date" : String.join("; ", tasks);
                }
```
- If the action is "list", the method checks if there are any tasks. It returns either a message saying there are no tasks or a string of tasks joined by semicolons.

```java
            } else {
                throw new IncorrectActionException("Incorrect action: " + action);
            }
```
- If the action is neither "add" nor "list", it throws an exception indicating the action is incorrect.

### Client Code: `ToDoClient`

Now let’s go through the client code:

```java
package eskandar_client_23137517;
```
- This line defines the package name for the client application.

```java
import java.io.*;
import java.net.*;
import java.util.Scanner;
```
- These lines import the necessary classes for input/output operations, networking, and for using the `Scanner` class to read user input.

```java
public class ToDoClient {
```
- This declares the main class of the client application named `ToDoClient`.

```java
    public static void main(String[] args) {
```
- This is the entry point of the Java client application where execution starts.

```java
        try (Socket socket = new Socket(InetAddress.getLocalHost(), 8081);
```
- This line creates a new socket connection to the server running on the local machine (localhost) at port `8081`. The `try-with-resources` ensures the socket will close automatically when done.

```java
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
```
- This initializes a `BufferedReader` (`in`) to read messages from the server.

```java
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
```
- This initializes a `PrintWriter` (`out`) to send messages to the server.

```java
             Scanner scanner = new Scanner(System.in)) {
```
- This initializes a `Scanner` to read user input from the keyboard.

```java
            System.out.println("Connected to server. Type your commands.");
```
- This prints a message to inform the

 user that they are connected to the server.

```java
            String userInput;
            while (true) {
```
- This initializes a variable `userInput` and starts an infinite loop to repeatedly read user commands.

```java
                System.out.print("Enter action and description (or STOP to quit): ");
                userInput = scanner.nextLine().trim();
```
- This prompts the user for input and reads the input line, trimming any leading or trailing whitespace.

```java
                if (userInput.isEmpty()) {
                    System.out.println("Input cannot be empty. Please enter a valid action and description.");
                    continue;
                }
```
- This checks if the input is empty. If it is, it informs the user and continues to the next iteration of the loop, skipping the sending part.

```java
                out.println(userInput);
```
- This sends the user input to the server.

```java
                if (userInput.equals("STOP")) break;
```
- If the user input is "STOP", it breaks out of the loop, ending the client program.

```java
                String response = in.readLine();
                System.out.println("Server response: " + response);
```
- This reads the response from the server and prints it to the console.

```java
        } catch (IOException e) {
            System.out.println("Error communicating with the server: " + e.getMessage());
        }
    }
}
```
- The `catch` block handles any `IOException` that might occur during communication with the server, printing an error message.

### Summary
- The **server code** creates a simple server that can accept client connections, handle messages to add or list tasks, and communicate back responses.
- The **client code** connects to this server, allows users to input commands, and sends them to the server while displaying the server's responses.