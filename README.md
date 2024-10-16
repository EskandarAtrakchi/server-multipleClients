# Getting Started 
------------------

## Server Steps (`ToDoServer`)

1. **Define Package**: 
   - Organizes the server class within the `eskandar_server_23137517` package.

2. **Import Libraries**: 
   - Imports necessary Java libraries for input/output, networking, and utilities.

3. **Custom Exception Class**: 
   - Defines `IncorrectActionException` to handle errors related to invalid actions in client messages.

4. **Declare Main Class**: 
   - Begins the definition of the `ToDoServer` class.

5. **Create Synchronized List**:
   - Initializes a synchronized list called `tasks` to store TODO items safely across multiple threads.

6. **Main Method Execution**:
   - Starts the execution of the server in the `main` method.

7. **Create ServerSocket**:
   - Creates a new `ServerSocket` that listens for incoming connections on port `8081`.

8. **Print Server Status**:
   - Outputs a message indicating that the server has started and is waiting for clients.

9. **Infinite Loop for Client Connections**:
   - Enters an infinite loop to continuously accept new client connections.

10. **Accept Client Connection**:
    - Waits for a client to connect and creates a `Socket` object for that connection.

11. **Start ClientHandler Thread**:
    - For each connected client, starts a new `ClientHandler` thread to manage communication.

12. **Handle Exceptions**:
    - Catches and prints any `IOException` that occurs while starting the server or during communication.

## Client Steps (`ToDoClient`)

1. **Define Package**:
   - Organizes the client class within the `eskandar_client_23137517` package.

2. **Import Libraries**:
   - Imports necessary Java libraries for input/output, networking, and reading user input.

3. **Declare Main Class**:
   - Begins the definition of the `ToDoClient` class.

4. **Main Method Execution**:
   - Starts the execution of the client in the `main` method.

5. **Create Socket Connection**:
   - Creates a new `Socket` connection to the server running on localhost at port `8081`.

6. **Initialize Input/Output Streams**:
   - Sets up `BufferedReader` to read messages from the server and `PrintWriter` to send messages to the server.

7. **Initialize Scanner for User Input**:
   - Creates a `Scanner` to read user commands from the console.

8. **Print Connection Status**:
   - Outputs a message indicating that the client is connected to the server.

9. **Infinite Loop for User Input**:
   - Enters an infinite loop to continuously read user input.

10. **Prompt User for Input**:
    - Displays a message asking the user to enter an action and description or to stop.

11. **Read User Input**:
    - Reads the input from the user and trims any leading or trailing whitespace.

12. **Validate User Input**:
    - Checks if the input is empty; if so, prompts the user to provide valid input and continues to the next iteration.

13. **Send User Input to Server**:
    - Sends the user’s command to the server via the output stream.

14. **Check for STOP Command**:
    - If the user input is "STOP," breaks out of the loop, ending the client program.

15. **Read Server Response**:
    - Waits for a response from the server and reads it.

16. **Display Server Response**:
    - Prints the server's response to the console.

17. **Handle Exceptions**:
    - Catches and prints any `IOException` that occurs during communication with the server.