package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import java.util.HashMap;
import java.util.LinkedList;

public class Solver {
//    int[] distTo;
    HashMap<WorldState, WorldState> edgeTo;
    LinkedList<WorldState> solution;
    HashMap<WorldState, Integer> distTo;
    int enqueueNum = 0; // to validate the critical optimization
    private class SearchNode implements Comparable<SearchNode> {
        WorldState state;
        int priority;
        public SearchNode(WorldState state, int curSteps) {
            priority = state.estimatedDistanceToGoal() + curSteps;
            this.state = state;
        }

        public Iterable<WorldState> getNeibgbors() {
            return state.neighbors();
        }

        public boolean isGoal() {
            return state.isGoal();
        }

        @Override
        public int compareTo(SearchNode o) {
            return this.priority - o.priority;
        }

    }

    public Solver(WorldState initial) {
        edgeTo = new HashMap<>();
        distTo = new HashMap<>();
        MinPQ<SearchNode> pq = new MinPQ<>();
        solution = new LinkedList<>();
        SearchNode nodeToSearch;
        distTo.put(initial, 0);
        pq.insert(new SearchNode(initial, distTo.get(initial)));
        while (!pq.isEmpty()) {
            nodeToSearch = pq.delMin();
            for (WorldState neighbor: nodeToSearch.getNeibgbors()) {
                // critical optimization, avoid duplicate exploration
                if (neighbor.equals(edgeTo.get(nodeToSearch))) {
                    continue;
                }
                // relaxation, Note that it's not a "real" relaxation, because
                // changePriority is not supported in MinPQ.
                if (edgeTo.containsKey(neighbor)) {
                    // find a better solution
                    if (distTo.get(nodeToSearch.state) + 1 < distTo.get(neighbor)) {
                        SearchNode nextNode = new SearchNode(neighbor, distTo.get(nodeToSearch.state));
                        edgeTo.put(nextNode.state, nodeToSearch.state);
                        distTo.put(nextNode.state, distTo.get(nodeToSearch.state) + 1);
                        pq.insert(nextNode);
                    }
                    continue;
                }
                SearchNode nextNode = new SearchNode(neighbor, distTo.get(nodeToSearch.state));
                pq.insert(nextNode);
                enqueueNum++;
                edgeTo.put(nextNode.state, nodeToSearch.state);
                distTo.put(nextNode.state, distTo.get(nodeToSearch.state) + 1);
                if (nextNode.isGoal()) {
                    WorldState intermediateState = edgeTo.get(nextNode.state);
                    solution.addFirst(nextNode.state);
                    while (intermediateState != initial) {
                        solution.addFirst(intermediateState);
                        intermediateState = edgeTo.get(intermediateState);
                    }
                    // add the first state
                    solution.addFirst(intermediateState);
                    return;
                }
            }
        }

    }


    public int moves() {
        // the number of moves is length - 1
        return solution.size() - 1;
    }

    public Iterable<WorldState> solution() {
        return solution;
    }
}
