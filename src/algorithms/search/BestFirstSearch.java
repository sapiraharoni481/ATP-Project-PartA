package algorithms.search;

import java.util.*;

public class BestFirstSearch extends BreadthFirstSearch {
    @Override
    public Solution solve(ISearchable searchable) {
        if (searchable == null)
            return null;

        // נשתמש בתור עדיפויות לפי העלות
        PriorityQueue<AState> openList = new PriorityQueue<>(Comparator.comparingDouble(AState::getCost));
        // מפה לגישה מהירה למצבים בתור לפי המזהה שלהם
        HashMap<String, AState> openMap = new HashMap<>();
        HashSet<String> closedSet = new HashSet<>();

        AState startState = searchable.getStartState();
        openList.add(startState);
        openMap.put(startState.getState(), startState);

        while (!openList.isEmpty()) {
            AState current = openList.poll();
            openMap.remove(current.getState());  // מסירים מהמפה בנוסף להסרה מהתור

            // בדיקה אם הגענו למצב היעד
            if (current.equals(searchable.getGoalState())) {
                return backtrackSolution(current);
            }

            // אם המצב כבר עובד, נדלג עליו
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

                // אם כבר בדקנו מצב זה, נדלג
                if (closedSet.contains(neighborState)) {
                    continue;
                }

                // נחשב את העלות החדשה להגיע למצב השכן
                double newCost = current.getCost() + neighbor.getCost();

                // בדיקה אם המצב כבר נמצא ברשימה הפתוחה
                if (openMap.containsKey(neighborState)) {
                    AState existingState = openMap.get(neighborState);

                    // אם מצאנו דרך זולה יותר למצב קיים
                    if (newCost < existingState.getCost()) {
                        // מסירים את המצב הישן מהתור
                        openList.remove(existingState);

                        // מעדכנים את העלות והקודם
                        existingState.setCameFrom(current);
                        existingState.setCost(newCost);

                        // מוסיפים מחדש לתור
                        openList.add(existingState);
                        // אין צורך לעדכן את המפה כי מדובר באותו אובייקט
                    }
                } else {
                    // מצב חדש שלא ראינו קודם
                    neighbor.setCameFrom(current);
                    neighbor.setCost(newCost);
                    openList.add(neighbor);
                    openMap.put(neighborState, neighbor);
                }
            }
        }

        // לא מצאנו פתרון
        return new Solution();
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