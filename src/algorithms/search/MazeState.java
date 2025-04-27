
package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState {
    private Position position;

    // קונסטרקטור רגיל
    public MazeState(Position position) {
        super(position.toString());
        this.position = position;
    }

    // קונסטרקטור עם עלות
    public MazeState(Position position, double cost) {
        super(position.toString(), cost);
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    // פונקציות חדשות ל-heurstic
    public void setHeuristic(double heuristic) {
        this.heuristic = heuristic;
    }

    public double getHeuristic() {
        return heuristic;
    }

    @Override
    public String toString() {
        return position.toString();
    }
}
