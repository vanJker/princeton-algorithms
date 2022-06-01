/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LinkedQueue<LineSegment> segments; // line segments

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

        segments = new LinkedQueue<>();
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

    /** unit tests */
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
}
