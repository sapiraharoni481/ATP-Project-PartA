package algorithms.test;


import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;

import java.util.ArrayList;

public class CompleteSearchAlgorithmsTest {
    public static void main(String[] args) {
        // יצירת מבוך קטן בגודל 10x10
        MyMazeGenerator generator = new MyMazeGenerator();
        Maze maze = generator.generate(100, 100);

        System.out.println("=== בדיקת אלגוריתמי חיפוש ותנועות אלכסוניות ===");
        System.out.println("מבוך:");
        printMaze(maze);

        // בדיקת getAllPossibleStates
        SearchableMaze searchableMaze = new SearchableMaze(maze);

        // בדיקת מצבים אפשריים מנקודת ההתחלה
        AState startState = searchableMaze.getStartState();
        Position startPosition = ((MazeState)startState).getPosition();

        System.out.println("\nבדיקת מצבים אפשריים מנקודת ההתחלה: " + startPosition);

        ArrayList<AState> possibleStates = searchableMaze.getAllPossibleStates(startState);

        System.out.println("מספר מצבים אפשריים: " + possibleStates.size());
        System.out.println("רשימת מצבים אפשריים:");

        boolean foundDiagonal = false;
        for (AState state : possibleStates) {
            MazeState mazeState = (MazeState)state;
            Position pos = mazeState.getPosition();
            boolean isDiagonal = (pos.getRowIndex() != startPosition.getRowIndex() &&
                    pos.getColumnIndex() != startPosition.getColumnIndex());

            System.out.println("- " + pos + " (עלות: " + state.getCost() + ")" +
                    (isDiagonal ? " [אלכסוני]" : " [ישר]"));

            if (isDiagonal) {
                foundDiagonal = true;
            }
        }

        System.out.println("האם נמצאו תנועות אלכסוניות: " + (foundDiagonal ? "כן" : "לא"));

        // יצירת האלגוריתמים
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        DepthFirstSearch dfs = new DepthFirstSearch();
        BestFirstSearch bestFirstSearch = new BestFirstSearch();

        // בדיקת BFS
        System.out.println("\n=== בדיקת BFS ===");
        Solution bfsSolution = bfs.solve(searchableMaze);
        checkSolution(bfsSolution, "BFS");

        // בדיקת DFS
        System.out.println("\n=== בדיקת DFS ===");
        Solution dfsSolution = dfs.solve(searchableMaze);
        checkSolution(dfsSolution, "DFS");

        // בדיקת Best First Search
        System.out.println("\n=== בדיקת Best First Search ===");
        Solution bestSolution = bestFirstSearch.solve(searchableMaze);
        checkSolution(bestSolution, "Best First Search");

        // השוואה בין האלגוריתמים
        System.out.println("\n=== השוואת האלגוריתמים ===");
        System.out.println("BFS:");
        System.out.println("- מספר צעדים: " + bfsSolution.getSolutionPath().size());
        System.out.println("- מספר מצבים שנבדקו: " + bfs.getNumberOfNodesEvaluated());

        System.out.println("DFS:");
        System.out.println("- מספר צעדים: " + dfsSolution.getSolutionPath().size());
        System.out.println("- מספר מצבים שנבדקו: " + dfs.getNumberOfNodesEvaluated());

        System.out.println("Best First Search:");
        System.out.println("- מספר צעדים: " + bestSolution.getSolutionPath().size());
        System.out.println("- מספר מצבים שנבדקו: " + bestFirstSearch.getNumberOfNodesEvaluated());

        // בדיקה אם Best First Search מוצא פתרון יותר יעיל מבחינת מספר צעדים
        if (bestSolution.getSolutionPath().size() <= bfsSolution.getSolutionPath().size() &&
                bestSolution.getSolutionPath().size() <= dfsSolution.getSolutionPath().size()) {
            System.out.println("\nBest First Search מצא את הפתרון הקצר ביותר או שווה למינימום!");
        } else {
            System.out.println("\nBest First Search לא מצא את הפתרון הקצר ביותר!");
        }
    }

    // פונקציה לבדיקת פתרון
    private static void checkSolution(Solution solution, String algorithmName) {
        ArrayList<AState> path = solution.getSolutionPath();
        System.out.println("פתרון עם " + algorithmName + ":");
        System.out.println("מספר צעדים בפתרון: " + path.size());

        boolean hasDiagonal = false;
        int diagonalCount = 0;

        for (int i = 1; i < path.size(); i++) {
            MazeState current = (MazeState)path.get(i);
            MazeState previous = (MazeState)path.get(i-1);

            Position currentPos = current.getPosition();
            Position prevPos = previous.getPosition();

            boolean isDiagonal = (currentPos.getRowIndex() != prevPos.getRowIndex() &&
                    currentPos.getColumnIndex() != prevPos.getColumnIndex());

            if (isDiagonal) {
                hasDiagonal = true;
                diagonalCount++;
                System.out.println("נמצאה תנועה אלכסונית: " + prevPos + " -> " + currentPos);
            }
        }

        System.out.println("האם הפתרון כולל תנועות אלכסוניות: " + (hasDiagonal ? "כן" : "לא"));
        System.out.println("מספר תנועות אלכסוניות: " + diagonalCount);

        // הצגת הפתרון המלא (אופציונלי - אם הוא ארוך מדי אפשר להוריד או להגביל)
        if (path.size() <= 20) {  // מציג את הפתרון רק אם הוא לא ארוך מדי
            System.out.println("הפתרון המלא:");
            for (int i = 0; i < path.size(); i++) {
                MazeState state = (MazeState)path.get(i);
                System.out.println((i+1) + ". " + state.getPosition());
            }
        }
    }

    // פונקציה להדפסת המבוך
    private static void printMaze(Maze maze) {
        int[][] mazeData = maze.getMaze();
        Position start = maze.getStartPosition();
        Position goal = maze.getGoalPosition();

        for (int i = 0; i < mazeData.length; i++) {
            for (int j = 0; j < mazeData[0].length; j++) {
                if (i == start.getRowIndex() && j == start.getColumnIndex()) {
                    System.out.print("S"); // נקודת התחלה
                } else if (i == goal.getRowIndex() && j == goal.getColumnIndex()) {
                    System.out.print("E"); // נקודת סיום
                } else {
                    System.out.print(mazeData[i][j]);
                }
            }
            System.out.println();
        }
    }
}
