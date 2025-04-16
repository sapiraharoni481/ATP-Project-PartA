package algorithms.search;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import java.util.ArrayList;
// מחלקת עם אדפטר להתאים בין המבוך לאגוריתמי החיפוש
public class SearchableMaze implements ISearchable {
    private Maze maze;
    private MazeState startState;
    private MazeState goalState;

    public SearchableMaze(Maze maze) {
        this.maze = maze;
        this.startState = new MazeState(maze.getStartPosition());
        this.goalState = new MazeState(maze.getGoalPosition());
    }

    @Override
    public AState getStartState() {
        return startState;
    }
    @Override
    public AState getGoalState() {
        return goalState;
    }
    @Override
    public ArrayList<AState> getAllPossibleStates(AState state) {
        ArrayList<AState> possibleStates = new ArrayList<>();

        if (!(state instanceof MazeState)) {
            return possibleStates;
        }
        MazeState mazeState = (MazeState) state;
        Position position = mazeState.getPosition();
        int row = position.getRowIndex();
        int col = position.getColumnIndex();

        // Check up
        checkAndAddPosition(possibleStates, row - 1, col, 10);

        // Check right - cost 10
        checkAndAddPosition(possibleStates, row, col + 1, 10);

        // Check down - cost 10
        checkAndAddPosition(possibleStates, row + 1, col, 10);

        // Check left - cost 10
        checkAndAddPosition(possibleStates, row, col - 1, 10);

        // Check  up-right - cost 15
        checkAndAddDiagonal(possibleStates, row, col, row - 1, col + 1);

        // Check  down-right - cost 15
        checkAndAddDiagonal(possibleStates, row, col, row + 1, col + 1);

        // Check  down-left  - cost 15
        checkAndAddDiagonal(possibleStates, row, col, row + 1, col - 1);

        // Check  up-left  - cost 15
        checkAndAddDiagonal(possibleStates, row, col, row - 1, col - 1);
        return possibleStates;
    }

    private void checkAndAddPosition(ArrayList<AState> possibleStates, int row, int col, double cost) {
        if (isValidPosition(row, col)) {
            Position pos = new Position(row, col);
            possibleStates.add(new MazeState(pos, cost));
        }
    }

    private void checkAndAddDiagonal(ArrayList<AState> possibleStates, int currentRow, int currentCol, int diagonalRow, int diagonalCol) {
        if (isValidPosition(diagonalRow, diagonalCol) && ((isValidPosition(currentRow, diagonalCol) && isValidPosition(diagonalRow, currentCol)))) {
            Position pos = new Position(diagonalRow, diagonalCol);     //  ניתן רק אם שני המעברים באופן ישר אפשריים
            possibleStates.add(new MazeState(pos, 15)); // עלות תנועה אלכסונית היא 15
        }
    }

    private boolean isValidPosition(int row, int col) {
        // בדיקה שהמיקום הוא בתוך גבולות המבוך ושהוא לא קיר
        return row >= 0 && row < maze.getMaze().length && col >= 0 && col < maze.getMaze()[0].length && maze.getMaze()[row][col] == 0;
    }
}