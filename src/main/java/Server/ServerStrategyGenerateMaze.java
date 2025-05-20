package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {
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