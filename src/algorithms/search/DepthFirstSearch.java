package algorithms.search;

import java.util.*;

public class DepthFirstSearch extends ASearchingAlgorithm {

    @Override
    public Solution solve(ISearchable searchable) {
        if (searchable == null)
            return null;
        Stack<AState> stack = new Stack<>();
        HashSet<String> visited = new HashSet<>();
        AState startState = searchable.getStartState();  // נוסיף את מצב ההתחלה למחסנית
        stack.push(startState);
        while (!stack.isEmpty()) {
            AState current = stack.pop();
            if (visited.contains(current.getState())) {  // בדיקה אם כבר ביקרנו במצב זה
                continue;
            }
            visited.add(current.getState());  // מסמנים את המצב כמצב שכבר בדקנו
            this.numberOfNodesEvaluated++;
            if (current.equals(searchable.getGoalState())) {  // בדיקה אם הגענו למצב היעד
                return backtrackSolution(current);
            }
            ArrayList<AState> neighbors = searchable.getAllPossibleStates(current);  // נקבל את כל המצבים האפשריים מהמצב הנוכחי
            for (int i = neighbors.size() - 1; i >= 0; i--) { // נעבור על השכנים בסדר הפוך כדי לשמור על סדר חיפוש נכון
                AState neighbor = neighbors.get(i);
                if (!visited.contains(neighbor.getState())) {
                    neighbor.setCameFrom(current);
                    neighbor.setCost(current.getCost() + neighbor.getCost());
                    stack.push(neighbor);
                }
            }
        }
        return new Solution(); // אם הגענו לכאן, לא מצאנו פתרון
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
        return "Depth First Search";
    }
}