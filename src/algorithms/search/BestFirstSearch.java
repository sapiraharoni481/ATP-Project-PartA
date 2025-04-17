//package algorithms.search;
//
//import java.util.*;
//
//public class BestFirstSearch extends BreadthFirstSearch {
//    @Override
//    public Solution solve(ISearchable searchable) {
//        if (searchable == null)
//            return null;
//        // נאתחל את המשתנים  תור עדיפויות לפי העלות
//        PriorityQueue<AState> openList = new PriorityQueue<>(Comparator.comparingDouble(AState::getCost));
//        HashSet<String> closedSet = new HashSet<>();
//        AState startState = searchable.getStartState();   //נוסיף את מצב ההתחלה לרשימה הפתוחה
//        openList.add(startState);
//        while (!openList.isEmpty()) {
//            AState current = openList.poll();
//            String currentStateString = current.getState();
//            if (current.equals(searchable.getGoalState())) {   // בדיקה אם הגענו למצב היעד
//                return backtrackSolution(current);
//            }
//            if (closedSet.contains(currentStateString)) {  // אם המצב כבר עובד, נדלג עליוע
//                continue;
//            }
//            closedSet.add(currentStateString);     // מסמנים את המצב כמצב שכבר בדקנו
//            this.numberOfNodesEvaluated++;
//            ArrayList<AState> neighbors = searchable.getAllPossibleStates(current);  // נקבל את כל המצבים האפשריים מהמצב הנוכחי
//            for (AState neighbor : neighbors) {
//                if (!closedSet.contains(neighbor.getState())) {
//                    double newCost = current.getCost() + neighbor.getCost();
//
//                    neighbor.setCameFrom(current);  // אם מצאנו דרך זולה יותר
//                    neighbor.setCost(newCost);
//                    openList.add(neighbor);
//                }
//            }
//        }
//        return new Solution();  //  לא מצאנו פתרון אם
//    }//
//
//
//    private Solution backtrackSolution(AState goalState) {
//        Solution solution = new Solution();
//        ArrayList<AState> path = new ArrayList<>();
//        AState current = goalState;
//        while (current != null) {
//            path.add(0, current);  // הוספה בתחילת המסלול
//            current = current.getCameFrom();
//        }
//
//        solution.setSolutionPath(path);
//        return solution;
//    }
//
//    @Override
//    public String getName() {
//        return "Best First Search";
//    }
//}
//



//package algorithms.search;
//
//import java.util.*;
//
//public class BestFirstSearch extends BreadthFirstSearch {
//    @Override
//    public Solution solve(ISearchable searchable) {
//        if (searchable == null)
//            return null;
//
//        // תור עדיפויות לפי עלות
//        PriorityQueue<AState> openList = new PriorityQueue<>(Comparator.comparingDouble(AState::getCost));
//        // מפה לגישה מהירה למצבים בתור לפי המזהה שלהם
//        HashMap<String, AState> openMap = new HashMap<>();
//        HashSet<String> closedSet = new HashSet<>();
//
//        AState startState = searchable.getStartState();
//        openList.add(startState);
//        openMap.put(startState.getState(), startState);
//
//        while (!openList.isEmpty()) {
//            AState current = openList.poll();
//            openMap.remove(current.getState());
//
//            String currentStateString = current.getState();
//
//            if (current.equals(searchable.getGoalState())) {
//                return backtrackSolution(current);
//            }
//
//            if (closedSet.contains(currentStateString)) {
//                continue;
//            }
//
//            closedSet.add(currentStateString);
//            this.numberOfNodesEvaluated++;
//
//            ArrayList<AState> neighbors = searchable.getAllPossibleStates(current);
//
//            for (AState neighbor : neighbors) {
//                String neighborState = neighbor.getState();
//
//                if (closedSet.contains(neighborState)) {
//                    continue;
//                }
//
//                double newCost = current.getCost() + neighbor.getCost();
//
//                // אם המצב כבר בתור עם עלות גבוהה יותר, עדכן אותו
//                if (openMap.containsKey(neighborState)) {
//                    AState existingState = openMap.get(neighborState);
//                    if (newCost < existingState.getCost()) {
//                        // יש לנו דרך זולה יותר למצב קיים
//
//                        // הסר את המצב הישן מהתור
//                        openList.remove(existingState);
//
//                        // עדכן את העלות והקודם
//                        existingState.setCameFrom(current);
//                        existingState.setCost(newCost);
//
//                        // הוסף מחדש לתור
//                        openList.add(existingState);
//
//                        // אין צורך לעדכן את המפה כי אותו אובייקט
//                    }
//                } else {
//                    // מצב חדש, הוסף לתור ולמפה
//                    neighbor.setCameFrom(current);
//                    neighbor.setCost(newCost);
//                    openList.add(neighbor);
//                    openMap.put(neighborState, neighbor);
//                }
//            }
//        }
//
//        return new Solution();
//    }
//
//    private Solution backtrackSolution(AState goalState) {
//        Solution solution = new Solution();
//        ArrayList<AState> path = new ArrayList<>();
//        AState current = goalState;
//        while (current != null) {
//            path.add(0, current);
//            current = current.getCameFrom();
//        }
//
//        solution.setSolutionPath(path);
//        return solution;
//    }
//
//    @Override
//    public String getName() {
//        return "Best First Search";
//    }
//}

package algorithms.search;

import java.util.*;
// זה של דודי מההרצאה
public class BestFirstSearch extends ASearchingAlgorithm {
    @Override
    public Solution solve(ISearchable searchable) {
        if (searchable == null)
            return null;

        // 1. Define lists, OPEN and CLOSED, and add the start node, s, to OPEN.
        PriorityQueue<AState> OPEN = new PriorityQueue<>(Comparator.comparingDouble(AState::getCost));
        HashSet<String> CLOSED = new HashSet<>();

        AState startState = searchable.getStartState();
        OPEN.add(startState);

        while (!OPEN.isEmpty()) {
            // 2. IF OPEN is empty THEN Exit
            // (this is checked by the while condition)

            // 3. Take from OPEN the node n with the best score, and move it to CLOSED.
            AState current = OPEN.poll();
            CLOSED.add(current.getState());
            this.numberOfNodesEvaluated++;

            // 4. IF n is the goal state
            if (current.equals(searchable.getGoalState())) {
                // return the solution by tracing the path from the goal node to n
                return backtrackSolution(current);
            }

            // 5. Expand node n by getting his successors
            ArrayList<AState> successors = searchable.getAllPossibleStates(current);

            // 6. FOR each successor s:
            for (AState successor : successors) {
                String successorState = successor.getState();

                // IF s is in not in CLOSE continue
                if (CLOSED.contains(successorState)) {
                    continue;
                }

                double newCost = current.getCost() + successor.getCost();

                // Check if successor is already in OPEN
                boolean inOpen = false;
                AState existingState = null;

                // This is inefficient but we have to search through OPEN
                for (AState state : OPEN) {
                    if (state.getState().equals(successorState)) {
                        inOpen = true;
                        existingState = state;
                        break;
                    }
                }

                // IF s is not in OPEN
                if (!inOpen) {
                    // a) apply the cost of arrival to s node
                    successor.setCost(newCost);
                    // b) update s parent node to be n
                    successor.setCameFrom(current);
                    // c) add s to OPEN
                    OPEN.add(successor);
                }
                // ELSE IF this new path shorter then previous one
                else if (newCost < existingState.getCost()) {
                    // a) add to open if its not there (already covered by inOpen check)
                    // b) update s with the shortest path
                    OPEN.remove(existingState);
                    existingState.setCost(newCost);
                    existingState.setCameFrom(current);
                    OPEN.add(existingState);
                }
            }
            // 7. looping structure by sending the algorithm back to the second step.
            // (this is handled by the while loop)
        }

        // No solution found
        return new Solution();
    }

    private Solution backtrackSolution(AState goalState) {
        Solution solution = new Solution();
        ArrayList<AState> path = new ArrayList<>();

        AState current = goalState;
        while (current != null) {
            path.add(0, current);
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