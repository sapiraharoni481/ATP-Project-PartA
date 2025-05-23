package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * A multi-threaded server implementation that handles client connections
 * using a configurable strategy pattern and thread pool for concurrent processing.
 *
 * The server listens on a specified port and processes client requests using
 * the provided IServerStrategy implementation. Each client connection is handled
 * in a separate thread from a thread pool to ensure concurrent processing.
 *
 * @author Sapir Aharoni
 * @version 1.0
 */
public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
    private ExecutorService threadPool; // thread pool for handling clients

    /**
     * Constructs a new Server with the specified configuration.
     * The thread pool size is read from the Configurations singleton.
     *
     * @param port the port number on which the server will listen
     * @param listeningIntervalMS the timeout interval in milliseconds for socket operations
     * @param strategy the strategy implementation for handling client communication
     */
    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        // Read thread pool size from configurations
        int threadPoolSize = Configurations.getInstance().getThreadPoolSize();
        this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
    }

    /**
     * Starts the server in a new thread.
     * The server will begin listening for client connections asynchronously.
     */
    public void start() {
        new Thread(() -> {
            runServer();
        }).start();
    }

    /**
     * Main server loop that accepts client connections and delegates
     * them to the thread pool for processing.
     */
    private void runServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            System.out.println("Starting server at port = " + port);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client accepted: " + clientSocket.toString());

                    // Handle the client in a separate thread from the thread pool
                    threadPool.execute(() -> {
                        handleClient(clientSocket);
                    });

                } catch (SocketTimeoutException e) {
                    System.out.println("Socket timeout - No clients pending!");
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles an individual client connection by applying the configured strategy
     * and properly closing the connection when finished.
     *
     * @param clientSocket the socket connection to the client
     */
    private void handleClient(Socket clientSocket) {
        try {
            strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gracefully stops the server and shuts down the thread pool.
     * Waits for existing tasks to complete before forcing termination if necessary.
     */
    public void stop() {
        System.out.println("Stopping server...");
        stop = true;

        // Shutdown the thread pool gracefully
        threadPool.shutdown();
        try {
            // Wait a certain time for existing tasks to terminate
            if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                // Force termination if tasks don't finish in time
                threadPool.shutdownNow();
                if (!threadPool.awaitTermination(10, TimeUnit.SECONDS))
                    System.err.println("Thread pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            threadPool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}