/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private Queue<LineSegment> segments; //  line segments

    /** finds all line segments containing 4 or more points */
    public FastCollinearPoints(Point[] points) {
        // points is null
        if (points == null) {
            throw new IllegalArgumentException();
        }
        // any point in the array is null
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }
        // contains a repeated point
        // Arrays.sort(points); // cost O(N) if use sort
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[j].compareTo(points[i]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        segments = new Queue<>();
        // Arrays.sort(points); // sort for rest sorts' stable (smaller points are smaller indices)
        Queue<Point> ps = new Queue<>(); // copy of sorted points (since other in-place sorts)
        for (int i = 0; i < points.length; i++) {
            ps.enqueue(points[i]);
        }

        for (Point p: ps) {
            // StdOut.println("DEBUG: " + \n" + p);
            Arrays.sort(points); // stable for later sort (smaller points are smaller indices)
            Arrays.sort(points, p.slopeOrder()); // sort points by slope with p
            int lo = 1; // lower index
            int hi = 1; // higher index
            for (int j = 1; j < points.length - 1; j++) { // now points[0] is p itself
                // StdOut.print("DEBUG: " + points[j] + " ");
                if (p.slopeTo(points[j]) == p.slopeTo(points[j + 1])) {
                    hi++;
                }
                // Note: when not collinear or at end of array
                if (p.slopeTo(points[j]) != p.slopeTo(points[j + 1]) || j == points.length - 2) {
                    if ((hi-lo+1) >= 3 && p.compareTo(points[lo]) < 0) {
                        segments.enqueue(new LineSegment(p, points[hi]));
                    }
                    lo = j + 1;
                    hi = j + 1;
                }
            }
        }
    }

    /** the number of line segments */
    public int numberOfSegments() {
        return segments.size();
    }

    /** the line segments */
    public LineSegment[] segments() {
        LineSegment[] array = new LineSegment[segments.size()];
        int i = 0; // index
        for (LineSegment ls: segments) {
            array[i++] = ls;
        }
        return array;
    }

    /*
    // unit tests
    public static void main(String[] args) {
        int n = StdIn.readInt(); // number of points
        Point[] points = new Point[n];
        int i = 0;

        while (!StdIn.isEmpty()) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i++] = new Point(x, y);
        }

        FastCollinearPoints fcp = new FastCollinearPoints(points);
        StdOut.println("number of line segments: " + fcp.numberOfSegments());
        StdOut.println("collinear line segments: ");
        LineSegment[] ls = fcp.segments();
        for (int j = 0; j < ls.length; j++) {
            StdOut.println(ls[j]);
        }
    }
    */

    /** sample client */
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
