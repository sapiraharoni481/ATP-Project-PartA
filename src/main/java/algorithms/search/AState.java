package algorithms.search;

import java.io.Serializable;
/**
 * Abstract class representing a generic state in a searchable problem.
 * Stores information about the state ID, cost, path (came from), and optional heuristic value.
 * Used as a base class for domain-specific states such as positions in a maze.
 *
 * @author Batel
 * @version 1.0
 * @since 2025-04-20
 */
public abstract class AState implements Serializable {
    private String state;
    private double cost;
    private AState cameFrom;
    /**
     * Estimated cost from this state to the goal.
     * Used in informed search algorithms such as Best-First Search.
     */
    protected double heuristic = 0;
    /**
     * Constructs a state with a given string identifier.
     * The cost is initialized to 0, and the parent state is null.
     *
     * @param state Unique identifier of the state (e.g., coordinates as string).
     */
    public AState(String state) {
        this.state = state;
        this.cost = 0;
        this.cameFrom = null;
    }
    /**
     * Constructs a state with a given identifier and cost.
     * The parent state is initialized to null.
     *
     * @param state Unique identifier of the state.
     * @param cost Cost to reach this state from the initial state.
     */
    public AState(String state, double cost) {
        this.state = state;
        this.cost = cost;
        this.cameFrom = null;
    }
    /**
     * Returns the string identifier of the state.
     *
     * @return The state string.
     */
    public String getState() {
        return state;
    }
    /**
     * Returns the cost to reach this state from the initial state.
     *
     * @return The cost value.
     */
    public double getCost() {
        return cost;
    }
    /**
     * Sets the cost to reach this state.
     *
     * @param cost The new cost value.
     */
    public void setCost(double cost) {
        this.cost = cost;
    }
    /**
     * Returns the parent state that led to this one.
     *
     * @return The previous state.
     */
    public AState getCameFrom() {
        return cameFrom;
    }
    /**
     * Returns the heuristic estimate from this state to the goal.
     *
     * @return The heuristic value.
     */
    public double getHeuristic(){
        return  heuristic;
    }
    /**
     * Sets the parent state that led to this one.
     *
     * @param cameFrom The previous state.
     */
    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }
    /**
     * Compares this state to another based on their string identifiers.
     *
     * @param o The object to compare to.
     * @return true if the states are equal; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AState aState = (AState) o;
        return state.equals(aState.state);
    }
    /**
     * Generates a hash code based on the state identifier.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return state.hashCode();
    }

}