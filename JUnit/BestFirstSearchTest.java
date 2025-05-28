import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BestFirstSearch algorithm using JUnit 5.
 * These tests validate correctness, efficiency, and robustness of the algorithm
 * under various scenarios, including edge cases and performance comparisons.
 *
 * @author Batel
 * @version 1.0
 * @since 2025-04-24
 */
class BestFirstSearchTest {

    private BestFirstSearch bestFirstSearch;
    private SearchableMaze searchableMaze;
    /**
     * Initializes a simple 5x5 maze and the BestFirstSearch instance before each test.
     */
    @BeforeEach
    void setUp() {

        bestFirstSearch = new BestFirstSearch();

        int[][] simpleMap = {
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0}
        };

        Maze simpleMaze = new Maze(simpleMap.length, simpleMap[0].length);

        for (int i = 0; i < simpleMap.length; i++) {
            for (int j = 0; j < simpleMap[0].length; j++) {
                simpleMaze.getMaze()[i][j] = simpleMap[i][j];
            }
        }

        simpleMaze.setStartPosition(new Position(0, 0));
        simpleMaze.setGoalPosition(new Position(4, 4));

        searchableMaze = new SearchableMaze(simpleMaze);
    }
    /**
     * Tests that the algorithm returns the correct name.
     */
    @Test
    void testGetName() {
        assertEquals("Best First Search", bestFirstSearch.getName());
    }
    /**
     * Tests that passing null to solve returns null.
     */
    @Test
    void testSolveWithNullInput() {
        Solution solution = bestFirstSearch.solve(null);
        assertNull(solution);
    }
    /**
     * Tests that solve returns a valid, non-empty solution path.
     */
    @Test
    void testSolveReturnsValidSolution() {

        Solution solution = bestFirstSearch.solve(searchableMaze);
        assertNotNull(solution);

        ArrayList<AState> path = solution.getSolutionPath();
        assertNotNull(path);
        assertFalse(path.isEmpty());
        assertEquals(searchableMaze.getStartState().getState(), path.get(0).getState());
        assertEquals(searchableMaze.getGoalState().getState(), path.get(path.size() - 1).getState());
    }
    /**
     * Tests that the number of nodes evaluated is greater than zero after solving.
     */
    @Test
    void testNumberOfNodesEvaluated() {
        bestFirstSearch.solve(searchableMaze);
        assertTrue(bestFirstSearch.getNumberOfNodesEvaluated() > 0);
    }

    /**
     * Tests that each state in the solution path is properly linked to its predecessor.
     */
    @Test
    void testSolutionPathContinuity() {
        Solution solution = bestFirstSearch.solve(searchableMaze);
        ArrayList<AState> path = solution.getSolutionPath();

        for (int i = 1; i < path.size(); i++) {
            AState current = path.get(i);
            AState previous = path.get(i - 1);
            assertEquals(previous, current.getCameFrom());
        }
    }
    /**
     * Tests that the total cost of the solution is positive.
     */
    @Test
    void testSolutionCost() {

        Solution solution = bestFirstSearch.solve(searchableMaze);
        ArrayList<AState> path = solution.getSolutionPath();

        if (!path.isEmpty()) {
            AState lastState = path.get(path.size() - 1);
            assertTrue(lastState.getCost() > 0);
        }
    }
    /**
     * Compares efficiency: ensures BestFirstSearch evaluates fewer or equal nodes than BFS and DFS.
     */
    @Test
    void testBestFirstSearchIsMoreEfficientThanBFSAndDFS() throws Exception {
        Maze maze = new Maze(30, 30);
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                maze.setCell(i, j, 0);
            }
        }
        maze.setStartPosition(new Position(0, 0));
        maze.setGoalPosition(new Position(29, 29));
        SearchableMaze searchable = new SearchableMaze(maze);

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


        assertTrue(bestNodes <= bfsNodes, "BestFirstSearch should evaluate fewer or equal nodes than BFS");
        assertTrue(bestNodes <= dfsNodes, "BestFirstSearch should evaluate fewer or equal nodes than DFS");
    }
    /**
     * Tests efficiency on a 20x20 open maze.
     */
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
    /**
     * Tests solving a large 100x100 open maze.
     */
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
    /**
     * Tests solving a vertical maze of size 5x1.
     */
    @Test
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
    /**
     * Tests solving a horizontal maze of size 1x5.
     */
    void testMaze_1x5() throws Exception {
        Maze maze = new Maze(1, 5);
        for (int i = 0; i < 5; i++) maze.setCell(0, i, 0);
        maze.setStartPosition(new Position(0, 0));
        maze.setGoalPosition(new Position(0, 4));
        SearchableMaze searchable = new SearchableMaze(maze);

        Solution solution = bestFirstSearch.solve(searchable);
        assertEquals(5, solution.getSolutionPath().size());
    }
    /**
     * Tests solving a trivial 1x1 maze.
     */
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
    /**
     * Tests efficiency of BestFirstSearch on random mazes with 20% walls.
     */
    @Test
    void testBestFirstSearchEfficiencyOnRandomMazes() throws Exception {
        for (int t = 0; t < 5; t++) {
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
    /**
     * Tests solving an empty 5x5 maze and validates the solution path.
     */
    @Test
    void testSolveEmptyMaze() {
        int rows = 5;
        int cols = 5;
        int[][] emptyMaze = new int[rows][cols];

        Position start = new Position(0, 0);
        Position goal = new Position(4, 4);

        Maze maze = new Maze(emptyMaze, start, goal);
        SearchableMaze searchable = new SearchableMaze(maze);
        BestFirstSearch bfs = new BestFirstSearch();

        Solution solution = bfs.solve(searchable);

        assertNotNull(solution);
        ArrayList<AState> path = solution.getSolutionPath();
        assertFalse(path.isEmpty());

        assertEquals(searchable.getStartState().getState(), path.get(0).getState());
        assertEquals(searchable.getGoalState().getState(), path.get(path.size() - 1).getState());

        assertTrue(path.size() <= rows + cols);
    }



}