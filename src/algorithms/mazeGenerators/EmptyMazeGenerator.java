package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{
    @Override
    public Maze generate(int rows, int columns){
        int[][] mazeGrid = new int[rows][columns];
        Position startPosition = new Position(0,0);
        Position goalPosition = new Position(rows-1,columns-1);
        return new Maze(mazeGrid, startPosition,goalPosition);
    }
}
