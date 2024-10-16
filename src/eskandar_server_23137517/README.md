# A step-by-step explanation of how to build the server.
--------------------------------------------------------

### 1. **Import necessary libraries**
   - Include libraries for handling input/output, networking (sockets), and data structures (e.g., lists).

### 2. **Define a custom exception**
   - Create a custom exception class that extends the `Exception` class. This will be used to handle incorrect actions sent by the client.

### 3. **Create the main `ToDoServer` class**
   - This class will handle the main server logic, including setting up the server socket and handling multiple client connections.

### 4. **Setup a server socket in the main method**
   - Initialize a `ServerSocket` on a specific port (e.g., 8081) to allow the server to listen for incoming client connections.

### 5. **Accept incoming client connections**
   - Continuously listen for client connections using the `accept()` method of the `ServerSocket`. Each accepted connection should be passed to a new thread.

### 6. **Handle each client with a new thread**
   - For every accepted connection, create a new thread to handle that client. This ensures that multiple clients can connect simultaneously without interfering with each other.

### 7. **Create a `ClientHandler` class**
   - This class should extend `Thread` and handle all communication between the server and a particular client. Each client will have its own instance of this class.

### 8. **Initialize client-specific task list**
   - Inside the `ClientHandler` class, create a synchronized list to store tasks for that specific client.

### 9. **Override the `run` method in the `ClientHandler` class**
   - In the `run` method, set up input/output streams to read client messages and send responses back to the client.

### 10. **Read client messages in a loop**
   - Use a loop to continuously read messages from the client. If the message is `"STOP"`, break the loop and close the connection.

### 11. **Process client messages**
   - Pass each received message to a separate method to handle actions like "add," "list," or "remove". Each action should update the client-specific task list.

### 12. **Handle invalid or empty messages**
   - If a client sends an empty message or a message in an incorrect format, throw your custom exception with a relevant error message.

### 13. **Split client messages into action and description**
   - For each valid message, split it into two parts: the action (e.g., "add", "list", "remove") and the description (e.g., the task to add or remove).

### 14. **Handle the "add" action**
   - Add the provided task to the client's task list and return a confirmation message to the client.

### 15. **Handle the "list" action**
   - If the client requests a list of tasks, return the tasks specific to that client. If there are no tasks, return a message indicating this.

### 16. **Handle the "remove" action**
   - If the client requests to remove a task, check if the task exists in the client's task list. If it does, remove it and return the updated list of tasks.

### 17. **Handle exceptions**
   - Use try-catch blocks to handle input/output errors and your custom exception. Ensure that any errors are caught and appropriate error messages are sent to the client.

### 18. **Close client socket**
   - After the client sends the "STOP" command or disconnects, close the socket to release resources.
