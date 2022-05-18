/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] sites;  // true represent the site is open
    private final WeightedQuickUnionUF uf;  // store connected relationship
    private final WeightedQuickUnionUF ufOnlyTop;  // same as above but only contain virtual top site
    private final int topID, bottomID;  // id of virtual sites
    private int count;  // number of open sites
    private final int size;  // size of given grid

    /** Creates n-by-n grid, with all sites initially blocked. */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N must be larger than zero!");
        }

        sites = new boolean[n * n];  // index from 0 to (n*n-1)
        uf = new WeightedQuickUnionUF(n * n + 2);  // n*n and (n*n+1) represent top and bottom virtual site
        ufOnlyTop = new WeightedQuickUnionUF(n * n + 1);
        topID = n * n;
        bottomID = n * n + 1;
        count = 0;  // all sites are not open at start
        size = n;
    }

    /** Opens the site (row, col) if it is not open already. */
    public void open(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("Outside range!");
        }

        // if given site is open, just return
        if (isOpen(row, col)) {
            return;
        }

        // open site (row, col)
        int id = twoDimToOneDim(row, col);  // 1d index of given site
        sites[id] = true;
        // connect the given with surround open sites
        connectOpenNeighbors(row, col);
        // update number od open sites
        count += 1;
    }

    /** Is the site (row, col) open? */
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("Outside range!");
        }

        int id = twoDimToOneDim(row, col);
        return isOpen(id);
    }

    /** Is the site (row, col) full? */
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("Outside range!");
        }

        // Return true if site (row, col) connects with virtual top site.
        // But this will false when the system is percolated.
        // Thin about a percolated grid and one site which connect with
        // a open bottom site. Would this site should be full?
        int id = twoDimToOneDim(row, col);
        return ufOnlyTop.find(id) == ufOnlyTop.find(topID);  // use ufOnlyTop instead
        // unnecessary to include isOpen()? Why?
        // Ans. not open site is disjoint.
    }

    /** Returns the number of open sites. */
    public int numberOfOpenSites() {
        return count;
    }

    /** Does the system percolate? */
    public boolean percolates() {
        // Return true when virtual top and bottom sites are connected.
        return uf.find(topID) == uf.find(bottomID);
    }

    /** Convert 2d index to 1d index which 2d and 1d index start at 1 and 0. */
    private int twoDimToOneDim(int row, int col) {
        return (row - 1) * size + (col - 1);
    }

    /** Connect neighboring (left, right, up, down) open sites of given site. */
    private void connectOpenNeighbors(int row, int col) {
        int id = twoDimToOneDim(row, col);  // 1d index

        // connect left when site is not at leftmost col and left site is open
        int left = id - 1;
        if (id % size != 0 && isOpen(left)) {
            uf.union(id, left);
            ufOnlyTop.union(id, left);
        }

        // connect right when site is not at rightmost col and right site is open
        int right = id + 1;
        if ((id + 1) % size != 0 && isOpen(right)) {
            uf.union(id, right);
            ufOnlyTop.union(id, right);
        }

        // connect up when site is not at upmost row and up site is open
        int up = id - size;
        if (id - size >= 0 && isOpen(up)) {
            uf.union(id, up);
            ufOnlyTop.union(id, up);
        }

        // connect down when site is not at downmost row and down site is open
        int down = id + size;
        if (id + size < sites.length && isOpen(down)) {
            uf.union(id, down);
            ufOnlyTop.union(id, down);
        }

        // connect virtual top site
        if (id - size < 0) {
            uf.union(id, topID);
            ufOnlyTop.union(id, topID);
        }
        // connect virtual bottom site
        if (id + size >= sites.length) {
            uf.union(id, bottomID);
        }
        // Note: don't use 'else if' above. Think 1-by-1 grid.
    }

    /** 1d version of isOpen() .*/
    private boolean isOpen(int id) {
        return sites[id];
    }

    /** Test client  (optional). */
    public static void main(String[] args) {
        In in = new In(args[0]);  // input file
        int n = in.readInt();
        Percolation sys = new Percolation(n);  // create a n-by-n grid

        while (!in.isEmpty()) {
            int x = in.readInt();
            int y = in.readInt();
            sys.open(x, y);
        }

        StdOut.println("The system is percolated? " + sys.percolates());
        StdOut.println("THe number of open sites: " + sys.numberOfOpenSites());
        // 2d index start from 1
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                // print all open sites
                if (sys.isOpen(i, j)) {
                    StdOut.printf("%4d %4d", i, j);
                }

                if (j < n - 1) StdOut.print("   ");
                else StdOut.println();
            }
        }
    }
}
