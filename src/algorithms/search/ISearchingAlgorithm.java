package algorithms.search;
/**
 * The ISearchingAlgorithm interface defines the basic operations
 * required for implementing search algorithms over a searchable domain.
 * <p>
 * It provides methods for solving a problem, retrieving the algorithm name,
 * and tracking the number of evaluated nodes during the search.
 * </p>
 *
 * Typical implementations include algorithms like BFS, DFS, and Best-First Search.
 *
 * @author Batel
 * @version 1.0
 * @since 2025-04-20
 */
public interface ISearchingAlgorithm {
    /**
     * Solves the given searchable problem and returns a solution path.
     *
     * @param searchable The problem domain to solve.
     * @return A Solution representing the path from the start to the goal state.
     */
    Solution solve(ISearchable searchable);
    /**
     * Returns the name of the search algorithm.
     *
     * @return A string containing the algorithm's name.
     */
    String getName();
    /**
     * Returns the number of nodes that were evaluated during the search process.
     *
     * @return The number of evaluated nodes.
     */
    int getNumberOfNodesEvaluated();
}