# Server Code Snippets Explained

1. I have used custom exception class to handle incorrect actions, I will be able to throw this exception in the handleClientMessage method, this is important to make it flexable to throw exceptions instead of specify them in try and catch blocks

```java
class IncorrectActionException extends Exception {
    // Constructor that accepts a message
    public IncorrectActionException(String message) {
        // Call the super class constructor
        super(message);
    } 
}
```



2. Used the finally block to either close the connection or catch error as the finally block to close the client socket, will be executed regardless of any exceptions.

```java
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
```



3.
