package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    protected int numberOfNodesEvaluated;

    public ASearchingAlgorithm() {
        this.numberOfNodesEvaluated = 0;
    }
    @Override
    public int getNumberOfNodesEvaluated() {
        return numberOfNodesEvaluated;
    }
}//