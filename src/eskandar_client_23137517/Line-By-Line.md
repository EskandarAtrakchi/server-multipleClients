# Line-By-Line of how to code the client side
---------------------------------------------

### 1. **Import necessary libraries**
   - Include libraries for input/output handling, networking (sockets), and user input (scanner).

### 2. **Define the main `ToDoClient` class**
   - This class will handle all client-side logic for connecting to the server, sending commands, and receiving responses.

### 3. **Establish a connection to the server**
   - Inside the `main` method, create a `Socket` object to connect to the server. Use `InetAddress.getLocalHost()` to connect to the local server running on port 8081.

### 4. **Set up input/output streams**
   - Initialize a `BufferedReader` to receive messages from the server and a `PrintWriter` to send messages to the server. These streams will be used to communicate between the client and the server.

### 5. **Create a `Scanner` for user input**
   - Use a `Scanner` to read user input from the console. The client will use this input to send commands to the server.

### 6. **Print a message indicating successful connection**
   - Display a message on the client console indicating that the client has successfully connected to the server and is ready to accept commands.

### 7. **Enter a loop to process user commands**
   - Use a `while (true)` loop to continuously prompt the user for input. This loop will allow the client to send multiple commands to the server.

### 8. **Prompt the user for an action and description**
   - Inside the loop, prompt the user to input an action (e.g., "add", "list", "remove") and a description for the task. Read the input using the `Scanner`.

### 9. **Trim user input**
   - After receiving input, use the `trim()` method to remove any leading or trailing whitespace.

### 10. **Validate the user input**
   - If the user input is empty, print an error message and continue to the next iteration of the loop, prompting the user to enter a valid command.

### 11. **Send the user input to the server**
   - Use the `PrintWriter` object to send the user's input to the server. This input will be processed by the server.

### 12. **Handle the "STOP" command**
   - If the user enters "STOP", break out of the loop and end the client session. This will signal the client to stop sending requests to the server.

### 13. **Receive the server's response**
   - Use the `BufferedReader` to read the server's response to the client. The server will send feedback based on the user's command.

### 14. **Print the server's response**
   - After receiving the server's response, print the message on the client console to inform the user of the result (e.g., "Task added: a").

### 15. **Handle communication errors**
   - Use a `try-catch` block to handle any `IOException` that might occur during communication (e.g., if the connection to the server fails).

### 16. **Close the client socket**
   - After the user sends the "STOP" command or the client disconnects, ensure that the client socket is properly closed to release resources.
   