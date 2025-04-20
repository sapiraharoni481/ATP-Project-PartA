package algorithms.mazeGenerators;

/**
 * A simple maze generator that creates an empty maze with no walls.
 * All the cells in the maze are accessible (set to 0).
 * The start position is set to (0,0) and the goal position is set to (rows-1, columns-1).
 *
 * This class extends the class AMazeGenerator.
 *
 * @author Sapir
 * @version 1.0
 * @since 2025-04-13
 */
public class EmptyMazeGenerator extends AMazeGenerator {

    /**
     * Generates an empty maze of the given dimensions.
     * All values in the maze grid are initialized to 0 (empty path).
     *
     * @param rows  Number of rows in the maze.
     * @param columns Number of columns in the maze.
     * @return A Maze object with an empty grid and predefined start and goal positions.
     */
    @Override
    public Maze generate(int rows, int columns) {
        int[][] mazeGrid = new int[rows][columns]; // the default is 0 so it's an empty maze
        Position startPosition = new Position(0, 0);
        Position goalPosition = new Position(rows - 1, columns - 1);
        return new Maze(mazeGrid, startPosition, goalPosition);
    }
}
