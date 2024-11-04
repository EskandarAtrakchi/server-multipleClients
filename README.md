# Client Code Snippets Explained

1. In try block I have used resources as a parameter try-with-resources statement to automatically close the resources.



```java
try (Socket socket = new Socket(InetAddress.getLocalHost(), 8081);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {
```



2. Just in case if the host is unknown, I have used the exception to catch the unknown host in a catch block, if the host is unknown, print an error message, I have used the UnknownHostException on the client side only since this is client related issue.

```java
catch (UnknownHostException e) {
    System.out.println("Unknown host: " + e.getMessage());
}
```



3. have used getLocalHost() method&#x20;

```java
Socket socket = new Socket(InetAddress.getLocalHost(), 8081);
```

