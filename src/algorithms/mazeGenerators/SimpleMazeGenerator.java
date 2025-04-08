package algorithms.mazeGenerators;

import java.util.Random;
public class SimpleMazeGenerator extends AMazeGenerator{
    @Override
    public Maze generate(int rows, int columns){
        Random random= new Random();
        int[][] mazeGrid = new int[rows][columns];
        for (int i=0; i< rows;i++){
            for(int j = 0; j<columns; j++){
                // ניצור קיר בהסתברות של 30%
                mazeGrid[i][j]= (random.nextDouble()<0.3)?1:0;
            }
        }
        // נגדיר נקודות התחלה וסיום
        Position startPosition = new Position(0,0);
        Position goalPosition = new Position(rows-1,columns-1);
        // נוודא שפתוחות
        mazeGrid[startPosition.getRowIndex()][startPosition.getColumnIndex()]=0;
        mazeGrid[goalPosition.getRowIndex()][goalPosition.getColumnIndex()]=0;
        // נוודא שיש לפחות דרך פתרון אחד למבוך
        ensurePath(mazeGrid,startPosition,goalPosition);
        return new Maze(mazeGrid,startPosition,goalPosition);
    }
    private void ensurePath(int[][] maze,Position start,Position end){
        int currentRow = start.getRowIndex();
        int currentCol = start.getColumnIndex();
        int goalRow = end.getRowIndex();
        int goalColumn = end.getColumnIndex();
        // ניצור נתיב ישר מההתחלה לסוף
        while (currentRow<goalRow){
            currentRow++;
            maze[currentRow][currentCol]=0;//נפתח את המעבר
        }
        while (currentCol<goalColumn){
            currentRow++;
            maze[currentRow][currentCol]=0;
        }
    }
}
