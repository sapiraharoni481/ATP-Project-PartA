

package algorithms.mazeGenerators;

import java.util.*;
/**
 * Maze generator that uses randomized Depth-First Search (DFS)
 * to create a perfect maze, then ensures there is a valid path
 * to the goal position.
 *
 * Walls are represented by 1 and paths by 0.
 * The algorithm breaks walls between unvisited neighbors
 * and uses a stack to simulate DFS.
 *
 * @author Sapir
 * @version 1.0
 * @since 2025-04-13
 */
public class MyMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generate(int rows, int columns) {
        // Create a maze grid filled with walls (1)
        int[][] mazeGrid = initializeMazeGrid(rows, columns);
        // Get all possible positions on the maze borders
        ArrayList<Position> borderPositions = getBorderPositions(rows, columns);
        Random rand = new Random();
        // Randomly select a start and goal position from the borders (must be different)
        Position startPosition = borderPositions.get(rand.nextInt(borderPositions.size()));
        Position goalPosition = borderPositions.get(rand.nextInt(borderPositions.size()));
        while (goalPosition.equals(startPosition)) {
            goalPosition = borderPositions.get(rand.nextInt(borderPositions.size()));
        }
        // Generate the maze using DFS starting from the start position
        generateMazeDFS(mazeGrid, startPosition, rows, columns);
        mazeGrid[goalPosition.getRowIndex()][goalPosition.getColumnIndex()] = 0;
        ensurePathToGoal(mazeGrid, goalPosition, rows, columns);
        return new Maze(mazeGrid, startPosition, goalPosition);
    }

    /**
     * Initializes a grid of the maze with all cells set to wall (1).
     */
    private int[][] initializeMazeGrid(int rows, int columns) {
        int[][] grid = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(grid[i], 1);
        }
        return grid;
    }
    /**
     * Returns all positions on the outer border of the maze.
     */
    private ArrayList<Position> getBorderPositions(int rows, int columns) {
        ArrayList<Position> borderPositions = new ArrayList<>();
        // Add top and bottom rows
        for (int col = 0; col < columns; col++) {
            borderPositions.add(new Position(0, col));
            borderPositions.add(new Position(rows - 1, col));
        }
        for (int row = 1; row < rows - 1; row++) {
            borderPositions.add(new Position(row, 0));
            borderPositions.add(new Position(row, columns - 1));
        }
        return borderPositions;
    }
    /**
     * Generates a maze using the randomized DFS algorithm.
     * Opens paths by breaking walls between visited cells.
     */
    private void generateMazeDFS(int[][] mazeGrid, Position startPosition, int rows, int columns) {
        Random random = new Random();
        Stack<Position> stack = new Stack<>();
        // Mark the start position as a path
        mazeGrid[startPosition.getRowIndex()][startPosition.getColumnIndex()] = 0;
        stack.push(startPosition);
        while (!stack.isEmpty()) {
            Position current = stack.peek();
            // Get all unvisited neighbors that are two steps away
            ArrayList<Position> neighbors = getUnvisitedNeighbors(current, mazeGrid, rows, columns);
            if (!neighbors.isEmpty()) {
                // Choose a random unvisited neighbor
                Position next = neighbors.get(random.nextInt(neighbors.size()));
                int currRow = current.getRowIndex();
                int currCol = current.getColumnIndex();
                int nextRow = next.getRowIndex();
                int nextCol = next.getColumnIndex();
                // Break the wall between current and next cell
                if (currRow == nextRow) {
                    mazeGrid[currRow][(currCol + nextCol) / 2] = 0;
                } else {
                    mazeGrid[(currRow + nextRow) / 2][currCol] = 0;
                }

                mazeGrid[nextRow][nextCol] = 0;
                stack.push(next);
            } else {
                // Backtrack if no neighbors available
                stack.pop();
            }
        }
    }
    /**
     * Ensures there is a valid path to the goal position in case it was isolated.
     *
     * @param mazeGrid The maze grid.
     * @param goalPosition The position of the goal.
     * @param rows Number of rows in the maze.
     * @param columns Number of columns in the maze.
     */
    private void ensurePathToGoal(int[][] mazeGrid, Position goalPosition, int rows, int columns) {
        int goalRow = goalPosition.getRowIndex();
        int goalCol = goalPosition.getColumnIndex();

        // If goal is already connected, no need to do anything
        if (isConnectedToMaze(mazeGrid, goalRow, goalCol, rows, columns)) {
            return;
        }

        // Find the nearest accessible cell
        Position nearestCell = findNearestAccessibleCell(mazeGrid, goalRow, goalCol, rows, columns);

        // Create a path from the nearest cell to the goal
        int currentRow = nearestCell.getRowIndex();
        int currentCol = nearestCell.getColumnIndex();

        // Create horizontal path first
        while (currentCol != goalCol) {
            if (currentCol < goalCol) {
                currentCol++;
            } else {
                currentCol--;
            }
            mazeGrid[currentRow][currentCol] = 0;
        }

        // Then create vertical path
        while (currentRow != goalRow) {
            if (currentRow < goalRow) {
                currentRow++;
            } else {
                currentRow--;
            }
            mazeGrid[currentRow][currentCol] = 0;
        }
    }


    /**
     * Checks if the goal cell is adjacent to any accessible cell.
     */
    private boolean isConnectedToMaze(int[][] mazeGrid, int row, int col, int rows, int columns) {
        if (row > 0 && mazeGrid[row-1][col] == 0)
            return true;
        if (row < rows-1 && mazeGrid[row+1][col] == 0)
            return true;
        if (col > 0 && mazeGrid[row][col-1] == 0)
            return true;
        if (col < columns-1 && mazeGrid[row][col+1] == 0)
            return true;
        return false;
    }
    /**
     * Finds the nearest cell to the goal that is part of the maze path using BFS.
     */
    private Position findNearestAccessibleCell(int[][] mazeGrid, int goalRow, int goalCol, int rows, int columns) {
        // Simple BFS to find the nearest accessible cell
        boolean[][] visited = new boolean[rows][columns];
        Stack<Position> queue = new Stack<>();
        queue.push(new Position(goalRow, goalCol));
        visited[goalRow][goalCol] = true;
        while (!queue.isEmpty()) {
            Position current = queue.pop();
            int row = current.getRowIndex();
            int col = current.getColumnIndex();
            // Check all four adjacent cells
            int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                // Check if valid position
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < columns) {
                    if (!visited[newRow][newCol]) {
                        visited[newRow][newCol] = true;
                        queue.push(new Position(newRow, newCol));
                        // If this cell is accessible (part of the existing maze)
                        if (mazeGrid[newRow][newCol] == 0) {
                            return new Position(newRow, newCol);
                        }
                    }
                }
            }
        }
        // Fallback - should never reach here if the maze has at least one open cell
        return new Position(0, 0);
    }
    /**
     * Returns a list of unvisited neighbors (distance 2 away) that are still walls (value 1).
     */
    private ArrayList<Position> getUnvisitedNeighbors(Position p,int[][] maze,int rows, int columns){
        ArrayList<Position> neighbors = new ArrayList<>();
        int row = p.getRowIndex();
        int col = p.getColumnIndex();
        // Checking neighbors at a distance of 2 cells to avoid breaking adjacent walls
        if (row-2 >= 0 && maze[row-2][col] == 1){
            neighbors.add(new Position(row-2,col));
        }
        if (row+2 < rows && maze[row+2][col] == 1){
            neighbors.add(new Position(row+2,col));
        }
        if(col-2 >=0 && maze[row][col-2]==1){
            neighbors.add(new Position(row,col-2));
        }
        if(col+2 < columns && maze[row][col+2]==1){
            neighbors.add(new Position(row,col+2));
        }
        return neighbors;
    }
}