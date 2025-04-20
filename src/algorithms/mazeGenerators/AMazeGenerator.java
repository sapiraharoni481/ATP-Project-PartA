package algorithms.mazeGenerators;

/**
 * Abstract class representing a maze generator.
 * Implements basic functionality for measuring the time it takes to generate a maze.
 * Specific generation logic is to be implemented by subclasses.
 *
 * @author Sapir
 * @version 1.0
 * @since 2025-04-10
 */
public abstract class AMazeGenerator implements IMazeGenerator {

    /**
     * Measures the time (in milliseconds) required to generate a maze
     * with the specified number of rows and columns.
     *
     * @param rows    Number of rows in the maze.
     * @param columns Number of columns in the maze.
     * @return Time taken to generate the maze, in milliseconds.
     */
    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long startTime = System.currentTimeMillis();
        generate(rows, columns);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    /**
     * Generates a new maze with the specified dimensions.
     * Subclasses must provide an implementation of this method.
     *
     * @param rows    Number of rows in the maze.
     * @param columns Number of columns in the maze.
     * @return A new Maze object.
     */
    @Override
    public abstract Maze generate(int rows, int columns);
}
