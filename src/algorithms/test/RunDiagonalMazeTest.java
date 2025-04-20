//package algorithms.test;
//
//import algorithms.mazeGenerators.Maze;
//import algorithms.mazeGenerators.Position;
//import algorithms.search.*;
//
//import java.util.ArrayList;
//
//public class RunDiagonalMazeTest {
//    public static void main(String[] args) {
//        // יצירת מבוך ידני בצורת מלבן 5x5
//        int[][] mazeData = {
//                {0, 0, 0, 0, 0},
//                {0, 1, 1, 1, 0},
//                {0, 1, 0, 1, 0},
//                {0, 1, 1, 1, 0},
//                {0, 0, 0, 0, 0}
//        };
//
//        // יצירת מבוך עם נקודת התחלה בפינה השמאלית העליונה ונקודת סיום בפינה הימנית התחתונה
//        Maze manualMaze = new Maze(mazeData, new Position(0, 0), new Position(4, 4));
//
//        // הפיכת המבוך לבעיית חיפוש
//        SearchableMaze searchableMaze = new SearchableMaze(manualMaze);
//
//        // פתרון המבוך עם האלגוריתמים השונים
//        solveProblem(searchableMaze, new BreadthFirstSearch());
//        solveProblem(searchableMaze, new DepthFirstSearch());
//        solveProblem(searchableMaze, new BestFirstSearch());
//    }
//
//    private static void solveProblem(ISearchable domain, ISearchingAlgorithm searcher) {
//        // Solve a searching problem with a searcher
//        Solution solution = searcher.solve(domain);
//        System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", searcher.getName(), searcher.getNumberOfNodesEvaluated()));
//
//        // Printing Solution Path
//        System.out.println("Solution path:");
//        ArrayList<AState> solutionPath = solution.getSolutionPath();
//        for (int i = 0; i < solutionPath.size(); i++) {
//            System.out.println(String.format("%s. %s", i, solutionPath.get(i)));
//        }
//
//        double totalCost = 0;
//        AState goal = solutionPath.get(solutionPath.size() - 1);
//        System.out.println("Total path cost: " + goal.getCost());
//
//        System.out.println("Number of steps: " + solutionPath.size());
//    }
//}