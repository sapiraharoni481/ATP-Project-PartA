//package algorithms.search;
//
//
//import algorithms.mazeGenerators.Maze;
//import algorithms.mazeGenerators.Position;
//import algorithms.search.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class BestFirstSearchTest {
//
//    private BestFirstSearch bestFirstSearch;
//    private SearchableMaze searchableMaze;
//
//    @BeforeEach
//    void setUp() {
//        // מאתחל את האלגוריתם לפני כל בדיקה
//        bestFirstSearch = new BestFirstSearch();
//
//        // יוצר מבוך פשוט לבדיקה
//        int[][] simpleMap = {
//                {0, 0, 0, 0, 0},
//                {0, 1, 1, 1, 0},
//                {0, 1, 0, 1, 0},
//                {0, 1, 0, 1, 0},
//                {0, 0, 0, 0, 0}
//        };
//
//        Maze simpleMaze = new Maze(simpleMap.length, simpleMap[0].length);
//
//        // העתקת נתוני המבוך
//        for (int i = 0; i < simpleMap.length; i++) {
//            for (int j = 0; j < simpleMap[0].length; j++) {
//                simpleMaze.getMaze()[i][j] = simpleMap[i][j];
//            }
//        }
//
//        // קביעת נקודות התחלה וסיום
//        simpleMaze.setStartPosition(new Position(0, 0));
//        simpleMaze.setGoalPosition(new Position(4, 4));
//
//        searchableMaze = new SearchableMaze(simpleMaze);
//    }
//
//    @Test
//    void testGetName() {
//        // בדיקה שהשם של האלגוריתם נכון
//        assertEquals("Best First Search", bestFirstSearch.getName());
//    }
//
//    @Test
//    void testSolveWithNullInput() {
//        // בדיקה שהאלגוריתם מחזיר null כאשר מעבירים לו null
//        Solution solution = bestFirstSearch.solve(null);
//        assertNull(solution);
//    }
//
//    @Test
//    void testSolveReturnsValidSolution() {
//        // בדיקה שהאלגוריתם מחזיר פתרון תקף
//        Solution solution = bestFirstSearch.solve(searchableMaze);
//
//        // בדיקה שהפתרון לא null
//        assertNotNull(solution);
//
//        // בדיקה שיש נתיב בפתרון
//        ArrayList<AState> path = solution.getSolutionPath();
//        assertNotNull(path);
//        assertFalse(path.isEmpty());
//
//        // בדיקה שהנתיב מתחיל בנקודת ההתחלה ומסתיים בנקודת הסיום
//        assertEquals(searchableMaze.getStartState().getState(), path.get(0).getState());
//        assertEquals(searchableMaze.getGoalState().getState(), path.get(path.size() - 1).getState());
//    }
//
//    @Test
//    void testNumberOfNodesEvaluated() {
//        // בדיקה שמספר הצמתים שנבדקו גדול מ-0
//        bestFirstSearch.solve(searchableMaze);
//        assertTrue(bestFirstSearch.getNumberOfNodesEvaluated() > 0);
//    }
//
//    @Test
//    void testSolutionPathContinuity() {
//        // בדיקה שהנתיב רציף (כל מצב מקושר למצב הקודם)
//        Solution solution = bestFirstSearch.solve(searchableMaze);
//        ArrayList<AState> path = solution.getSolutionPath();
//
//        for (int i = 1; i < path.size(); i++) {
//            AState current = path.get(i);
//            AState previous = path.get(i - 1);
//            // בדיקה שהמצב הנוכחי הגיע מהמצב הקודם
//            assertEquals(previous, current.getCameFrom());
//        }
//    }
//    @Test
//    void testSolutionCost() {
//        // בדיקה שהעלות של הפתרון חיובית
//        Solution solution = bestFirstSearch.solve(searchableMaze);
//        ArrayList<AState> path = solution.getSolutionPath();
//
//        if (!path.isEmpty()) {
//            AState lastState = path.get(path.size() - 1);
//            assertTrue(lastState.getCost() > 0);
//        }
//    }
//}