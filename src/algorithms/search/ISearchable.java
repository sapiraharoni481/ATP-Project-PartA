package algorithms.search;
import java.util.ArrayList;

public interface ISearchable {
    AState getStartState();
    AState getGoalState();
    double getStepCost(AState from, AState to);
    ArrayList<AState> getAllPossibleStates(AState state);
}