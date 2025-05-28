package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * A client class that establishes connection with a server and executes a specific communication strategy.
 * This class implements the Strategy pattern where the actual communication logic is delegated
 * to an IClientStrategy implementation, allowing for flexible and reusable client behavior.
 *
 * The client handles the socket connection establishment and cleanup, while the strategy
 * defines what data to send and how to process the server response.
 *
 * @author sapir aharoni
 * @version 1.0
 */
public class Client {
    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy strategy;

    /**
     * Constructs a new Client with the specified server connection details and communication strategy.
     *
     * @param serverIP The IP address of the server to connect to
     * @param serverPort The port number on which the server is listening
     * @param strategy The communication strategy that defines how to interact with the server
     */
    public Client(InetAddress serverIP, int serverPort, IClientStrategy strategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.strategy = strategy;
    }

    /**
     * Establishes a connection to the server and executes the communication strategy.
     * This method:
     * 1. Creates a socket connection to the specified server IP and port
     * 2. Prints connection confirmation message
     * 3. Delegates the actual communication to the provided strategy
     * 4. Automatically closes the socket connection using try-with-resources
     *
     * The socket streams (InputStream and OutputStream) are passed to the strategy
     * for handling the specific communication protocol.
     *
     * @throws IOException if an error occurs during socket creation or communication
     */
    public void communicateWithServer() {
        try (Socket serverSocket = new Socket(serverIP, serverPort)) {
            System.out.println("Connected to server - IP = " + serverIP + ", Port = " + serverPort);
            strategy.clientStrategy(serverSocket.getInputStream(), serverSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}