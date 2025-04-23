package algorithms.search;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import java.util.ArrayList;
/**
 * A concrete implementation of the ISearchable interface, which adapts
 * a  Maze object to be compatible with generic search algorithms.
 * <p>
 * Each cell in the maze is treated as a state, and legal transitions correspond
 * to valid movements (up, down, left, right, and diagonals).
 * </p>
 *
 * @author Batel
 * @version 1.0
 * @since 2025-04-20
 */
public class SearchableMaze implements ISearchable {
    private Maze maze;
    private MazeState startState;
    private MazeState goalState;
    /**
     * Constructs a SearchableMaze using a given Maze.
     * Initializes the start and goal states and precomputes the heuristic
     * value for the start state.
     *
     * @param maze The maze to be adapted to a searchable format.
     */

    public SearchableMaze(Maze maze) {
        this.maze = maze;
        this.startState = new MazeState(maze.getStartPosition());
        this.goalState = new MazeState(maze.getGoalPosition());
        Position goalPos = ((MazeState) goalState).getPosition();
        Position startPos = ((MazeState) startState).getPosition();
        startState.heuristic = Math.abs(goalPos.getRowIndex() - startPos.getRowIndex()) + Math.abs(goalPos.getColumnIndex() - startPos.getColumnIndex());
    }
    /**
     * Returns the initial state of the search problem.
     *
     * @return The start state.
     */
    @Override
    public AState getStartState() {
        return startState;
    }
    /**
     * Returns the goal state of the search problem.
     *
     * @return The goal state.
     */
    @Override
    public AState getGoalState() {
        return goalState;
    }
    /**
     * Returns all valid adjacent states that can be reached from the given state.
     *
     * @param state The current state.
     * @return A list of reachable neighboring states.
     */
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
        checkAndAddPosition(possibleStates, row - 1, col);
        // Check right - cost 10
        checkAndAddPosition(possibleStates, row, col + 1);
        // Check down - cost 10
        checkAndAddPosition(possibleStates, row + 1, col);
        // Check left - cost 10
        checkAndAddPosition(possibleStates, row, col - 1);
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
    /**
     * Adds a valid cardinal (non-diagonal) neighboring position to the state list.
     *
     * @param possibleStates The list of possible next states.
     * @param row Row index of the candidate neighbor.
     * @param col Column index of the candidate neighbor.
     */
    private void checkAndAddPosition(ArrayList<AState> possibleStates, int row, int col) {
        if (isValidPosition(row, col)) {
            Position goalPos = ((MazeState) goalState).getPosition();
            Position pos = new Position(row, col);
            double initialHeuristic = Math.abs(goalPos.getRowIndex() - pos.getRowIndex()) + Math.abs(goalPos.getColumnIndex() - pos.getColumnIndex());
            MazeState z = new MazeState(pos, 10) ; // Cardinal move cost = 10
            z.setHeuristic(initialHeuristic);
            possibleStates.add(z);
        }
    }
    /**
     * Adds a valid diagonal neighboring position to the state list,
     * ensuring the diagonal move is logically allowed (i.e., passes through open neighbors).
     *
     * @param possibleStates The list of possible next states.
     * @param currentRow The current state's row index.
     * @param currentCol The current state's column index.
     * @param diagonalRow The row index of the diagonal neighbor.
     * @param diagonalCol The column index of the diagonal neighbor.
     */
    private void checkAndAddDiagonal(ArrayList<AState> possibleStates, int currentRow, int currentCol, int diagonalRow, int diagonalCol) {
        if (isValidPosition(diagonalRow, diagonalCol) && ((isValidPosition(currentRow, diagonalCol) || isValidPosition(diagonalRow, currentCol)))) {
            Position pos = new Position(diagonalRow, diagonalCol);     //  ניתן רק אם שני המעברים באופן ישר אפשריים
            Position goalPos = ((MazeState) goalState).getPosition();
            double initialHeuristic = Math.abs(goalPos.getRowIndex() - pos.getRowIndex()) + Math.abs(goalPos.getColumnIndex() - pos.getColumnIndex());
            MazeState z = new MazeState(pos, 15) ; // Diagonal move cost = 15
            z.setHeuristic(initialHeuristic);
            possibleStates.add(z);
        }
    }
    /**
     * Validates that a given position is within the maze bounds and not a wall.
     *
     * @param row The row index.
     * @param col The column index.
     * @return true if the position is valid and accessible, false otherwise.
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < maze.getMaze().length && col >= 0 && col < maze.getMaze()[0].length && maze.getMaze()[row][col] == 0;
    }
}

