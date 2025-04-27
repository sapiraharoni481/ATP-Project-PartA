package algorithms.search;
import java.util.ArrayList;
/**
 * The ISearchable interface represents a generic problem domain
 * that can be searched using search algorithms.
 * <p>
 * Classes implementing this interface provide the starting state,
 * the goal state, and a way to retrieve all valid moves (states) from a given state.
 * </p>
 *
 * This interface is typically used in search problems like mazes, graphs, or puzzles.
 *
 * @author Batel
 * @version 1.0
 * @since 2025-04-20
 */
public interface ISearchable {
    /**
     * Returns the initial state of the search problem.
     *
     * @return The start AState.
     */
    AState getStartState();
    /**
     * Returns the goal state that the algorithm aims to reach.
     *
     * @return The goal AState.
     */
    AState getGoalState();
    /**
     * Given a current state, returns all valid states that can be reached from it.
     *
     * @param state The current AState to expand.
     * @return A list of possible next AState instances.
     */
    ArrayList<AState> getAllPossibleStates(AState state);
}