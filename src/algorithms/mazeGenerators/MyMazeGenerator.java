

package algorithms.mazeGenerators;

// add to git
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
// Randomized depth first algorithms.search
public class MyMazeGenerator extends AMazeGenerator{
    @Override
    public Maze generate(int rows, int columns){
        // Creating a maze filled with walls
        int[][] mazeGrid = new int[rows][columns];
        for (int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                mazeGrid[i][j]=1;
            }
        }
        Random random = new Random();
        Stack<Position> stack = new Stack<>();
        // Start with the top left corner
        int startRow=0;
        int startCol=0;
        Position startPosition = new Position(startRow,startCol);
        mazeGrid[startRow][startCol]=0;
        stack.push(startPosition);
        // Creating a random maze using DFS method
        while (!stack.isEmpty()){
            Position current = stack.peek();
            ArrayList<Position> neighbors = getUnvisitedNeighbors(current,mazeGrid, rows,columns);
            if (neighbors.size()>0){
                Position next = neighbors.get(random.nextInt(neighbors.size()));
                // Breaking walls between cells

                int currRow = current.getRowIndex();
                int currCol = current.getColumnIndex();
                int nextRow = next.getRowIndex();
                int nextCol = next.getColumnIndex();
                if(currRow==nextRow){
                    if(currCol < nextCol) { // right
                        mazeGrid[currRow][currCol + 1] = 0;
                    } else {
                        mazeGrid[currRow][currCol-1] = 0;
                    }
                }else {
                    if(currRow<nextRow) { // down
                        mazeGrid[currRow+1][currCol] = 0;
                    }else { // up
                        mazeGrid[currRow-1][currCol] = 0;
                    }

                }
                mazeGrid[nextRow][nextCol] = 0;
                stack.push(next);
            }else {
                stack.pop();
            }
        }

        // Set the goal position
        Position goalPosition = new Position(rows-1,columns-1);
        mazeGrid[rows-1][columns-1]=0;

        // Create a path from the current maze to the goal position if needed
        ensurePathToGoal(mazeGrid, rows, columns);

        return new Maze(mazeGrid, startPosition, goalPosition);
    }

    // Add this method to ensure there's a path to the goal
    private void ensurePathToGoal(int[][] mazeGrid, int rows, int columns) {
        // If goal is already connected, no need to do anything
        if (isConnectedToMaze(mazeGrid, rows-1, columns-1, rows, columns)) {
            return;
        }

        // Otherwise, create a path from the nearest open cell to the goal
        int goalRow = rows-1;
        int goalCol = columns-1;

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

    // Check if a cell is connected to the maze
    private boolean isConnectedToMaze(int[][] mazeGrid, int row, int col, int rows, int columns) {
        // Check if any adjacent cell is a path
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

    // Find the nearest cell that is already part of the maze
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