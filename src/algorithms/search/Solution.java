package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
//
public class Solution implements Serializable {
    private ArrayList<AState> solutionPath;

    public Solution() {
        this.solutionPath = new ArrayList<>();
    }
    public ArrayList<AState> getSolutionPath() {
        return solutionPath;
    }
    public void setSolutionPath(ArrayList<AState> solutionPath) {
        this.solutionPath = solutionPath;
    }
}