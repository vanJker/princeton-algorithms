/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int[][] tiles;  // tiles of puzzle
    private final int n;          // dimension of puzzle
    private int blankRow;
    private int blankCol;   // index of blank

    /**
     * create a board from an n-by-n array of tiles,
     * where tiles[row][col] = tile at (row, col)
     */
    public Board(int[][] tiles) {
        n = tiles.length; // set dimension of puzzle
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) { // set index of blank
                    blankRow = i;
                    blankCol = j;
                }
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    /** string representation of this board */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    /** board dimension n */
    public int dimension() {
        return n;
    }

    /** number of tiles out of place */
    public int hamming() {
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n-1 && j == n-1) { // blank is not a tile
                    continue;
                }
                if (tiles[i][j] != goalTile(i, j)) {
                    result += 1;
                }
            }
        }
        return result;
    }

    /** goal tile at (row, col) */
    private int goalTile(int row, int col) {
        return row*n + col + 1;
    }

    /** sum of Manhattan distances between tiles and goal */
    public int manhattan() {
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) { // blank is not a tile
                    continue;
                }
                result += manDis(tiles[i][j], i, j);
            }
        }
        return result;
    }

    /** manhattan distance of tile */
    private int manDis(int tile, int row, int col) {
        int v = Math.abs(row - goalRow(tile));
        int h = Math.abs(col - goalCol(tile));
        return v + h;
    }

    /** goal row of tile */
    private int goalRow(int tile) {
        return (tile - 1) / n;
    }

    /** goal column of tile */
    private int goalCol(int tile) {
        return (tile - 1) % n;
    }

    /** is this board the goal board? */
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n-1 && j == n-1) continue; // skip blank
                if (tiles[i][j] != goalTile(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** does this board equal y? */
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y; // cast type
        if (this.n != that.n) { // check dimension
            return false;
        }
        for (int i = 0; i < n; i++) { // check every tile
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /** all neighboring boards */
    public Iterable<Board> neighbors() {
        Queue<Board> queue = new Queue<>();
        if (blankRow != 0) { // not at top row
            int[][] arr = copyArray(tiles);
            exchange(arr, blankRow, blankCol, blankRow-1, blankCol);
            queue.enqueue(new Board(arr));
        }
        if (blankRow != n-1) { // not at bottom row
            int[][] arr = copyArray(tiles);
            exchange(arr, blankRow, blankCol, blankRow+1, blankCol);
            queue.enqueue(new Board(arr));
        }
        if (blankCol != 0) { // not at left column
            int[][] arr = copyArray(tiles);
            exchange(arr, blankRow, blankCol, blankRow, blankCol-1);
            queue.enqueue(new Board(arr));
        }
        if (blankCol != n-1) { // not at right column
            int[][] arr = copyArray(tiles);
            exchange(arr, blankRow, blankCol, blankRow, blankCol+1);
            queue.enqueue(new Board(arr));
        }
        return queue;
    }

    /** deep copy 2D array (assume square) */
    private static int[][] copyArray(int[][] arr) {
        int n = arr.length;
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = arr[i][j];
            }
        }
        return result;
    }

    /** exchange two items in a 2D array (assume square) */
    private static void exchange(int[][] arr, int r1, int c1, int r2, int c2) {
        int temp = arr[r1][c1];
        arr[r1][c1] = arr[r2][c2];
        arr[r2][c2] = temp;
    }

    /** a board that is obtained by exchangeing any pair of tiles */
    public Board twin() {
        // must return same twin board every time
        int[][] arr = copyArray(tiles);
        int r1 = blankRow;
        int c1 = (blankCol + 1) % n;
        int r2 = (blankRow + 1) % n;
        int c2 = blankCol;
        exchange(arr, r1, c1, r2, c2);
        return new Board(arr);
    }

    /** unit testing (not graded) */
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt(); // read dimension of puzzle
        int[][] tiles = new int[n][n];
        int i = 0, j = 0; // start index

        while (!in.isEmpty()) {
            tiles[i][j] = in.readInt();
            j = (j + 1) % n;
            i = (j == 0) ? (i+1) : i;
        }
        Board board = new Board(tiles);

        StdOut.println("puzzle: ");
        StdOut.println(board);
        StdOut.println("hamming: " + board.hamming());
        StdOut.println("manhattan: "+ board.manhattan());
        StdOut.println("neighbors: ");
        for (Board neighbor: board.neighbors()) {
            StdOut.println(neighbor);
        }
        StdOut.println("twin: ");
        StdOut.println(board.twin());
    }
}
