package algorithms.mazeGenerators;

/**
 * Interface for maze generator classes.
 * Provides methods to generate a maze and to measure the time it takes to generate it.
 *
 * Classes that implement this interface should provide specific maze generation algorithms.
 *
 * @author Sapir
 * @version 1.0
 * @since 2025-04-13
 */
public interface IMazeGenerator {

    /**
     * Generates a maze with the specified number of rows and columns.
     *
     * @param rows Number of rows in the maze.
     * @param columns Number of columns in the maze.
     * @return A Maze object containing the generated maze.
     */
    Maze generate(int rows, int columns);

    /**
     * Measures the time (in milliseconds) it takes to generate a maze
     * with the given dimensions.
     *
     * @param rows Number of rows in the maze.
     * @param columns Number of columns in the maze.
     * @return Time taken to generate the maze, in milliseconds.
     */
    long measureAlgorithmTimeMillis(int rows, int columns);
}
