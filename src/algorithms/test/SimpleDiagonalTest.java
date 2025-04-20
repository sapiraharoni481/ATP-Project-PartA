//package algorithms.test;
//
//
//import algorithms.mazeGenerators.*;
//import algorithms.search.*;
//
//import java.util.ArrayList;
//
///**
// * בדיקה פשוטה לתנועות אלכסוניות
// */
//public class SimpleDiagonalTest {
//
//    public static void main(String[] args) {
//        // יצירת מבוך
//        MyMazeGenerator generator = new MyMazeGenerator();
//        Maze maze = generator.generate(10, 10);
//
//        System.out.println("=== בדיקת תנועות אלכסוניות ===");
//        System.out.println("מבוך:");
//        maze.print();
//
//        // יצירת SearchableMaze
//        SearchableMaze searchableMaze = new SearchableMaze(maze);
//
//        // בדיקה של מצבים אפשריים מנקודת ההתחלה
//        AState startState = searchableMaze.getStartState();
//        System.out.println("\nבדיקת מצבים אפשריים מנקודת ההתחלה: " + startState);
//
//        ArrayList<AState> possibleStates = searchableMaze.getAllPossibleStates(startState);
//        System.out.println("מספר מצבים אפשריים: " + possibleStates.size());
//
//        // בדיקה אם יש תנועות אלכסוניות
//        boolean foundDiagonal = false;
//        MazeState startMazeState = (MazeState)startState;
//        Position startPos = startMazeState.getPosition();
//
//        System.out.println("\nרשימת מצבים אפשריים:");
//        for (AState state : possibleStates) {
//            MazeState mazeState = (MazeState)state;
//            Position pos = mazeState.getPosition();
//
//            int rowDiff = Math.abs(pos.getRowIndex() - startPos.getRowIndex());
//            int colDiff = Math.abs(pos.getColumnIndex() - startPos.getColumnIndex());
//
//            boolean isDiagonal = (rowDiff == 1 && colDiff == 1);
//            if (isDiagonal) {
//                foundDiagonal = true;
//            }
//
//            System.out.println("- " + state + " (עלות: " + state.getCost() + ")" +
//                    (isDiagonal ? " [אלכסוני]" : " [ישר]"));
//        }
//
//        System.out.println("\nהאם נמצאו תנועות אלכסוניות: " + (foundDiagonal ? "כן" : "לא"));
//
//        // בדיקת פתרון עם Best First Search
//        BestFirstSearch bestFirstSearch = new BestFirstSearch();
//        Solution solution = bestFirstSearch.solve(searchableMaze);
//
//        System.out.println("\nבדיקת פתרון עם Best First Search:");
//        System.out.println("מספר צעדים בפתרון: " + solution.getSolutionPath().size());
//
//        // בדיקה אם הפתרון כולל תנועות אלכסוניות
//        boolean hasDiagonalInSolution = false;
//        ArrayList<AState> path = solution.getSolutionPath();
//
//        for (int i = 1; i < path.size(); i++) {
//            MazeState current = (MazeState) path.get(i);
//            MazeState previous = (MazeState) path.get(i-1);
//
//            Position currentPos = current.getPosition();
//            Position previousPos = previous.getPosition();
//
//            int rowDiff = Math.abs(currentPos.getRowIndex() - previousPos.getRowIndex());
//            int colDiff = Math.abs(currentPos.getColumnIndex() - previousPos.getColumnIndex());
//
//            if (rowDiff == 1 && colDiff == 1) {
//                hasDiagonalInSolution = true;
//                System.out.println("נמצאה תנועה אלכסונית בפתרון: מ-" + previous + " ל-" + current);
//            }
//        }
//        System.out.println("האם הפתרון כולל תנועות אלכסוניות: " + (hasDiagonalInSolution ? "כן" : "לא"));
//    }
//}
//
////package algorithms.test;
////
////import algorithms.mazeGenerators.Maze;
////import algorithms.mazeGenerators.MyMazeGenerator;
////import algorithms.mazeGenerators.Position;
////import algorithms.search.*;
////
////import java.util.ArrayList;
////
////public class SimpleDiagonalTest {
////    public static void main(String[] args) {
////        // יצירת מבוך קטן בגודל 10x10
////        MyMazeGenerator generator = new MyMazeGenerator();
////        Maze maze = generator.generate(10, 10);
////
////        System.out.println("=== בדיקת תנועות אלכסוניות ===");
////        System.out.println("מבוך:");
////        printMaze(maze);
////
////        // בדיקת getAllPossibleStates
////        SearchableMaze searchableMaze = new SearchableMaze(maze);
////
////        // בדיקת מצבים אפשריים מנקודת ההתחלה
////        AState startState = searchableMaze.getStartState();
////        Position startPosition = ((MazeState)startState).getPosition();
////
////        System.out.println("\nבדיקת מצבים אפשריים מנקודת ההתחלה: " + startPosition);
////
////        ArrayList<AState> possibleStates = searchableMaze.getAllPossibleStates(startState);
////
////        System.out.println("מספר מצבים אפשריים: " + possibleStates.size());
////        System.out.println("רשימת מצבים אפשריים:");
////
////        boolean foundDiagonal = false;
////        for (AState state : possibleStates) {
////            MazeState mazeState = (MazeState)state;
////            Position pos = mazeState.getPosition();
////            boolean isDiagonal = (pos.getRowIndex() != startPosition.getRowIndex() &&
////                    pos.getColumnIndex() != startPosition.getColumnIndex());
////
////            System.out.println("- " + pos + " (עלות: " + state.getCost() + ")" +
////                    (isDiagonal ? " [אלכסוני]" : " [ישר]"));
////
////            if (isDiagonal) {
////                foundDiagonal = true;
////            }
////        }
////
////        System.out.println("האם נמצאו תנועות אלכסוניות: " + (foundDiagonal ? "כן" : "לא"));
////
////        // בדיקת פתרון עם Best First Search
////        BestFirstSearch bestFirstSearch = new BestFirstSearch();
////
////        System.out.println("\nבדיקת פתרון עם Best First Search:");
////        Solution solution = bestFirstSearch.solve(searchableMaze);
////
////        ArrayList<AState> path = solution.getSolutionPath();
////        System.out.println("מספר צעדים בפתרון: " + path.size());
////
////        boolean hasDiagonal = false;
////        for (int i = 1; i < path.size(); i++) {
////            MazeState current = (MazeState)path.get(i);
////            MazeState previous = (MazeState)path.get(i-1);
////
////            Position currentPos = current.getPosition();
////            Position prevPos = previous.getPosition();
////
////            boolean isDiagonal = (currentPos.getRowIndex() != prevPos.getRowIndex() &&
////                    currentPos.getColumnIndex() != prevPos.getColumnIndex());
////
////            if (isDiagonal) {
////                hasDiagonal = true;
////                System.out.println("נמצאה תנועה אלכסונית: " + prevPos + " -> " + currentPos);
////            }
////        }
////
////        System.out.println("האם הפתרון כולל תנועות אלכסוניות: " + (hasDiagonal ? "כן" : "לא"));
////    }
////
////    // פונקציה להדפסת המבוך
////    private static void printMaze(Maze maze) {
////        int[][] mazeData = maze.getMaze();
////        Position start = maze.getStartPosition();
////        Position goal = maze.getGoalPosition();
////
////        for (int i = 0; i < mazeData.length; i++) {
////            for (int j = 0; j < mazeData[0].length; j++) {
////                if (i == start.getRowIndex() && j == start.getColumnIndex()) {
////                    System.out.print("S"); // נקודת התחלה
////                } else if (i == goal.getRowIndex() && j == goal.getColumnIndex()) {
////                    System.out.print("E"); // נקודת סיום
////                } else {
////                    System.out.print(mazeData[i][j]);
////                }
////            }
////            System.out.println();
////        }
////    }
////}