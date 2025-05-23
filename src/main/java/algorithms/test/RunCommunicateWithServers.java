package algorithms.test;

import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.AState;
import algorithms.search.Solution;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Main class for running servers and clients for maze generation and solving.
 * This class demonstrates client-server communication in three domains:
 * 1. Maze generation
 * 2. Search problem solving for mazes
 * 3. String reversal (commented out)
 *
 * The class initializes multiple servers, communicates with them using different
 * client strategies, and then properly shuts them down.
 *
 * @author Your Name
 * @version 1.0
 */
public class RunCommunicateWithServers {

    /**
     * Main method that starts all servers and performs communication with them.
     * The servers run on different ports:
     * - Port 5400: Maze generation server
     * - Port 5401: Search problem solving server
     * - Port 5402: String reverser server (commented out)
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Initializing servers
        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        // Server stringReverserServer = new Server(5402, 1000, new ServerStrategyStringReverser());

        // Starting servers
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
        // stringReverserServer.start();

        // Communicating with servers
        CommunicateWithServer_MazeGenerating();
        CommunicateWithServer_SolveSearchProblem();
        // CommunicateWithServer_StringReverser();

        // Stopping all servers
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
        // stringReverserServer.stop();
    }

    /**
     * Communicates with the maze generation server to create a new maze.
     * This method:
     * 1. Connects to the maze generation server on port 5400
     * 2. Sends maze dimensions (50x50) to the server
     * 3. Receives a compressed maze from the server
     * 4. Decompresses the maze using MyDecompressorInputStream
     * 5. Creates a Maze object and prints it
     *
     * Uses ObjectOutputStream and ObjectInputStream for communication.
     */
    private static void CommunicateWithServer_MazeGenerating() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();

                        int[] mazeDimensions = new int[]{50, 50};
                        toServer.writeObject(mazeDimensions); // Send maze dimensions to server
                        toServer.flush();

                        byte[] compressedMaze = (byte[]) fromServer.readObject(); // Read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[2512]; // NOTE: Change size according to your maze size
                        is.read(decompressedMaze); // Fill decompressedMaze with bytes

                        Maze maze = new Maze(decompressedMaze);
                        maze.print();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * Communicates with the search problem solving server to find a solution for a maze.
     * This method:
     * 1. Generates a new maze locally using MyMazeGenerator
     * 2. Prints the generated maze
     * 3. Connects to the solve search problem server on port 5401
     * 4. Sends the maze to the server
     * 5. Receives a Solution object containing the path through the maze
     * 6. Prints the solution steps
     *
     * Uses ObjectOutputStream and ObjectInputStream for communication.
     */
    private static void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();

                        MyMazeGenerator mg = new MyMazeGenerator();
                        Maze maze = mg.generate(50, 50);
                        maze.print();

                        toServer.writeObject(maze); // Send maze to server
                        toServer.flush();

                        Solution mazeSolution = (Solution) fromServer.readObject(); // Read maze solution from server

                        // Print Maze Solution retrieved from the server
                        System.out.println(String.format("Solution steps: %s", mazeSolution));
                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                        for (int i = 0; i < mazeSolutionSteps.size(); i++) {
                            System.out.println(String.format("%s. %s", i, mazeSolutionSteps.get(i).toString()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * Communicates with the string reverser server to reverse a string.
     * This method:
     * 1. Connects to the string reverser server on port 5402
     * 2. Sends a test message to the server
     * 3. Receives the reversed string from the server
     * 4. Prints the server response
     *
     * Uses BufferedReader and PrintWriter for text-based communication.
     *
     * Note: This method is currently not called in main() as the server is commented out.
     */
    private static void CommunicateWithServer_StringReverser() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5402, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        BufferedReader fromServer = new BufferedReader(new InputStreamReader(inFromServer));
                        PrintWriter toServer = new PrintWriter(outToServer);

                        String message = "Client Message";
                        String serverResponse;

                        toServer.write(message + "\n");
                        toServer.flush();

                        serverResponse = fromServer.readLine();
                        System.out.println(String.format("Server response: %s", serverResponse));

                        toServer.flush();
                        fromServer.close();
                        toServer.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}