package algorithms.search;
import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {
    @Override
    public Solution solve(ISearchable searchable) {
        if (searchable == null)
            return null;
        Queue<AState> openList = new LinkedList<>();     // נאתחל את המשתנים
        HashSet<String> closedSet = new HashSet<>();
        AState startState = searchable.getStartState();    // נוסיף את מצב ההתחלה לרשימה הפתוחה
        openList.add(startState);
        while (!openList.isEmpty()) {
            AState current = openList.poll();
            String currentStateString = current.getState();
            if (current.equals(searchable.getGoalState())) {  // בדיקה אם הגענו למצב היעד
                return backtrackSolution(current);
            }
            if (closedSet.contains(currentStateString)) {      // אם המצב כבר עובד מדלגים עליו
                continue;
            }
            closedSet.add(currentStateString);    // מסמנים את המצב כמצב שכבר בדקנו
            this.numberOfNodesEvaluated++;
            ArrayList<AState> neighbors = searchable.getAllPossibleStates(current);   // נקבל את כל המצבים האפשריים מהמצב הנוכחי
            for (AState neighbor : neighbors) {
                if (!closedSet.contains(neighbor.getState())) {
                    neighbor.setCameFrom(current);
                    neighbor.setCost(current.getCost() + neighbor.getCost());
                    openList.add(neighbor);
                }
            }
        }
        return new Solution();  // אם הגענו לכאן, לא מצאנו פתרון
    }
    private Solution backtrackSolution(AState goalState) {
        Solution solution = new Solution();
        ArrayList<AState> path = new ArrayList<>();
        AState current = goalState;
        while (current != null) {
            path.add(0, current);  // הוספה בתחילת המסלול
            current = current.getCameFrom();
        }
        solution.setSolutionPath(path);
        return solution;
    }

    @Override
    public String getName() {
        return "Breadth First Search";
    }
}