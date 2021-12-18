package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    boolean cycleFound = false;

    public MazeCycles(Maze m) {
        super(m);
        distTo[0] = 0;
        edgeTo[0] = 0;
    }

    private void dfs(int v) {
        marked[v] = true;
        announce();



        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                announce();
                distTo[w] = distTo[v] + 1;
                dfs(w);
                if (cycleFound) {
                    return;
                }
            } else if (edgeTo[v] != w) {
                cycleFound = true;
                return;
            }
        }
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        dfs(0);
    }

    // Helper methods go here
}

