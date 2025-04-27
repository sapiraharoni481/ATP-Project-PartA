
package algorithms.search;

import java.util.*;
/**
 * Best First Search algorithm.
 * Inherits the search logic from  BreadthFirstSearch and overrides
 * the open list to use a priority queue ordered by heuristic value (h(n)) only.
 *
 * This search strategy prefers states that are estimated to be closer to the goal,
 * without considering the actual path cost so far.
 *
 * @author Batel
 * @version 1.0
 * @since 2025-04-20
 */
public class BestFirstSearch extends BreadthFirstSearch {
    /**
     * Creates a priority queue (open list) that orders states by their heuristic value.
     * This overrides the default FIFO queue used by  BreadthFirstSearch.
     *
     * @return A priority queue ordered by heuristic only.
     */
    @Override
    protected Queue<AState> createOpenList() {
        return new PriorityQueue<>(Comparator.comparingDouble(state -> {
            if (state instanceof MazeState) {
                return ((MazeState) state).getHeuristic(); // // rank by h(n)
            }
            return state.getCost(); // fallback in case of non-MazeState
        }));
    }
    /**
     * Returns the name of the algorithm.
     *
     * @return A string identifying this algorithm.
     */
    @Override
    public String getName() {
        return "Best First Search";
    }
}