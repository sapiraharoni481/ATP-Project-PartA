package algorithms.search;

import java.util.ArrayList;

/**
 * An abstract base class for search algorithms.
 * Provides a shared implementation for tracking the number of evaluated nodes.
 *
 * Classes extending this must implement the solve() method
 * and can use numberOfNodesEvaluated to count explored states.
 *
 * Implements  ISearchingAlgorithm.
 *
 * @author Your Batel
 * @version 1.0
 * @since 2025-04-20
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    /**
     * The number of nodes (states) that have been evaluated during the search.
     */
    protected int numberOfNodesEvaluated;
    /**
     * Constructs a new search algorithm instance and initializes
     * the evaluated node count to zero.
     */
    public ASearchingAlgorithm() {
        this.numberOfNodesEvaluated = 0;
    }
    /**
     * Returns the number of nodes (states) that were evaluated during the search.
     *
     * @return The number of evaluated nodes.
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return numberOfNodesEvaluated;
    }
    /**
     * Builds the solution path by tracing back from the goal state to the start.
     *
     * @param goalState The goal state reached during the search.
     * @return A  Solution containing the ordered path from start to goal.
     */
    protected Solution backtrackSolution(AState goalState) {
        Solution solution = new Solution();
        ArrayList<AState> path = new ArrayList<>();

        AState current = goalState;
        while (current != null) {
            path.add(0, current);// Adds the state at the beginning of the list
            current = current.getCameFrom();
        }
        solution.setSolutionPath(path);
        return solution;
    }
}