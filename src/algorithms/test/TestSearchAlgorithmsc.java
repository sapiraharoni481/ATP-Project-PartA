package algorithms.test;
import algorithms.mazeGenerators.*;
import algorithms.search.*;
public class TestSearchAlgorithmsc {


    public static void main(String[] args) {
        IMazeGenerator generator = new MyMazeGenerator();
        Maze maze = generator.generate(20, 20);
        ISearchable searchableMaze = new SearchableMaze(maze);

        testSearcher(new BreadthFirstSearch(), searchableMaze);
        testSearcher(new BestFirstSearch(), searchableMaze);
        testSearcher(new DepthFirstSearch(), searchableMaze);
    }

    private static void testSearcher(ISearchingAlgorithm searcher, ISearchable searchable) {
        System.out.println("Running: " + searcher.getName());
        Solution solution = searcher.solve(searchable);

        if (solution == null || solution.getSolutionPath().isEmpty()) {
            System.out.println("❌ No solution found.");
            return;
        }

        System.out.println("✅ Solution found!");
        System.out.println("Steps in path: " + solution.getSolutionPath().size());
        System.out.println("Nodes evaluated: " + searcher.getNumberOfNodesEvaluated());

        AState goal = solution.getSolutionPath().get(solution.getSolutionPath().size() - 1);
        System.out.println("Total path cost: " + goal.getCost());
        System.out.println("Path ends at: " + goal);
        System.out.println("--------------\n");
    }
}
