package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;

/**
 * Server strategy implementation for generating mazes based on client requests.
 * This strategy receives maze dimensions from the client, generates a maze using
 * the configured algorithm, compresses the maze data, and sends it back to the client.
 *
 * The maze generation algorithm is determined by the server configuration and can be:
 * - MyMazeGenerator (default)
 * - SimpleMazeGenerator
 * - EmptyMazeGenerator
 *
 * @author Sapir Aharoni
 * @version 1.0
 */
public class ServerStrategyGenerateMaze implements IServerStrategy {

    /**
     * Applies the maze generation strategy by reading maze dimensions from the client,
     * generating a maze using the configured algorithm, compressing the result,
     * and sending the compressed maze back to the client.
     *
     * Protocol:
     * 1. Receives int[] array with maze dimensions [rows, columns]
     * 2. Generates maze using configured algorithm
     * 3. Compresses maze using MyCompressorOutputStream
     * 4. Sends compressed maze as byte array back to client
     *
     * @param inFromClient the input stream from the client
     * @param outToClient the output stream to the client
     */
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();

            try {
                // Read maze dimensions from client (expecting int[] of size 2)
                int[] mazeDimensions = (int[]) fromClient.readObject();

                // Generate maze based on client's request
                IMazeGenerator mazeGenerator;
                String generatingAlgorithm = Configurations.getInstance().getMazeGeneratingAlgorithm();

                switch (generatingAlgorithm) {
                    case "MyMazeGenerator":
                        mazeGenerator = new MyMazeGenerator();
                        break;
                    case "SimpleMazeGenerator":
                        mazeGenerator = new SimpleMazeGenerator();
                        break;
                    case "EmptyMazeGenerator":
                        mazeGenerator = new EmptyMazeGenerator();
                        break;
                    default:
                        mazeGenerator = new MyMazeGenerator();
                        break;
                }

                // Generate maze with the dimensions received from client
                Maze maze = mazeGenerator.generate(mazeDimensions[0], mazeDimensions[1]);

                // Compress maze and send it back to the client
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                MyCompressorOutputStream compressor = new MyCompressorOutputStream(byteArrayOutputStream);
                compressor.write(maze.toByteArray());
                compressor.flush();

                byte[] compressedMaze = byteArrayOutputStream.toByteArray();
                toClient.writeObject(compressedMaze);
                toClient.flush();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}