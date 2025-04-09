package algorithms.mazeGenerators;


import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
// Randomized depth first search
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
        // Choosing a random end point on the opposite side
        Position goalPosition = new Position(rows-1,columns-1);
        mazeGrid[rows-1][columns-1]=0;
        return new Maze(mazeGrid, startPosition,goalPosition);

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