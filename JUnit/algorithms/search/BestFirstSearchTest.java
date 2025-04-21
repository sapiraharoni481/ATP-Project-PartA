package algorithms.search;


import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    private BestFirstSearch bestFirstSearch;
    private SearchableMaze searchableMaze;

    @BeforeEach
    void setUp() {
        // מאתחל את האלגוריתם לפני כל בדיקה
        bestFirstSearch = new BestFirstSearch();

        // יוצר מבוך פשוט לבדיקה
        int[][] simpleMap = {
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0}
        };

        Maze simpleMaze = new Maze(simpleMap.length, simpleMap[0].length);

        // העתקת נתוני המבוך
        for (int i = 0; i < simpleMap.length; i++) {
            for (int j = 0; j < simpleMap[0].length; j++) {
                simpleMaze.getMaze()[i][j] = simpleMap[i][j];
            }
        }

        // קביעת נקודות התחלה וסיום
        simpleMaze.setStartPosition(new Position(0, 0));
        simpleMaze.setGoalPosition(new Position(4, 4));

        searchableMaze = new SearchableMaze(simpleMaze);
    }

    @Test
    void testGetName() {
        // בדיקה שהשם של האלגוריתם נכון
        assertEquals("Best First Search", bestFirstSearch.getName());
    }

    @Test
    void testSolveWithNullInput() {
        // בדיקה שהאלגוריתם מחזיר null כאשר מעבירים לו null
        Solution solution = bestFirstSearch.solve(null);
        assertNull(solution);
    }

    @Test
    void testSolveReturnsValidSolution() {
        // בדיקה שהאלגוריתם מחזיר פתרון תקף
        Solution solution = bestFirstSearch.solve(searchableMaze);

        // בדיקה שהפתרון לא null
        assertNotNull(solution);

        // בדיקה שיש נתיב בפתרון
        ArrayList<AState> path = solution.getSolutionPath();
        assertNotNull(path);
        assertFalse(path.isEmpty());

        // בדיקה שהנתיב מתחיל בנקודת ההתחלה ומסתיים בנקודת הסיום
        assertEquals(searchableMaze.getStartState().getState(), path.get(0).getState());
        assertEquals(searchableMaze.getGoalState().getState(), path.get(path.size() - 1).getState());
    }

    @Test
    void testNumberOfNodesEvaluated() {
        // בדיקה שמספר הצמתים שנבדקו גדול מ-0
        bestFirstSearch.solve(searchableMaze);
        assertTrue(bestFirstSearch.getNumberOfNodesEvaluated() > 0);
    }

    @Test
    void testSolutionPathContinuity() {
        // בדיקה שהנתיב רציף (כל מצב מקושר למצב הקודם)
        Solution solution = bestFirstSearch.solve(searchableMaze);
        ArrayList<AState> path = solution.getSolutionPath();

        for (int i = 1; i < path.size(); i++) {
            AState current = path.get(i);
            AState previous = path.get(i - 1);
            // בדיקה שהמצב הנוכחי הגיע מהמצב הקודם
            assertEquals(previous, current.getCameFrom());
        }
    }
    @Test
    void testSolutionCost() {
        // בדיקה שהעלות של הפתרון חיובית
        Solution solution = bestFirstSearch.solve(searchableMaze);
        ArrayList<AState> path = solution.getSolutionPath();

        if (!path.isEmpty()) {
            AState lastState = path.get(path.size() - 1);
            assertTrue(lastState.getCost() > 0);
        }
    }
    @Test
    void testBestFirstSearchIsMoreEfficientThanBFSAndDFS() throws Exception {
        // יצירת מבוך פתוח בגודל 30x30
        Maze maze = new Maze(30, 30);
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                maze.setCell(i, j, 0);
            }
        }
        maze.setStartPosition(new Position(0, 0));
        maze.setGoalPosition(new Position(29, 29));
        SearchableMaze searchable = new SearchableMaze(maze);

        // הרצת כל האלגוריתמים
        BestFirstSearch best = new BestFirstSearch();
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        DepthFirstSearch dfs = new DepthFirstSearch();

        best.solve(searchable);
        bfs.solve(searchable);
        dfs.solve(searchable);

        int bestNodes = best.getNumberOfNodesEvaluated();
        int bfsNodes = bfs.getNumberOfNodesEvaluated();
        int dfsNodes = dfs.getNumberOfNodesEvaluated();

        System.out.printf("Best: %d, BFS: %d, DFS: %d\n", bestNodes, bfsNodes, dfsNodes);

        // בדיקה ש־Best פותח פחות או שווה לשניהם
        assertTrue(bestNodes <= bfsNodes, "BestFirstSearch should evaluate fewer or equal nodes than BFS");
        assertTrue(bestNodes <= dfsNodes, "BestFirstSearch should evaluate fewer or equal nodes than DFS");
    }
    @Test
    void testEfficiencyOnOpenMaze() throws Exception {
        Maze maze = new Maze(20, 20);
        for (int i = 0; i < 20; i++)
            for (int j = 0; j < 20; j++) maze.setCell(i, j, 0);
        maze.setStartPosition(new Position(0, 0));
        maze.setGoalPosition(new Position(19, 19));

        SearchableMaze searchable = new SearchableMaze(maze);
        bestFirstSearch.solve(searchable);

        assertTrue(bestFirstSearch.getNumberOfNodesEvaluated() < 400); // ציפייה לא לפתוח את כל המבוך
    }
    @Test
    void testLargeOpenMaze() throws Exception {
        int size = 100;
        Maze maze = new Maze(size, size);
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                maze.setCell(i, j, 0);

        maze.setStartPosition(new Position(0, 0));
        maze.setGoalPosition(new Position(size - 1, size - 1));
        SearchableMaze searchable = new SearchableMaze(maze);

        Solution solution = bestFirstSearch.solve(searchable);
        assertNotNull(solution);
        assertFalse(solution.getSolutionPath().isEmpty());
    }
    @Test
    // מבוך בגודל Nx1 (טור אחד)
    void testMaze_5x1() throws Exception {
        Maze maze = new Maze(5, 1);
        for (int i = 0; i < 5; i++) maze.setCell(i, 0, 0);
        maze.setStartPosition(new Position(0, 0));
        maze.setGoalPosition(new Position(4, 0));
        SearchableMaze searchable = new SearchableMaze(maze);

        Solution solution = bestFirstSearch.solve(searchable);
        assertEquals(5, solution.getSolutionPath().size());
    }
    @Test
    // מבוך בגודל 1xN (שורה אחת)
    void testMaze_1x5() throws Exception {
        Maze maze = new Maze(1, 5);
        for (int i = 0; i < 5; i++) maze.setCell(0, i, 0);
        maze.setStartPosition(new Position(0, 0));
        maze.setGoalPosition(new Position(0, 4));
        SearchableMaze searchable = new SearchableMaze(maze);

        Solution solution = bestFirstSearch.solve(searchable);
        assertEquals(5, solution.getSolutionPath().size());
    }
    @Test
    void testTrivialMaze_1x1() throws Exception {
        Maze maze = new Maze(1, 1);
        maze.setStartPosition(new Position(0, 0));
        maze.setGoalPosition(new Position(0, 0));
        SearchableMaze searchable = new SearchableMaze(maze);

        Solution solution = bestFirstSearch.solve(searchable);
        assertNotNull(solution);
        assertEquals(1, solution.getSolutionPath().size());
    }
    @Test
    void testBestFirstSearchEfficiencyOnRandomMazes() throws Exception {
        for (int t = 0; t < 5; t++) { // נריץ על 5 מבוכים שונים
            Maze maze = new Maze(20, 20);
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    maze.setCell(i, j, (Math.random() < 0.2) ? 1 : 0); // 20% קירות
                }
            }
            maze.setStartPosition(new Position(0, 0));
            maze.setGoalPosition(new Position(19, 19));
            maze.setCell(0, 0, 0); // הבטחת פתיחה
            maze.setCell(19, 19, 0);

            SearchableMaze searchable = new SearchableMaze(maze);
            BestFirstSearch best = new BestFirstSearch();
            BreadthFirstSearch bfs = new BreadthFirstSearch();

            best.solve(searchable);
            bfs.solve(searchable);

            int bestNodes = best.getNumberOfNodesEvaluated();
            int bfsNodes = bfs.getNumberOfNodesEvaluated();

            assertTrue(bestNodes <= bfsNodes, "BestFirstSearch opened more nodes than BFS in test " + t);
        }
    }
    @Test
    void testSolveEmptyMaze() {
        // יצירת מבוך ריק 5x5
        int rows = 5;
        int cols = 5;
        int[][] emptyMaze = new int[rows][cols]; // כל התאים הם 0 כברירת מחדל

        // נקודות התחלה וסיום
        Position start = new Position(0, 0);
        Position goal = new Position(4, 4);

        Maze maze = new Maze(emptyMaze, start, goal);
        SearchableMaze searchable = new SearchableMaze(maze);
        BestFirstSearch bfs = new BestFirstSearch();

        Solution solution = bfs.solve(searchable);

        // הפתרון לא אמור להיות ריק
        assertNotNull(solution);
        ArrayList<AState> path = solution.getSolutionPath();
        assertFalse(path.isEmpty());

        // התחלה וסיום
        assertEquals(searchable.getStartState().getState(), path.get(0).getState());
        assertEquals(searchable.getGoalState().getState(), path.get(path.size() - 1).getState());

        // לוודא שהדרך הכי קצרה אכן נמצאה (צריכה להיות באורך 9 צעדים בדיאגונל במקרה הזה)
        assertTrue(path.size() <= rows + cols);  // זהירות - לא תמיד יהיה בדיוק rows+cols-1 אם מותר אלכסונים
    }














}