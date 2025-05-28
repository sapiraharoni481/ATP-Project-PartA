package algorithms.mazeGenerators;

import java.io.Serializable;
/**
 * Represents a position (cell) in a 2D maze.
 * Stores the row and column indices of the position.
 * Used for navigating and identifying locations in a maze.
 *
 * @author Sapir
 * @version 1.0
 * @since 2025-04-13
 */
public class Position implements Serializable  {
    private int row;
    private int column;
    /**
     * Constructs a Position object with the specified row and column indices.
     *
     * @param row The row index of the position.
     * @param column The column index of the position.
     */
    public Position(int row,int column){
        this.row= row;
        this.column=column;
    }
    /**
     * Returns the row index of this position.
     *
     * @return The row index.
     */
    public int getRowIndex(){
        return row;
    }
    /**
     * Returns the column index of this position.
     *
     * @return The column index.
     */
    public int getColumnIndex(){
        return column;
    }
    /**
     * Returns a string representation of the position in the format: [row,column].
     *
     * @return A string representing the position.
     */
    @Override
    public String toString(){
        return "[" + row + "," + column + "]";
    }
}