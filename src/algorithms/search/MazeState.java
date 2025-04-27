
package algorithms.search;

import algorithms.mazeGenerators.Position;
/**
 * The  MazeState class represents a specific state within a maze,
 * identified by a Position. It extends the abstract AState class
 * and adds maze-specific logic such as coordinates and heuristic calculation.
 * <p>
 * This class is used in search algorithms to represent a cell in the maze grid.
 * </p>
 *
 * @author Batel
 * @version 1.0
 * @since 2025-04-20
 */
public class MazeState extends AState {
    private Position position;
    /**
     * Constructs a new MazeState at the given position.
     * The cost is initialized to 0 by the superclass constructor.
     *
     * @param position The position of the state in the maze.
     */
    public MazeState(Position position) {
        super(position.toString());
        this.position = position;
    }
    /**
     * Constructs a new MazeState at the given position with a specified cost.
     *
     * @param position The position of the state in the maze.
     * @param cost The cost to reach this state.
     */
    public MazeState(Position position, double cost) {
        super(position.toString(), cost);
        this.position = position;
    }
    /**
     * Returns the position of this state in the maze.
     *
     * @return The Position of the state.
     */
    public Position getPosition() {
        return position;
    }
    /**
     * Sets the heuristic estimate from this state to the goal.
     *
     * @param heuristic The heuristic value (e.g., Manhattan distance).
     */
    public void setHeuristic(double heuristic) {
        this.heuristic = heuristic;
    }
    /**
     * Returns the heuristic estimate from this state to the goal.
     *
     * @return The heuristic value.
     */
    public double getHeuristic() {
        return heuristic;
    }
    /**
     * Returns a string representation of this MazeState,
     * based on its Position.
     *
     * @return A string in the format "[row,column]".
     */
    @Override
    public String toString() {
        return position.toString();
    }
}
