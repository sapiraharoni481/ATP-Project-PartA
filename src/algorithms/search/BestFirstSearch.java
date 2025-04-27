//
//////////////////////////////////////////////////////
//package algorithms.search;
//
//import algorithms.mazeGenerators.Position;
//
//import java.util.*;
//
//public class BestFirstSearch extends BreadthFirstSearch {
//
//    public Solution solve(ISearchable searchable) {
//        if (searchable == null)
//            return null;
//        // תור עדיפויות לפי ערך heuristic (קרבה ליעד)
//        PriorityQueue<AState> openList = new PriorityQueue<>(Comparator.comparingDouble(a -> ((MazeState) a).getHeuristic()));
//        HashMap<String, AState> openMap = new HashMap<>();
//        HashSet<String> closedSet = new HashSet<>();
//        AState startState = searchable.getStartState();
//        AState goalState = searchable.getGoalState();
//        // חישוב מרחק למטרה עבור מצב ההתחלה
//        Position goalPos = ((MazeState) goalState).getPosition();
//        Position startPos = ((MazeState) startState).getPosition();
//        double initialHeuristic = Math.abs(goalPos.getRowIndex() - startPos.getRowIndex()) +
//                Math.abs(goalPos.getColumnIndex() - startPos.getColumnIndex());
//        ((MazeState) startState).setHeuristic(initialHeuristic);
//        openList.add(startState);
//        openMap.put(startState.getState(), startState);
//        while (!openList.isEmpty()) {
//            AState current = openList.poll();
//            openMap.remove(current.getState());
//            if (current.equals(goalState)) {
//                return backtrackSolution(current);
//            }
//            if (closedSet.contains(current.getState())) {
//                continue;
//            }
//            closedSet.add(current.getState());
//            this.numberOfNodesEvaluated++;
//            ArrayList<AState> neighbors = searchable.getAllPossibleStates(current);
//            for (AState neighbor : neighbors) {
//                String neighborState = neighbor.getState();
//                if (closedSet.contains(neighborState)) {
//                    continue;
//                }
//                // חישוב המרחק למטרה (heuristic)
//                Position neighborPos = ((MazeState) neighbor).getPosition();
//                double heuristic = Math.abs(goalPos.getRowIndex() - neighborPos.getRowIndex()) +
//                        Math.abs(goalPos.getColumnIndex() - neighborPos.getColumnIndex());
//                ((MazeState) neighbor).setHeuristic(heuristic);
//                // 💡 חישוב העלות הכוללת של הדרך עד עכשיו
//                double newCost = current.getCost() + neighbor.getCost();
//                neighbor.setCost(newCost);
//                if (openMap.containsKey(neighborState)) {
//                    AState existingState = openMap.get(neighborState);
//                    // אם מצאנו דרך זולה יותר למצב קיים
//                    if (newCost < existingState.getCost()) {
//                        // מסירים את המצב הישן מהתור
//                        openList.remove(existingState);
//                        // מעדכנים את העלות והקודם
//                        existingState.setCameFrom(current);
//                        existingState.setCost(newCost);
//                        // מוסיפים מחדש לתור
//                        openList.add(existingState);
//                    }
//                } else {
//                    // מצב חדש שלא ראינו קודם
//                    neighbor.setCameFrom(current);
//                    neighbor.setCost(newCost);
//                    openList.add(neighbor);
//                    openMap.put(neighborState, neighbor);
//                }
//            }
//        }
//        return new Solution(); // אם לא נמצא פתרון
//    }
//    @Override
//    public String getName() {
//        return "Best First Search";
//    }
//}

package algorithms.search;

import java.util.*;

public class BestFirstSearch extends BreadthFirstSearch {

    //    @Override
//    protected Queue<AState> createOpenList() {
//        return new PriorityQueue<>(new Comparator<AState>() {
//            @Override
//            public int compare(AState o1, AState o2) {
//                return (int)(100*(o1.getCost() - o2.getCost())) ;
//            }
//        });
//    }
    @Override
    protected Queue<AState> createOpenList() {
        return new PriorityQueue<>(Comparator.comparingDouble(state -> {
            if (state instanceof MazeState) {
                return ((MazeState) state).getHeuristic(); // דירוג לפי h בלבד
            }
            return state.getCost(); // fallback
        }));
    }


    @Override
    public String getName() {
        return "Best First Search";
    }
}