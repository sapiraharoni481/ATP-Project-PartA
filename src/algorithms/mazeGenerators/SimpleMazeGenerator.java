package algorithms.mazeGenerators;
import java.util.*;
import java.util.Random;
/**
 * A basic maze generator that creates a randomized maze
 * with a 30% chance for walls, and ensures there is at least
 * one valid path from the start to the goal.
 *
 * The guaranteed path is a simple L-shaped path from the top-left
 * to the bottom-right corner of the maze.
 *
 * Extends AMazeGenerator.
 *
 * @author Sapir
 * @version 1.0
 * @since 2025-04-13
 */
public class SimpleMazeGenerator extends AMazeGenerator{
    /**
     * Generates a randomized maze with the specified dimensions.
     * Each cell has a 30% chance to be a wall, except for the start and goal cells.
     * A simple guaranteed path is created from start to goal.
     *
     * @param rows Number of rows in the maze.
     * @param columns Number of columns in the maze.
     * @return A Maze object with at least one valid solution.
     */
    @Override
    public Maze generate(int rows, int columns) {
        Random random = new Random();
        int[][] mazeGrid = new int[rows][columns];

        // Randomly set walls with 30% probability
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                mazeGrid[i][j] = (random.nextDouble() < 0.3) ? 1 : 0;
            }
        }

        // Get random start and goal positions from the border
        ArrayList<Position> borderPositions = getBorderPositions(rows, columns);
        Position startPosition = borderPositions.get(random.nextInt(borderPositions.size()));
        Position goalPosition = borderPositions.get(random.nextInt(borderPositions.size()));
        while (goalPosition.equals(startPosition)) {
            goalPosition = borderPositions.get(random.nextInt(borderPositions.size()));
        }

        // Ensure start and goal are not walls
        mazeGrid[startPosition.getRowIndex()][startPosition.getColumnIndex()] = 0;
        mazeGrid[goalPosition.getRowIndex()][goalPosition.getColumnIndex()] = 0;

        // Create simple L-shaped path
        ensurePath(mazeGrid, startPosition, goalPosition);
        return new Maze(mazeGrid, startPosition, goalPosition);
    }

    /**
     * Collects all positions located on the border of the maze.
     */
    private ArrayList<Position> getBorderPositions(int rows, int columns) {
        ArrayList<Position> borderPositions = new ArrayList<>();

        // Top and bottom rows
        for (int col = 0; col < columns; col++) {
            borderPositions.add(new Position(0, col));   // top row
            borderPositions.add(new Position(rows - 1, col));// bottom row
        }

        // Left and right columns (excluding corners already added)
        for (int row = 1; row < rows - 1; row++) {
            borderPositions.add(new Position(row, 0)); // left column
            borderPositions.add(new Position(row, columns - 1)); // right column
        }

        return borderPositions;
    }
    /**
     * Creates a guaranteed simple path from the start to the goal
     * by carving a straight L-shaped path through the maze.
     *
     * @param maze The maze grid to modify.
     * @param start The start position.
     * @param end  The goal position.
     */
    private void ensurePath(int[][] maze,Position start,Position end){
        int currentRow = start.getRowIndex();
        int currentCol = start.getColumnIndex();
        int goalRow = end.getRowIndex();
        int goalColumn = end.getColumnIndex();
        // Create vertical path
        while (currentRow<goalRow){
            currentRow++;
            maze[currentRow][currentCol]=0;
        }
        // Then horizontal path
        while (currentCol < goalColumn) {
            currentCol++;
            maze[currentRow][currentCol] = 0;
        }
    }
}