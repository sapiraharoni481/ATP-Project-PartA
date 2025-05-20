package algorithms.search;
import java.util.*;

/**
 * The DepthFirstSearch class implements a search algorithm based on
 * the Depth-First Search (DFS) strategy using a LIFO stack.
 * <p>
 * This class is a concrete implementation of the ASearchingAlgorithm abstract class.
 * It explores the deepest nodes first and backtracks when no further path is available.
 *
 * @author Batel
 * @version 1.0
 * @since 2025-04-20
 */
public class DepthFirstSearch extends ASearchingAlgorithm {
    /**
     * Solves the given searchable problem using DFS strategy.
     *
     * @param searchable The problem domain implementing ISearchable
     * @return A  Solution representing the path from start to goal,
     * or an empty solution if no path was found.
     */
    @Override
    public Solution solve(ISearchable searchable) {
        if (searchable == null)
            return null;
        Stack<AState> stack = new Stack<>();
        HashSet<String> visited = new HashSet<>();
        AState startState = searchable.getStartState();  // Add the start state to the stack
        stack.push(startState);
        while (!stack.isEmpty()) {
            AState current = stack.pop();
            // Skip if this state was already visited
            if (visited.contains(current.getState())) {
                continue;
            }
            visited.add(current.getState());
            this.numberOfNodesEvaluated++;
            // If goal state is found, reconstruct the solution path
            if (current.equals(searchable.getGoalState())) {
                return backtrackSolution(current);
            }
            ArrayList<AState> neighbors = searchable.getAllPossibleStates(current);
            for (int i = neighbors.size() - 1; i >= 0; i--) {   // Traverse neighbors in reverse order to maintain DFS order
                AState neighbor = neighbors.get(i);
                if (!visited.contains(neighbor.getState())) {
                    neighbor.setCameFrom(current);
                    neighbor.setCost(current.getCost() + neighbor.getCost());
                    stack.push(neighbor);
                }
            }
        }
        return new Solution();
    }
    @Override
    public String getName() {
        return "Depth First Search";
    }
}