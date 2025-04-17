package algorithms.search;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {

    @Override
    public Solution solve(ISearchable searchable) {
        if (searchable == null)
            return null;

        // נשתמש בתור רגיל (FIFO) לחיפוש לרוחב
        Queue<AState> openList = new LinkedList<>();
        // מפה לבדיקה האם מצב כבר נמצא בתור
        HashMap<String, AState> openMap = new HashMap<>();
        // סט לבדיקה האם מצב כבר נבדק
        HashSet<String> closedSet = new HashSet<>();
        AState startState = searchable.getStartState();
        startState.setCost(0); // מצב ההתחלה בעלות 0
        openList.add(startState);
        openMap.put(startState.getState(), startState);
        while (!openList.isEmpty()) {
            AState current = openList.poll();
            openMap.remove(current.getState());
            // בדיקה אם הגענו למצב היעד
            if (current.equals(searchable.getGoalState())) {
                return backtrackSolution(current);
            }
            // אם המצב כבר נבדק, נדלג עליו
            if (closedSet.contains(current.getState())) {
                continue;
            }
            // מסמנים את המצב כמצב שכבר בדקנו
            closedSet.add(current.getState());
            this.numberOfNodesEvaluated++;
            // נקבל את כל המצבים האפשריים מהמצב הנוכחי
            ArrayList<AState> neighbors = searchable.getAllPossibleStates(current);
            for (AState neighbor : neighbors) {
                String neighborState = neighbor.getState();
                // אם כבר בדקנו מצב זה או שהוא כבר בתור, נדלג
                if (closedSet.contains(neighborState) || openMap.containsKey(neighborState)) {
                    continue;
                }
                // מעדכנים את המצב הקודם ומוסיפים לתור
                neighbor.setCameFrom(current);
                // בחיפוש לרוחב, העלות משקפת את מספר הצעדים מההתחלה

                neighbor.setCost(current.getCost() + neighbor.getCost());
                openList.add(neighbor);
                openMap.put(neighborState, neighbor);
            }
        }
        // לא מצאנו פתרון
        return new Solution();
    }
    protected Solution backtrackSolution(AState goalState) {
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

    // מחזיר את מספר המצבים שהוערכו
    public int getNumberOfNodesEvaluated() {
        return numberOfNodesEvaluated;
    }
}