package algorithms.mazeGenerators;
import java.util.Random;
import java.util.*;
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
     * Start and goal are chosen randomly from the outer border.
     *
     * @param rows    Number of rows in the maze.
     * @param columns Number of columns in the maze.
     * @return A Maze object with an empty grid and random start and goal on the border.
     */
    @Override
    public Maze generate(int rows, int columns) {
        int[][] mazeGrid = new int[rows][columns]; // default initialized to 0

        ArrayList<Position> borderPositions = getBorderPositions(rows, columns);
        Random rand = new Random();

        Position startPosition = borderPositions.get(rand.nextInt(borderPositions.size()));
        Position goalPosition = borderPositions.get(rand.nextInt(borderPositions.size()));
        while (goalPosition.equals(startPosition)) {
            goalPosition = borderPositions.get(rand.nextInt(borderPositions.size()));
        }

        return new Maze(mazeGrid, startPosition, goalPosition);
    }
    /**
     * Collects all positions on the border of the maze grid.
     */
    private ArrayList<Position> getBorderPositions(int rows, int columns) {
        ArrayList<Position> borderPositions = new ArrayList<>();

        // Top and bottom rows
        for (int col = 0; col < columns; col++) {
            borderPositions.add(new Position(0, col));  // Top
            borderPositions.add(new Position(rows - 1, col)); // Bottom
        }
        // Left and right columns (excluding corners already added)
        for (int row = 1; row < rows - 1; row++) {
            borderPositions.add(new Position(row, 0));  // Left
            borderPositions.add(new Position(row, columns - 1));// Right
        }
        return borderPositions;
    }
}