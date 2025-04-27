//package algorithms.test;
//
//import algorithms.mazeGenerators.*;
//import algorithms.search.*;
//
//import java.util.ArrayList;
//
///**
// * מחלקת בדיקה לאלגוריתמי חיפוש
// */
//public class TestSearchAlgorithms {
//
//    public static void main(String[] args) {
//        testBasicMazeSearch();
//        testAlgorithmPerformance();
//        testSolveComplexMaze();
//    }
//
//    /**
//     * בדיקה בסיסית של אלגוריתמי חיפוש על מבוך פשוט
//     */
//    private static void testBasicMazeSearch() {
//        System.out.println("===== בדיקה בסיסית של אלגוריתמי חיפוש =====");
//
//        // יצירת מבוך באמצעות MyMazeGenerator
//        MyMazeGenerator mazeGenerator = new MyMazeGenerator();
//        Maze maze = mazeGenerator.generate(10, 10);
//
//        System.out.println("מבוך לבדיקה:");
//        maze.print();
//
//        // הצגת נקודות התחלה וסיום
//        System.out.println("נקודת התחלה: " + maze.getStartPosition());
//        System.out.println("נקודת סיום: " + maze.getGoalPosition());
//
//        // בדיקת כל אלגוריתם
//        SearchableMaze searchableMaze = new SearchableMaze(maze);
//
//        testAlgorithm(new BreadthFirstSearch(), searchableMaze, "BFS");
//        testAlgorithm(new DepthFirstSearch(), searchableMaze, "DFS");
//        testAlgorithm(new BestFirstSearch(), searchableMaze, "Best First Search");
//    }
//
//    /**
//     * בדיקת ביצועים של אלגוריתמי החיפוש
//     */
//    private static void testAlgorithmPerformance() {
//        System.out.println("\n===== בדיקת ביצועים של אלגוריתמי חיפוש =====");
//
//        // יצירת מבוך גדול יותר עם MyMazeGenerator
//        MyMazeGenerator generator = new MyMazeGenerator();
//        Maze largeMaze = generator.generate(20, 20);
//        SearchableMaze searchableMaze = new SearchableMaze(largeMaze);
//
//        System.out.println("מבוך גדול (20x20)");
//
//        // בדיקת זמן חיפוש ומספר מצבים שנבדקו
//        ISearchingAlgorithm[] algorithms = {
//                new BreadthFirstSearch(),
//                new DepthFirstSearch(),
//                new BestFirstSearch()
//        };
//
//        for (ISearchingAlgorithm algorithm : algorithms) {
//            long startTime = System.currentTimeMillis();
//            Solution solution = algorithm.solve(searchableMaze);
//            long endTime = System.currentTimeMillis();
//
//            System.out.println(String.format("אלגוריתם %s:", algorithm.getName()));
//            System.out.println(String.format("- זמן ריצה: %d מילישניות", endTime - startTime));
//            System.out.println(String.format("- מספר מצבים שנבדקו: %d", algorithm.getNumberOfNodesEvaluated()));
//            System.out.println(String.format("- אורך הפתרון: %d צעדים", solution.getSolutionPath().size()));
//            System.out.println();
//        }
//    }
//
//    /**
//     * פתרון של מבוך מורכב יותר
//     */
//    private static void testSolveComplexMaze() {
//        System.out.println("\n===== פתרון מבוך מורכב =====");
//
//        // יצירת מבוך מורכב יותר עם MyMazeGenerator
//        MyMazeGenerator generator = new MyMazeGenerator();
//        Maze complexMaze = generator.generate(30, 30);
//        SearchableMaze searchableMaze = new SearchableMaze(complexMaze);
//
//        System.out.println("מבוך מורכב (30x30)");
//
//        // פתרון עם Best First Search
//        ISearchingAlgorithm bestFirst = new BestFirstSearch();
//
//        long startTime = System.currentTimeMillis();
//        Solution solution = bestFirst.solve(searchableMaze);
//        long endTime = System.currentTimeMillis();
//
//        System.out.println("Best First Search:");
//        System.out.println("- זמן ריצה: " + (endTime - startTime) + " מילישניות");
//        System.out.println("- מספר מצבים שנבדקו: " + bestFirst.getNumberOfNodesEvaluated());
//        System.out.println("- אורך המסלול: " + solution.getSolutionPath().size() + " צעדים");
//
//        // בדיקה אם יש תנועות אלכסוניות בפתרון
//        boolean hasDiagonalMoves = checkForDiagonalMoves(solution);
//        System.out.println("- האם הפתרון כולל תנועות אלכסוניות: " + (hasDiagonalMoves ? "כן" : "לא"));
//
//        if (!hasDiagonalMoves) {
//            System.out.println("  שים לב: על פי דרישות המשימה, Best First Search צריך לתמוך בתנועות אלכסוניות.");
//        }
//    }
//
//    /**
//     * בדיקת אלגוריתם חיפוש ספציפי
//     */
//    private static void testAlgorithm(ISearchingAlgorithm algorithm, ISearchable problem, String algorithmName) {
//        System.out.println("\nבדיקת " + algorithmName + ":");
//
//        long startTime = System.currentTimeMillis();
//        Solution solution = algorithm.solve(problem);
//        long endTime = System.currentTimeMillis();
//
//        System.out.println("- זמן ריצה: " + (endTime - startTime) + " מילישניות");
//        System.out.println("- מספר מצבים שנבדקו: " + algorithm.getNumberOfNodesEvaluated());
//        System.out.println("- אורך הפתרון: " + solution.getSolutionPath().size() + " צעדים");
//
//        // הדפסת הפתרון (מוגבל למספר צעדים מסוים)
//        printSolutionPreview(solution);
//    }
//
//    /**
//     * הדפסת תצוגה מקדימה של הפתרון
//     */
//    private static void printSolutionPreview(Solution solution) {
//        ArrayList<AState> path = solution.getSolutionPath();
//
//        int maxStepsToPrint = 5; // מספר הצעדים להדפסה
//        int stepsToPrint = Math.min(path.size(), maxStepsToPrint);
//
//        System.out.println("- התחלת הפתרון (5 צעדים ראשונים):");
//        for (int i = 0; i < stepsToPrint; i++) {
//            System.out.println("  " + (i + 1) + ". " + path.get(i));
//        }
//
//        if (path.size() > maxStepsToPrint) {
//            System.out.println("  ...ועוד " + (path.size() - maxStepsToPrint) + " צעדים");
//        }
//    }
//
//    /**
//     * בדיקה אם הפתרון כולל תנועות אלכסוניות
//     */
//    private static boolean checkForDiagonalMoves(Solution solution) {
//        ArrayList<AState> path = solution.getSolutionPath();
//
//        for (int i = 1; i < path.size(); i++) {
//            if (path.get(i) instanceof MazeState && path.get(i-1) instanceof MazeState) {
//                MazeState current = (MazeState) path.get(i);
//                MazeState previous = (MazeState) path.get(i-1);
//
//                // נשיג את הפוזיציה מכל state
//                Position currentPos = new Position(current.getRow(), current.getCol());
//                Position previousPos = new Position(previous.getRow(), previous.getCol());
//
//                // בדיקה אם המעבר הוא אלכסוני
//                int rowDiff = Math.abs(currentPos.getRowIndex() - previousPos.getRowIndex());
//                int colDiff = Math.abs(currentPos.getColumnIndex() - previousPos.getColumnIndex());
//
//                // אם השינוי הוא 1,1 אז זו תנועה אלכסונית
//                if (rowDiff == 1 && colDiff == 1) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }
//}//package algorithms.test;
////
////import algorithms.mazeGenerators.*;
////import algorithms.search.*;
////
////import java.util.ArrayList;
////
/////**
//// * מחלקת בדיקה לאלגוריתמי חיפוש
//// */
////public class TestSearchAlgorithms {
////
////    public static void main(String[] args) {
////        testBasicMazeSearch();
////        testAlgorithmPerformance();
////        testSolveComplexMaze();
////    }
////
////    /**
////     * בדיקה בסיסית של אלגוריתמי חיפוש על מבוך פשוט
////     */
////    private static void testBasicMazeSearch() {
////        System.out.println("===== בדיקה בסיסית של אלגוריתמי חיפוש =====");
////
////        // יצירת מבוך באמצעות MyMazeGenerator
////        MyMazeGenerator mazeGenerator = new MyMazeGenerator();
////        Maze maze = mazeGenerator.generate(10, 10);
////
////        System.out.println("מבוך לבדיקה:");
////        maze.print();
////
////        // הצגת נקודות התחלה וסיום
////        System.out.println("נקודת התחלה: " + maze.getStartPosition());
////        System.out.println("נקודת סיום: " + maze.getGoalPosition());
////
////        // בדיקת כל אלגוריתם
////        SearchableMaze searchableMaze = new SearchableMaze(maze);
////
////        testAlgorithm(new BreadthFirstSearch(), searchableMaze, "BFS");
////        testAlgorithm(new DepthFirstSearch(), searchableMaze, "DFS");
////        testAlgorithm(new BestFirstSearch(), searchableMaze, "Best First Search");
////    }
////
////    /**
////     * בדיקת ביצועים של אלגוריתמי החיפוש
////     */
////    private static void testAlgorithmPerformance() {
////        System.out.println("\n===== בדיקת ביצועים של אלגוריתמי חיפוש =====");
////
////        // יצירת מבוך גדול יותר עם MyMazeGenerator
////        MyMazeGenerator generator = new MyMazeGenerator();
////        Maze largeMaze = generator.generate(20, 20);
////        SearchableMaze searchableMaze = new SearchableMaze(largeMaze);
////
////        System.out.println("מבוך גדול (20x20)");
////
////        // בדיקת זמן חיפוש ומספר מצבים שנבדקו
////        ISearchingAlgorithm[] algorithms = {
////                new BreadthFirstSearch(),
////                new DepthFirstSearch(),
////                new BestFirstSearch()
////        };
////
////        for (ISearchingAlgorithm algorithm : algorithms) {
////            long startTime = System.currentTimeMillis();
////            Solution solution = algorithm.solve(searchableMaze);
////            long endTime = System.currentTimeMillis();
////
////            System.out.println(String.format("אלגוריתם %s:", algorithm.getName()));
////            System.out.println(String.format("- זמן ריצה: %d מילישניות", endTime - startTime));
////            System.out.println(String.format("- מספר מצבים שנבדקו: %d", algorithm.getNumberOfNodesEvaluated()));
////            System.out.println(String.format("- אורך הפתרון: %d צעדים", solution.getSolutionPath().size()));
////            System.out.println();
////        }
////    }
////
////    /**
////     * פתרון של מבוך מורכב יותר
////     */
////    private static void testSolveComplexMaze() {
////        System.out.println("\n===== פתרון מבוך מורכב =====");
////
////        // יצירת מבוך מורכב יותר עם MyMazeGenerator
////        MyMazeGenerator generator = new MyMazeGenerator();
////        Maze complexMaze = generator.generate(30, 30);
////        SearchableMaze searchableMaze = new SearchableMaze(complexMaze);
////
////        System.out.println("מבוך מורכב (30x30)");
////
////        // פתרון עם Best First Search
////        ISearchingAlgorithm bestFirst = new BestFirstSearch();
////
////        long startTime = System.currentTimeMillis();
////        Solution solution = bestFirst.solve(searchableMaze);
////        long endTime = System.currentTimeMillis();
////
////        System.out.println("Best First Search:");
////        System.out.println("- זמן ריצה: " + (endTime - startTime) + " מילישניות");
////        System.out.println("- מספר מצבים שנבדקו: " + bestFirst.getNumberOfNodesEvaluated());
////        System.out.println("- אורך המסלול: " + solution.getSolutionPath().size() + " צעדים");
////
////        // בדיקה אם יש תנועות אלכסוניות בפתרון
////        boolean hasDiagonalMoves = checkForDiagonalMoves(solution);
////        System.out.println("- האם הפתרון כולל תנועות אלכסוניות: " + (hasDiagonalMoves ? "כן" : "לא"));
////
////        if (!hasDiagonalMoves) {
////            System.out.println("  שים לב: על פי דרישות המשימה, Best First Search צריך לתמוך בתנועות אלכסוניות.");
////        }
////    }
////
////    /**
////     * בדיקת אלגוריתם חיפוש ספציפי
////     */
////    private static void testAlgorithm(ISearchingAlgorithm algorithm, ISearchable problem, String algorithmName) {
////        System.out.println("\nבדיקת " + algorithmName + ":");
////
////        long startTime = System.currentTimeMillis();
////        Solution solution = algorithm.solve(problem);
////        long endTime = System.currentTimeMillis();
////
////        System.out.println("- זמן ריצה: " + (endTime - startTime) + " מילישניות");
////        System.out.println("- מספר מצבים שנבדקו: " + algorithm.getNumberOfNodesEvaluated());
////        System.out.println("- אורך הפתרון: " + solution.getSolutionPath().size() + " צעדים");
////
////        // הדפסת הפתרון (מוגבל למספר צעדים מסוים)
////        printSolutionPreview(solution);
////    }
////
////    /**
////     * הדפסת תצוגה מקדימה של הפתרון
////     */
////    private static void printSolutionPreview(Solution solution) {
////        ArrayList<AState> path = solution.getSolutionPath();
////
////        int maxStepsToPrint = 5; // מספר הצעדים להדפסה
////        int stepsToPrint = Math.min(path.size(), maxStepsToPrint);
////
////        System.out.println("- התחלת הפתרון (5 צעדים ראשונים):");
////        for (int i = 0; i < stepsToPrint; i++) {
////            System.out.println("  " + (i + 1) + ". " + path.get(i));
////        }
////
////        if (path.size() > maxStepsToPrint) {
////            System.out.println("  ...ועוד " + (path.size() - maxStepsToPrint) + " צעדים");
////        }
////    }
////
////    /**
////     * בדיקה אם הפתרון כולל תנועות אלכסוניות
////     */
////    private static boolean checkForDiagonalMoves(Solution solution) {
////        ArrayList<AState> path = solution.getSolutionPath();
////
////        for (int i = 1; i < path.size(); i++) {
////            if (path.get(i) instanceof MazeState && path.get(i-1) instanceof MazeState) {
////                MazeState current = (MazeState) path.get(i);
////                MazeState previous = (MazeState) path.get(i-1);
////
////                // נשיג את הפוזיציה מכל state
////                Position currentPos = new Position(current.getRow(), current.getCol());
////                Position previousPos = new Position(previous.getRow(), previous.getCol());
////
////                // בדיקה אם המעבר הוא אלכסוני
////                int rowDiff = Math.abs(currentPos.getRowIndex() - previousPos.getRowIndex());
////                int colDiff = Math.abs(currentPos.getColumnIndex() - previousPos.getColumnIndex());
////
////                // אם השינוי הוא 1,1 אז זו תנועה אלכסונית
////                if (rowDiff == 1 && colDiff == 1) {
////                    return true;
////                }
////            }
////        }
////
////        return false;
////    }
////}