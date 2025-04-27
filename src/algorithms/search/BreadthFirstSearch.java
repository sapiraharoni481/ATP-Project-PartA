
package algorithms.search;
import java.util.*;
/**
 * An implementation of the Breadth-First Search algorithm.
 * Explores the shallowest nodes first using a FIFO queue (by default),
 * but can be extended by overriding createOpenList() to use other queue types.
 *
 * Inherits the basic search structure from  ASearchingAlgorithm.
 * Keeps track of visited nodes, uses a map to avoid duplicate exploration,
 * and reconstructs the solution path once the goal is found.
 *
 * @author Batel
 * @version 1.0
 * @since 2025-04-20
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {
    /**
     * Creates the open list (queue) used in the search.
     * By default, this is a simple FIFO queue (LinkedList),
     * but subclasses like BestFirstSearch can override this method
     * to return a  PriorityQueue.
     *
     * @return A new open list queue instance.
     */
    protected Queue<AState> createOpenList() {
        return new LinkedList<>();
    }
    /**
     * Solves the given searchable problem using breadth-first strategy.
     * Avoids re-exploration of already visited or queued states,
     * and updates cost if a better path is found to a state in the open list.
     *
     * @param searchable The problem to solve.
     * @return A Solution containing the path to the goal, or empty if no solution found.
     */
    @Override
    public Solution solve(ISearchable searchable) {
        if (searchable == null)
            return null;
        Queue<AState> openList = createOpenList();// Initialize the open list (queue), which can be overridden in subclasses
        HashMap<String, AState> openMap = new HashMap<>();// A map to keep track of states currently in the open list (for efficient updates)
        HashSet<String> closedSet = new HashSet<>();// A set to store already visited states
        AState startState = searchable.getStartState();// Get the start state and set its cost to 0
        startState.setCost(0);
        openList.add(startState);
        openMap.put(startState.getState(), startState);
        while (!openList.isEmpty()) {
            AState current = openList.poll();// Remove the state with the highest priority (FIFO or based on heuristic)
            openMap.remove(current.getState());
            if (current.equals(searchable.getGoalState())) {return backtrackSolution(current);}// Check if the goal has been reached
            if (closedSet.contains(current.getState())) continue;
            closedSet.add(current.getState());
            numberOfNodesEvaluated++;
            for (AState neighbor : searchable.getAllPossibleStates(current)) {
                String neighborState = neighbor.getState();
                double newCost = current.getCost() + neighbor.getCost();// Calculate cumulative cost from the start
                neighbor.setCost(newCost);
                if (closedSet.contains(neighborState))// Skip if the neighbor has already been evaluated
                    continue;
                if(openMap.containsKey(neighborState)) { // If neighbor is already in open list and we found a cheaper path to it
                    AState existingState = openMap.get(neighborState);
                    if (existingState.getCost() > newCost) {
                        existingState.setCameFrom(current);
                        existingState.setCost(newCost);
                    }
                }else {// New neighbor,set path and add to open list
                    neighbor.setCameFrom(current);
                    neighbor.setCost(newCost);
                    openList.add(neighbor);
                    openMap.put(neighborState, neighbor);
                }
            }
        }

        return new Solution();
    }
    /**
     * Returns the name of this algorithm.
     *
     * @return A string representing the algorithm name.
     */
    @Override
    public String getName() {
        return "Breadth First Search";
    }
}


