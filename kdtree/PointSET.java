import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

public class PointSET {
    private SET<Point2D> set;  // set contain all points

    /** construct an empty set of points. */
    public PointSET() {
        set = new SET<>();
    }

    /** is the set empty? */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /** number of points in the set. */
    public int size() {
        return set.size();
    }

    /** add the point to the set (if it is not already in the set). */
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        set.add(p);
    }

    /** does the set contain point p. */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return set.contains(p);
    }

    /** draw all points to standard draw. */
    public void draw() {
        for (Point2D p : set) {
            p.draw();
        }
    }

    /** all points that are inside the rectangle (or on the boundary). */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        Queue<Point2D> queue = new Queue<>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                queue.enqueue(p);
            }
        }
        return queue;
    }

    /** a nearest neighbor in the set to point p; null if the set is empty. */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        double lowestDistance = Double.POSITIVE_INFINITY;
        Point2D result = null;
        for (Point2D q : set) {
            if (p.distanceTo(q) < lowestDistance) {
                lowestDistance = p.distanceTo(q);
                result = q;
            }
        }
        return result;
    }

    /** unit testing of the methods (optional) . */
    public static void main(String[] args) {
        PointSET pointSet = new PointSET();

        In in = new In(args[0]);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            pointSet.insert(new Point2D(x, y));
        }

        Point2D origin = new Point2D(0, 0);
        StdOut.println("Is empty: " + pointSet.isEmpty());
        StdOut.println("Number of points: " + pointSet.size());
        StdOut.println("Contain origin point: " + pointSet.contains(origin));
        pointSet.draw();
    }
}
