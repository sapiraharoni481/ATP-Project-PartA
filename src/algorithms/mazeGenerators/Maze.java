package algorithms.mazeGenerators;


public class Maze {
    private  int[][] maze;
    private Position startPosition;
    private Position goalPosition;

    public Maze(int[][] maze, Position start, Position goal){
        this.maze=maze;
        this.startPosition=start;
        this.goalPosition=goal;
    }

    public int[][] getMaze(){
        return maze;
    }
    public Position getStartPosition(){ return startPosition;
    }
    public Position getGoaltPosition() {
        return goalPosition;
    }
    public void print(){ // need??

    }
}
