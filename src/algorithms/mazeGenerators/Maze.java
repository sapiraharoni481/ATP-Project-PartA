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
    public Maze(int rows, int cols) {
        this.maze = new int[rows][cols];
        this.startPosition = new Position(0, 0);  // ערכי ברירת מחדל
        this.goalPosition = new Position(rows-1, cols-1);  // ערכי ברירת מחדל
    }

    public void setStartPosition(Position start) {
        this.startPosition = start;
    }

    public void setGoalPosition(Position goal) {
        this.goalPosition = goal;
    }

    public void setCell(int row, int col, int value) {
        this.maze[row][col] = value;
    }

    public int[][] getMaze(){
        return maze;
    }

    public Position getGoaltPosition() {
        return goalPosition;
    }
    public void print(){
        for (int i=0;i<maze.length;i++){
            for (int j=0;j<maze[0].length;j++){
                if(i==startPosition.getRowIndex()&& j==startPosition.getColumnIndex())
                    System.out.print("S");
                else if (i==goalPosition.getRowIndex()&& j== goalPosition.getColumnIndex())
                    System.out.print("E");
                else
                    System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }

    public Position getGoalPosition() {
        return goalPosition;
    }
    public Position getStartPosition(){
        return startPosition;
    }
}