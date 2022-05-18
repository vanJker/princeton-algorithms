/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int size;  // size of grid
    private final double[] x;  // x[i] be the fraction of open sites in experiment i
    private final double mean;  // mean value
    private final double stddev;  // standard deviation value

    /** Perform independent trials on an n-by-n grid. */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Arguments must be larger than zero!");
        }

        size = n;
        x = new double[trials];
        for (int i = 0; i < trials; i++) {
            // independent experiment by random numbers
            Percolation perc = new Percolation(n);
            experiment(perc);
            x[i] = 1.0 * perc.numberOfOpenSites() / (n * n);
        }
        mean = StdStats.mean(x);
        stddev = StdStats.stddev(x);
    }

    /** Independent experiment by random numbers. */
    private void experiment(Percolation perc) {
        int row, col;
        while (!perc.percolates()) {
            row = StdRandom.uniform(1, size + 1);
            col = StdRandom.uniform(1, size + 1);
            perc.open(row, col);
        }
    }

    /** Sample mean of percolation threshold. */
    public double mean() {
        return mean;
    }

    /** Sample standard deviation of percolation threshold. */
    public double stddev() {
        return stddev;
    }

    /** Low endpoint of 95% confidence interval. */
    public double confidenceLo() {
        return mean - 1.96*stddev / Math.sqrt(x.length);
    }

    /** High endpoint of 95% confidence interval. */
    public double confidenceHi() {
        return  mean + 1.96*stddev / Math.sqrt(x.length);
    }

    /** Test client (see below). */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percs = new PercolationStats(n, trials);
        StdOut.println("mean =                    " + percs.mean());
        StdOut.println("stddev =                  " + percs.stddev());
        StdOut.println("95% confidence interval = [" + percs.confidenceLo() +", " + percs.confidenceHi() + "]");
    }
}
