/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final Stack<Board> sol; // stack for correct solution order

    // search node in game tree
    private static class Node implements Comparable<Node> {
        Board board;    // board contained
        int g;          // actual moves
        int h;          // heuristic
        Node parent;    // link to parent node

        public Node(Board board, int g, int h, Node parent) {
            this.board  = board;
            this.g      = g;
            this.h      = h;
            this.parent = parent;
        }

        public int compareTo(Node that) {
            // for TreeSet
            return (this.g + this.h) - (that.g + that.h);
        }
    }

    /** find a solution to the initial board (using the A* algorithm) */
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("initial can not be null!");
        }

        sol = new Stack<>();
        Node goalNode; // goal node in game tree

        // A* algorithm
        MinPQ<Node> pq = new MinPQ<>();
        pq.insert(new Node(initial, 0, initial.manhattan(), null));

        // twin pq
        MinPQ<Node> twinPQ = new MinPQ<>();
        twinPQ.insert(new Node(initial.twin(), 0, initial.twin().manhattan(), null));

        while (true) {
            Node node = pq.delMin();
            if (node.board.isGoal()) { // address goal
                goalNode = node;
                break;
            }
            for (Board board: node.board.neighbors()) {
                if (node.parent != null && board.equals(node.parent.board)) continue; // optimization
                pq.insert(new Node(board, node.g + 1, board.manhattan(), node));
            }

            // twin pq
            node = twinPQ.delMin();
            if (node.board.isGoal()) { // unsolvable since twin address goal
                goalNode = null;
                break;
            }
            for (Board board: node.board.neighbors()) {
                if (node.parent != null && board.equals(node.parent.board)) continue; // optimization
                twinPQ.insert(new Node(board, node.g + 1, board.manhattan(), node));
            }
        }

        if (goalNode == null) { // unsolvable
            return;
        }
        // create solution
        Node node = goalNode;
        while (node != null) {
            sol.push(node.board);
            node = node.parent;
        }
    }

    /** is the initial board solvable? (see below) */
    public boolean isSolvable() {
        return !sol.isEmpty();
    }

    /** min number of moves to solve initial board; -1 if unsolvable */
    public int moves() {
        // sol.size() is 0 if unsolvable
        return sol.size() - 1;
    }

    /** sequence of boards in the shortest solution; null if unsolvable */
    public Iterable<Board> solution() {
        if (isSolvable()) return sol;
        else              return null;
    }

    /** test client (see below) */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
