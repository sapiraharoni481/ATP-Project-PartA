//package algorithms.test;
//import algorithms.mazeGenerators.Position;
//import algorithms.mazeGenerators.Maze;
//import algorithms.search.*;
//
//public class ManualMazeBuilder {
//    public static void main(String[] args) {
//        int[][] manualMaze = {
//                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
//                {1, 1, 1, 0, 1, 0, 1, 1, 0, 1},
//                {1, 0, 0, 0, 1, 0, 1, 0, 0, 1},
//                {1, 0, 1, 1, 1, 0, 1, 1, 1, 1},
//                {1, 0, 1, 0, 0, 0, 0, 0, 0, 0},
//                {1, 0, 1, 0, 1, 1, 1, 1, 1, 0},
//                {1, 0, 0, 0, 1, 0, 0, 0, 1, 0},
//                {1, 1, 1, 0, 1, 0, 1, 0, 1, 0},
//                {1, 1, 1, 0, 0, 0, 1, 0, 0, 0},
//        };
//
//        Position start = new Position(0, 0);
//        Position goal = new Position(9, 9);
//
//        Maze maze = new Maze(manualMaze, start, goal);
//        maze.print(); // הצגת המבוך עם S ו-E
//
//        ISearchable searchableMaze = new SearchableMaze(maze);
//
//        System.out.println("\nRunning BFS:");
//        solveProblem(searchableMaze, new BreadthFirstSearch());
//
//        System.out.println("\nRunning BestFS:");
//        solveProblem(searchableMaze, new BestFirstSearch());
//
//        System.out.println("\nRunning DFS:");
//        solveProblem(searchableMaze, new DepthFirstSearch());
//    }
//
//    private static void solveProblem(ISearchable domain, ISearchingAlgorithm searcher) {
//        Solution solution = searcher.solve(domain);
//        System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", searcher.getName(), searcher.getNumberOfNodesEvaluated()));
//
//        if (solution != null) {
//            System.out.println("Steps in path: " + solution.getSolutionPath().size());
//            AState goal = solution.getSolutionPath().get(solution.getSolutionPath().size() - 1);
//            System.out.println("Total path cost: " + goal.getCost());
//        } else {
//            System.out.println("❌ No solution found.");
//        }
//    }
//
//}
//
//
//
//
