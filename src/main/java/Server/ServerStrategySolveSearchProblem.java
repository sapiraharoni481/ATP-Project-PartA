package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Server strategy implementation for solving maze search problems.
 * This strategy receives a maze from the client, solves it using the configured
 * search algorithm, and returns the solution. It implements a two-level caching
 * system for improved performance:
 * 1. In-memory cache using ConcurrentHashMap for fast access
 * 2. Disk-based cache for persistent storage across server restarts
 *
 * Supported search algorithms:
 * - BreadthFirstSearch
 * - DepthFirstSearch
 * - BestFirstSearch (default)
 *
 * @author Sapir Aharoni
 * @version 1.0
 */
public class ServerStrategySolveSearchProblem implements IServerStrategy {

    // Static cache to store maze solutions
    private static final ConcurrentHashMap<String, Solution> solutionsCache = new ConcurrentHashMap<>();

    /**
     * Applies the maze solving strategy by receiving a maze from the client,
     * checking cache for existing solutions, solving if necessary, and returning
     * the solution to the client.
     *
     * Cache strategy:
     * 1. Check in-memory cache first
     * 2. Check disk cache if not in memory
     * 3. Solve maze if no cached solution exists
     * 4. Cache solution both in memory and on disk
     *
     * Protocol:
     * 1. Receives Maze object from client
     * 2. Generates unique hash for the maze
     * 3. Checks caches for existing solution
     * 4. Solves maze using configured algorithm if needed
     * 5. Sends Solution object back to client
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
                // Read maze from client
                Maze maze = (Maze) fromClient.readObject();

                // Calculate unique hash for the maze to use as a key for caching
                String mazeHash = getMazeHash(maze);

                // Check if solution already exists in cache
                if (solutionsCache.containsKey(mazeHash)) {
                    System.out.println("Solution found in cache!");
                    toClient.writeObject(solutionsCache.get(mazeHash));
                    toClient.flush();
                    return;
                }

                // Solution not in cache, check if it exists on disk
                String tempDirPath = System.getProperty("java.io.tmpdir");
                File solFile = new File(tempDirPath, "sol_" + mazeHash + ".dat");

                if (solFile.exists()) {
                    // Read solution from file
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(solFile))) {
                        Solution solution = (Solution) ois.readObject();
                        solutionsCache.put(mazeHash, solution); // Update cache
                        toClient.writeObject(solution);
                        toClient.flush();
                        return;
                    } catch (Exception e) {
                        // If failed to read from file, we'll solve it again
                        System.out.println("Failed to read solution from file, solving again...");
                    }
                }

                // Solve the maze
                ISearchable searchableMaze = new SearchableMaze(maze);
                ISearchingAlgorithm searchAlgorithm;

                String searchingAlgorithm = Configurations.getInstance().getMazeSearchingAlgorithm();

                switch (searchingAlgorithm) {
                    case "BreadthFirstSearch":
                        searchAlgorithm = new BreadthFirstSearch();
                        break;
                    case "DepthFirstSearch":
                        searchAlgorithm = new DepthFirstSearch();
                        break;
                    case "BestFirstSearch":
                        searchAlgorithm = new BestFirstSearch();
                        break;
                    default:
                        searchAlgorithm = new BestFirstSearch();
                        break;
                }

                Solution solution = searchAlgorithm.solve(searchableMaze);

                // Cache the solution in memory
                solutionsCache.put(mazeHash, solution);

                // Save the solution to disk
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(solFile))) {
                    oos.writeObject(solution);
                    oos.flush();
                } catch (IOException e) {
                    System.out.println("Failed to save solution to disk: " + e.getMessage());
                }

                // Send solution to client
                toClient.writeObject(solution);
                toClient.flush();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a unique hash for a maze to use as a key for caching.
     * The hash includes maze dimensions, start/goal positions, and a checksum
     * of the maze structure to ensure uniqueness while keeping the hash compact.
     *
     * Hash components:
     * - Maze dimensions (rows x columns)
     * - Start position coordinates
     * - Goal position coordinates
     * - Checksum of entire maze structure
     *
     * @param maze the maze to generate a hash for
     * @return a string hash representing the unique maze configuration
     */
    private String getMazeHash(Maze maze) {
        StringBuilder sb = new StringBuilder();

        // Include maze dimensions
        int[][] mazeData = maze.getMaze();
        int rows = mazeData.length;
        int cols = mazeData[0].length;
        sb.append(rows).append("_").append(cols).append("_");

        // Include start and goal positions
        sb.append(maze.getStartPosition().toString()).append("_");
        sb.append(maze.getGoalPosition().toString()).append("_");

        // Include a sample of maze structure (to save space but still be unique enough)
        // We'll calculate a checksum rather than including the entire maze
        int checksum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                checksum = (checksum * 31 + mazeData[i][j]) % 1000000007;
            }
        }
        sb.append(checksum);

        return sb.toString();
    }
}