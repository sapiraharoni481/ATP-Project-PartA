//package algorithms.test;
//
//import algorithms.mazeGenerators.Maze;
//import algorithms.mazeGenerators.Position;
//import algorithms.search.*;
//
//import java.util.ArrayList;
//
//public class TestSearchAlgorithms2{
//
//    public static void main(String[] args) {
//        // מבוך קטן 5x5
//        int[][] mazeData = {
//                {0, 0, 1, 1, 1},
//                {1, 0, 1, 0, 0},
//                {1, 0, 0, 0, 1},
//                {1, 1, 1, 0, 1},
//                {1, 1, 1, 0, 0}
//        };
//
//        // נקודת התחלה וסיום
//        Position start = new Position(0, 0);
//        Position goal = new Position(4, 4);
//
//        Maze maze = new Maze(mazeData, start, goal);
//        SearchableMaze searchableMaze = new SearchableMaze(maze);
//
//        // הרצת כל אלגוריתם
//        runSearch(searchableMaze, new BreadthFirstSearch());
//        runSearch(searchableMaze, new DepthFirstSearch());
//        runSearch(searchableMaze, new BestFirstSearch());
//    }
//
//    private static void runSearch(ISearchable maze, ISearchingAlgorithm searcher) {
//        System.out.println("Running: " + searcher.getName());
//        Solution solution = searcher.solve(maze);
//        System.out.println("Nodes evaluated: " + searcher.getNumberOfNodesEvaluated());
//
//        ArrayList<AState> path = solution.getSolutionPath();
//        System.out.println("Path:");
//        for (int i = 0; i < path.size(); i++) {
//            System.out.println(i + ". " + path.get(i));
//        }
//
//        double totalCost = path.get(path.size() - 1).getCost();
//        System.out.println("Total cost: " + totalCost);
//        System.out.println("Path length: " + path.size());
//        System.out.println("--------------------------------------------------\n");
//    }
//}