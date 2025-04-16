package algorithms.search;

import java.util.*;

public class BestFirstSearch extends BreadthFirstSearch {

    @Override
    public Solution solve(ISearchable searchable) {
        if (searchable == null)
            return null;

        // נאתחל את המשתנים  תור עדיפויות לפי העלות
        PriorityQueue<AState> openList = new PriorityQueue<>(Comparator.comparingDouble(AState::getCost));
        HashSet<String> closedSet = new HashSet<>();

        AState startState = searchable.getStartState();   //נוסיף את מצב ההתחלה לרשימה הפתוחה
        openList.add(startState);

        while (!openList.isEmpty()) {
            AState current = openList.poll();
            String currentStateString = current.getState();

            if (current.equals(searchable.getGoalState())) {   // בדיקה אם הגענו למצב היעד
                return backtrackSolution(current);
            }

            if (closedSet.contains(currentStateString)) {  // אם המצב כבר עובד, נדלג עליוע
                continue;
            }
            closedSet.add(currentStateString);     // מסמנים את המצב כמצב שכבר בדקנו
            this.numberOfNodesEvaluated++;
            ArrayList<AState> neighbors = searchable.getAllPossibleStates(current);  // נקבל את כל המצבים האפשריים מהמצב הנוכחי
            for (AState neighbor : neighbors) {
                if (!closedSet.contains(neighbor.getState())) {
                    double newCost = current.getCost() + neighbor.getCost();

                    neighbor.setCameFrom(current);  // אם מצאנו דרך זולה יותר
                    neighbor.setCost(newCost);
                    openList.add(neighbor);
                }
            }
        }
        return new Solution();  //  לא מצאנו פתרון אם
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
        return "Best First Search";
    }
}