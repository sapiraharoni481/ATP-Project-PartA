package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;



/**
 * Represents a solution to a search problem.
 * Stores a path from the initial state to the goal state as a sequence of {@link AState} objects.
 * This class is used as the output of search algorithms.
 *
 * Implements Serializable to support saving or transferring the solution.
 *
 * @author Batel
 * @version 1.0
 * @since 2025-04-20
 */
public class Solution implements Serializable {
    /**
     * The sequence of states forming the path from the start state to the goal state.
     */
    private ArrayList<AState> solutionPath;
    /**
     * Constructs an empty solution path.
     * Typically used as a default before a path is generated.
     */
    public Solution() {
        this.solutionPath = new ArrayList<>();
    }
    /**
     * Returns the path of the solution.
     *
     * @return A list of states from start to goal.
     */
    public ArrayList<AState> getSolutionPath() {
        return solutionPath;
    }
    /**
     * Sets the solution path.
     *
     * @param solutionPath A list of states representing the solution from start to goal.
     */
    public void setSolutionPath(ArrayList<AState> solutionPath) {
        this.solutionPath = solutionPath;
    }
}