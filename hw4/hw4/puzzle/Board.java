package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState{
    static final int BLANK = 0;
    int[][] tiles;
    int[][] goal;
    public Board(int[][] tiles) {
        int N = tiles.length;
        this.tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
        goal = new int[N][N];
        for (int i = 0; i < N * N - 1; i++) {
            goal[i / N][i % N] = i + 1;
        }
    }

    public int tileAt(int i, int j) {
        return tiles[i][j];
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public int size() {
        return tiles.length;
    }

    public int hamming() {
        int mismatchCount = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tiles[i][j] != goal[i][j]) {
                    if (tiles[i][j] != BLANK) {
                        mismatchCount++;
                    }
                }
            }
        }
        return mismatchCount;
    }

    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tiles[i][j] == BLANK) {
                    continue;
                }
                for (int ii = 0; ii < size(); ii++) {
                    for (int jj = 0; jj < size(); jj++) {
                        if (tiles[i][j] == goal[ii][jj]) {
                            distance += Math.abs(ii - j) + Math.abs(jj - j);
                        }
                    }
                }
            }
        }
        return distance;
    }

    public boolean equals(Board y) {
        return (this.tiles).equals(y.tiles);
    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    /**
     * This is borrowed from http://joshh.ug/neighbors.html
     * @return neighbors of this board
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }
}
