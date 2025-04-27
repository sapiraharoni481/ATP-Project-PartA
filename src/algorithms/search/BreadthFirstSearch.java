
package algorithms.search;
import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {
    protected Queue<AState> createOpenList() {
        return new LinkedList<>();
    }
    @Override
    public Solution solve(ISearchable searchable) {
        if (searchable == null)
            return null;
        Queue<AState> openList = createOpenList();
        HashMap<String, AState> openMap = new HashMap<>();
        HashSet<String> closedSet = new HashSet<>();
        AState startState = searchable.getStartState();
        startState.setCost(0);
        openList.add(startState);
        openMap.put(startState.getState(), startState);
        while (!openList.isEmpty()) {
            AState current = openList.poll();
            openMap.remove(current.getState());
            if (current.equals(searchable.getGoalState())) {
                return backtrackSolution(current);
            }
            if (closedSet.contains(current.getState()))
                continue;
            closedSet.add(current.getState());
            numberOfNodesEvaluated++;
            for (AState neighbor : searchable.getAllPossibleStates(current)) {
                String neighborState = neighbor.getState();
                double newCost = current.getCost() + neighbor.getCost();
                neighbor.setCost(newCost);
                if (closedSet.contains(neighborState))
                    continue;
                if(openMap.containsKey(neighborState)) {
                    AState existingState = openMap.get(neighborState);
                    if (existingState.getCost() > newCost) {
                        existingState.setCameFrom(current);
                        existingState.setCost(newCost);
                    }
                }else {
                    neighbor.setCameFrom(current);
                    neighbor.setCost(newCost);
                    openList.add(neighbor);
                    openMap.put(neighborState, neighbor);
                }
            }
        }
        return new Solution();
    }
    protected Solution backtrackSolution(AState goalState) {
        ArrayList<AState> path = new ArrayList<>();
        AState current = goalState;
        while (current != null) {
            path.add(0, current);  // מוסיף את המצב בתחילת הרשימה
            current = current.getCameFrom();
        }
        Solution solution = new Solution();
        solution.setSolutionPath(path);
        return solution;
    }

    @Override
    public String getName() {
        return "Breadth First Search";
    }
}


//package algorithms.search;
//
//import java.util.*;
//
//public class BreadthFirstSearch extends ASearchingAlgorithm {
//
//    protected Queue<AState> createOpenList() {
//        return new LinkedList<>();
//    }
//
//    @Override
//    public Solution solve(ISearchable searchable) {
//        if (searchable == null)
//            return null;
//
//        Queue<AState> openList = createOpenList();
//        HashMap<String, AState> openMap = new HashMap<>();
//        HashSet<String> closedSet = new HashSet<>();
//        AState startState = initializeSearch(searchable, openList, openMap);
//
//        while (!openList.isEmpty()) {
//            AState current = openList.poll();
//            openMap.remove(current.getState());
//
//            if (current.equals(searchable.getGoalState())) {
//                return backtrackSolution(current);
//            }
//
//            if (closedSet.contains(current.getState()))
//                continue;
//
//            closedSet.add(current.getState());
//            numberOfNodesEvaluated++;
//
//            for (AState neighbor : searchable.getAllPossibleStates(current)) {
//                processNeighbor(current, neighbor, openList, openMap, closedSet);
//            }
//        }
//
//        return new Solution();
//    }
//
//    private AState initializeSearch(ISearchable searchable, Queue<AState> openList, HashMap<String, AState> openMap) {
//        AState startState = searchable.getStartState();
//        startState.setCost(0);
//        openList.add(startState);
//        openMap.put(startState.getState(), startState);
//        return startState;
//    }
//
//    private void processNeighbor(AState current, AState neighbor, Queue<AState> openList,
//                                 HashMap<String, AState> openMap, HashSet<String> closedSet) {
//        String neighborState = neighbor.getState();
//        double newCost = current.getCost() + neighbor.getCost();
//        neighbor.setCost(newCost);
//
//        if (closedSet.contains(neighborState)) return;
//
//        if (openMap.containsKey(neighborState)) {
//            AState existing = openMap.get(neighborState);
//            if (existing.getCost() > newCost) {
//                existing.setCameFrom(current);
//                existing.setCost(newCost);
//            }
//        } else {
//            neighbor.setCameFrom(current);
//            openList.add(neighbor);
//            openMap.put(neighborState, neighbor);
//        }
//    }
//
//    protected Solution backtrackSolution(AState goalState) {
//        ArrayList<AState> path = new ArrayList<>();
//        AState current = goalState;
//        while (current != null) {
//            path.add(0, current);
//            current = current.getCameFrom();
//        }
//        Solution solution = new Solution();
//        solution.setSolutionPath(path);
//        return solution;
//    }
//
//    @Override
//    public String getName() {
//        return "Breadth First Search";
//    }
//}