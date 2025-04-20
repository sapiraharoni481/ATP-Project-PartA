package algorithms.mazeGenerators;

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
    public Maze generate(int rows, int columns){
        Random random= new Random();
        int[][] mazeGrid = new int[rows][columns];
        // Randomly set walls with 30% probability
        for (int i=0; i< rows;i++){
            for(int j = 0; j<columns; j++){
                mazeGrid[i][j]= (random.nextDouble()<0.3)?1:0;
            }
        }
        // Define start and goal positions
        Position startPosition = new Position(0,0);
        Position goalPosition = new Position(rows-1,columns-1);
        // Ensure start and goal are not walls
        mazeGrid[startPosition.getRowIndex()][startPosition.getColumnIndex()]=0;
        mazeGrid[goalPosition.getRowIndex()][goalPosition.getColumnIndex()]=0;
        // Ensure at least one path exists between start and goal
        ensurePath(mazeGrid,startPosition,goalPosition);
        return new Maze(mazeGrid,startPosition,goalPosition);
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