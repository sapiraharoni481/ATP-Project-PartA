//package algorithms.mazeGenerators;
///**
// * Represents a maze structure using a 2D grid.
// * The maze consists of cells, each represented by an integer value.
// * Includes start and goal positions for search algorithms.
// *
// * A value of 0 usually represents a free cell, while 1 represents a wall.
// *
// * @author Sapir
// * @version 1.0
// * @since 2025-04-13
// */
//public class Maze {
//
//    private int[][] maze;
//    private Position startPosition;
//    private Position goalPosition;
//    /**
//     * Constructs a Maze object with a given grid and specific start and goal positions.
//     *
//     * @param maze The 2D grid representing the maze structure.
//     * @param start The starting position in the maze.
//     * @param goal The goal (end) position in the maze.
//     */
//    public Maze(int[][] maze, Position start, Position goal) {
//        this.maze = maze;
//        this.startPosition = start;
//        this.goalPosition = goal;
//    }
//    /**
//     * Constructs an empty Maze with all cells initialized to 0.
//     * The start is set to (0,0) and the goal to (rows-1, cols-1).
//     *
//     * @param rows Number of rows in the maze.
//     * @param cols Number of columns in the maze.
//     */
//    public Maze(int rows, int cols) {
//        this.maze = new int[rows][cols];
//        this.startPosition = new Position(0, 0);
//        this.goalPosition = new Position(rows - 1, cols - 1);
//    }
//
//    /**
//     * Returns the goal position of the maze.
//     *
//     * @return The goal Position.
//     */
//    public Position getGoalPosition() {
//        return goalPosition;
//    }
//
//    /**
//     * Returns the start position of the maze.
//     *
//     * @return The start  Position.
//     */
//    public Position getStartPosition() {
//        return startPosition;
//    }
//
//    /**
//     * Sets the start position of the maze.
//     *
//     * @param start The new start  Position.
//     */
//    public void setStartPosition(Position start) {
//        this.startPosition = start;
//    }
//
//    /**
//     * Sets the goal position of the maze.
//     *
//     * @param goal The new goal  Position.
//     */
//    public void setGoalPosition(Position goal) {
//        this.goalPosition = goal;
//    }
//    /**
//     * Sets the value of a specific cell in the maze.
//     *
//     * @param row   Row index of the cell.
//     * @param col   Column index of the cell.
//     * @param value The value to set (e.g., 0 for empty, 1 for wall).
//     */
//    public void setCell(int row, int col, int value) {
//        this.maze[row][col] = value;
//    }
//    /**
//     * Returns the 2D grid representing the maze.
//     *
//     * @return A 2D array of integers representing the maze.
//     */
//    public int[][] getMaze() {
//        return maze;
//    }
//    /**
//     * Prints the maze to the console.
//     * The start position is marked with 'S', the goal with 'E', and other values as-is.
//     */
//    public void print() {
//        for (int i = 0; i < maze.length; i++) {
//            for (int j = 0; j < maze[0].length; j++) {
//                if (i == startPosition.getRowIndex() && j == startPosition.getColumnIndex())
//                    System.out.print("S"); // start
//                else if (i == goalPosition.getRowIndex() && j == goalPosition.getColumnIndex())
//                    System.out.print("E"); // end
//                else
//                    System.out.print(maze[i][j]);
//            }
//            System.out.println();
//        }
//    }
//}
package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a maze structure using a 2D grid.
 * The maze consists of cells, each represented by an integer value.
 * Includes start and goal positions for search algorithms.
 * A value of 0 usually represents a free cell, while 1 represents a wall.
 *
 * The start and goal positions are guaranteed to be located on the outer border of the maze,
 * as required by the assignment instructions.
 *
 * @author Sapir
 * @version 1.1
 * @since 2025-04-20
 */
public class Maze {

    private int[][] maze;
    private Position startPosition;
    private Position goalPosition;

    /**
     * Constructs a Maze object with a given grid and specific start and goal positions.
     *
     * @param maze  The 2D grid representing the maze structure.
     * @param start The starting position in the maze.
     * @param goal  The goal (end) position in the maze.
     */
    public Maze(int[][] maze, Position start, Position goal) {
        this.maze = maze;
        this.startPosition = start;
        this.goalPosition = goal;
    }

    /**
     * Constructs an empty Maze with all cells initialized to 0.
     * The start and goal positions are selected randomly from the outer border of the maze.
     *
     * @param rows Number of rows in the maze.
     * @param cols Number of columns in the maze.
     */
    public Maze(int rows, int cols) {
        this.maze = new int[rows][cols];

        // Collect all valid positions on the border of the maze
        ArrayList<Position> borderPositions = new ArrayList<>();

        // Add top and bottom rows
        for (int col = 0; col < cols; col++) {
            borderPositions.add(new Position(0, col));           // Top row
            borderPositions.add(new Position(rows - 1, col));    // Bottom row
        }

        // Add left and right columns (excluding corners already added)
        for (int row = 1; row < rows - 1; row++) {
            borderPositions.add(new Position(row, 0));           // Left column
            borderPositions.add(new Position(row, cols - 1));    // Right column
        }

        Random rand = new Random();
        Position start = borderPositions.get(rand.nextInt(borderPositions.size()));
        Position goal = borderPositions.get(rand.nextInt(borderPositions.size()));

        // Ensure start and goal are not the same
        while (goal.equals(start)) {
            goal = borderPositions.get(rand.nextInt(borderPositions.size()));
        }

        this.startPosition = start;
        this.goalPosition = goal;
    }

    /**
     * Returns the goal position of the maze.
     *
     * @return The goal Position.
     */
    public Position getGoalPosition() {
        return goalPosition;
    }

    /**
     * Returns the start position of the maze.
     *
     * @return The start Position.
     */
    public Position getStartPosition() {
        return startPosition;
    }

    /**
     * Sets the start position of the maze.
     *
     * @param start The new start Position.
     */
    public void setStartPosition(Position start) {
        this.startPosition = start;
    }

    /**
     * Sets the goal position of the maze.
     *
     * @param goal The new goal Position.
     */
    public void setGoalPosition(Position goal) {
        this.goalPosition = goal;
    }

    /**
     * Sets the value of a specific cell in the maze.
     *
     * @param row   Row index of the cell.
     * @param col   Column index of the cell.
     * @param value The value to set (e.g., 0 for path, 1 for wall).
     */
    public void setCell(int row, int col, int value) {
        this.maze[row][col] = value;
    }

    /**
     * Returns the 2D grid representing the maze.
     *
     * @return A 2D array of integers representing the maze structure.
     */
    public int[][] getMaze() {
        return maze;
    }

    /**
     * Prints the maze to the console.
     * The start position is marked with 'S', the goal with 'E', and all other cells as 0 or 1.
     */
    public void print() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (i == startPosition.getRowIndex() && j == startPosition.getColumnIndex())
                    System.out.print("S"); // Start
                else if (i == goalPosition.getRowIndex() && j == goalPosition.getColumnIndex())
                    System.out.print("E"); // End
                else
                    System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }
}
