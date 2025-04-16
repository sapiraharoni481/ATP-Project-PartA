package algorithms.search;
import algorithms.mazeGenerators.Position;

public class MazeState extends AState {
    private Position position;
    //
    public MazeState(Position position) {
        super(position.toString());
        this.position = position;
    }

    public MazeState(Position position, double cost) {
        super(position.toString(), cost);
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return position.toString();
    }
}