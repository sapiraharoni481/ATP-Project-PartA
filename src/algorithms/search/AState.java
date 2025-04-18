package algorithms.search;

public abstract class AState {
    private String state;
    private double cost;
    private AState cameFrom;

    public AState(String state) {
        this.state = state;
        this.cost = 0;
        this.cameFrom = null;
    }

    public AState(String state, double cost) {
        this.state = state;
        this.cost = cost;
        this.cameFrom = null;
    }

    public String getState() {
        return state;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public AState getCameFrom() {
        return cameFrom;
    }

    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AState aState = (AState) o;
        return state.equals(aState.state);
    }

    @Override
    public int hashCode() {
        return state.hashCode();
    }

}