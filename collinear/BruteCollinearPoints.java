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

public class BruteCollinearPoints {
    private final Queue<LineSegment> segments; // line segments

    /** finds all line segments containing 4 points */
    public BruteCollinearPoints(Point[] points) {
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
        Point[] tmp = new Point[points.length]; // avoid mutate argument array
        for (int i = 0; i < points.length; i++) {
            tmp[i] = points[i];
        }
        points = tmp;

        Arrays.sort(points); // ensure segment p->s (p and s are the smallest and largest points)
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int h = k + 1; h < points.length; h++) {
                        // if collinear, add segments (the smallest index as start)
                        if (isCollinear(points[i], points[j], points[k], points[h])) {
                            segments.enqueue(new LineSegment(points[i], points[h]));
                        }
                    }
                }
            }
        }
    }

    /** return true if four points are collinear, return false otherwise. */
    private boolean isCollinear(Point p, Point q, Point r, Point s) {
        double slopeOfPQ = p.slopeTo(q);
        double slopeOfPR = p.slopeTo(r);
        double slopeOfPS = p.slopeTo(s);
        return slopeOfPQ == slopeOfPR && slopeOfPR == slopeOfPS;
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

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        StdOut.println("number of line segments: " + bcp.numberOfSegments());
        StdOut.println("collinear line segments: ");
        LineSegment[] ls = bcp.segments();
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
