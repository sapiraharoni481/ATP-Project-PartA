package algorithms.mazeGenerators;

import java.io.Serializable;
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
public class Maze implements Serializable {

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
     * Constructs a maze from a byte array representation.
     *
     * @param bytes A byte array representing maze dimensions, start/goal positions, and maze data.
     */
    public Maze(byte[] bytes) {
        // Extract maze dimensions
        int rows = ((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF);
        int cols = ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);

        // Extract start position
        int startRow = ((bytes[4] & 0xFF) << 8) | (bytes[5] & 0xFF);
        int startCol = ((bytes[6] & 0xFF) << 8) | (bytes[7] & 0xFF);
        startPosition = new Position(startRow, startCol);

        // Extract goal position
        int goalRow = ((bytes[8] & 0xFF) << 8) | (bytes[9] & 0xFF);
        int goalCol = ((bytes[10] & 0xFF) << 8) | (bytes[11] & 0xFF);
        goalPosition = new Position(goalRow, goalCol);

        // Construct maze grid
        maze = new int[rows][cols];
        int index = 12;

        // בניית המבוך עם בדיקת גבולות
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (index < bytes.length) {
                    maze[i][j] = bytes[index++] & 0xFF;
                } else {
                    // אם אין מספיק מידע, נשים 0 כברירת מחדל
                    maze[i][j] = 0;
                }
            }
        }
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
     * Converts the maze to a byte array representation.
     *
     * @return A byte array containing maze dimensions, start/goal positions, and maze data.
     */
    public byte[] toByteArray() {
        int rows = maze.length;
        int cols = maze[0].length;

        // 12 bytes for metadata + maze cells
        byte[] byteArray = new byte[rows * cols + 12];

        // Store maze dimensions (4 bytes)
        byteArray[0] = (byte)((rows >> 8) & 0xFF);
        byteArray[1] = (byte)(rows & 0xFF);
        byteArray[2] = (byte)((cols >> 8) & 0xFF);
        byteArray[3] = (byte)(cols & 0xFF);

        // Store start position (4 bytes)
        byteArray[4] = (byte)((startPosition.getRowIndex() >> 8) & 0xFF);
        byteArray[5] = (byte)(startPosition.getRowIndex() & 0xFF);
        byteArray[6] = (byte)((startPosition.getColumnIndex() >> 8) & 0xFF);
        byteArray[7] = (byte)(startPosition.getColumnIndex() & 0xFF);

        // Store goal position (4 bytes)
        byteArray[8] = (byte)((goalPosition.getRowIndex() >> 8) & 0xFF);
        byteArray[9] = (byte)(goalPosition.getRowIndex() & 0xFF);
        byteArray[10] = (byte)((goalPosition.getColumnIndex() >> 8) & 0xFF);
        byteArray[11] = (byte)(goalPosition.getColumnIndex() & 0xFF);

        // Store maze data
        int index = 12;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                byteArray[index++] = (byte)maze[i][j];
            }
        }

        return byteArray;
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