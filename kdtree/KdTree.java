/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private Node root;  // root of kd-tree

    private static class Node { // node of kd-tree
        Point2D point;
        Node left;
        Node right;
        int count;

        public Node(Point2D p) {
            point = p;
            count = 1;
        }
    }

    /** construct an empty set of points. */
    public KdTree() {
        root = null;
    }

    /** is the set empty? */
    public boolean isEmpty() {
        return root == null;
    }

    /** number of points in the set. */
    public int size() {
        return size(root);
    }

    /** number of points of kd-tree root at x. */
    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        else {
            return x.count;
        }
    }

    /** add the point to the set (if it is not already in the set). */
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        root = insert(root, p, 0);
    }

    /** add the point p to the kd-tree rooted at x. */
    private Node insert(Node x, Point2D p, int level) {
        if (x == null) {
            return new Node(p);
        }
        else if (p.equals(x.point)) { // already in kd-tree
            return x;
        }

        if (less(p, x.point, level)) {
            x.left = insert(x.left, p, level + 1);
        }
        else {
            x.right = insert(x.right, p, level + 1);
        }
        x.count = size(x.left) + size(x.right) + 1;
        return x;
    }

    /** does the set contain point p? */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return contains(root, p, 0);
    }

    /** does the kd-tree root at x contain point p? */
    private boolean contains(Node x, Point2D p, int level) {
        if (x == null) {
            return false;
        }
        else if (p.equals(x.point)) {
            return true;
        }

        if (less(p, x.point, level)) {
            return contains(x.left, p, level + 1);
        }
        else {
            return contains(x.right, p, level + 1);
        }
    }

    /** draw all points to standard draw. */
    public void draw() {
        draw(root, 0, 0, 0, 1, 1);
    }

    /** draw kd-tree root at x (by recursion). */
    private void draw(Node x, int level, double xmin, double ymin, double xmax, double ymax) {
        if (x == null) {
            return;
        }

        // draw point
        Point2D p = x.point;
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(p.x(), p.y());

        // draw line
        StdDraw.setPenRadius();
        if (level % 2 == 0) { // even level (vertical splits)
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(p.x(), ymin, p.x(), ymax);
            draw(x.left, level + 1, xmin, ymin, p.x(), ymax);
            draw(x.right, level + 1, p.x(), ymin, xmax, ymax);
        }
        else { // odd level (horizontal split)
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xmin, p.y(), xmax, p.y());
            draw(x.left, level + 1, xmin, ymin, xmax, p.y());
            draw(x.right, level + 1, xmin, p.y(), xmax, ymax);
        }

        // draw(x.left, level + 1);
        // draw(x.right, level + 1);
    }

    /** all points that are inside the rectangle (or on the boundary). */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        Queue<Point2D> queue = new Queue<>();
        range(root, rect, 0, queue);
        return queue;
    }

    /** all points that are inside the rectangle at kd-tree root at x. */
    private void range(Node x, RectHV rect, int level, Queue<Point2D> queue) {
        if (x == null) {
            return;
        }
        else if (rect.contains(x.point)) {
            queue.enqueue(x.point);
        }

        int cmp = compare(rect, x.point, level);
        if (cmp == -1) { // at left/bottom of the line
            range(x.left, rect, level + 1, queue);
        }
        else if (cmp == 1) { // at right/above of the line
            range(x.right, rect, level + 1, queue);
        }
        else { // split the rectangle
            range(x.left, rect, level + 1, queue);
            range(x.right, rect, level + 1, queue);
        }
    }

    /** a nearest neighbor in the set to the point p; null if the set is empty. */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) { // set is empty
            return null;
        }
        Point2D result = nearest(root, p, 0, root.point);
        return result;
    }

    /** find a nearest neighbor in kd-tree root at x. */
    private Point2D nearest(Node x, Point2D p, int level, Point2D champion) {
        if (x == null) {
            return champion;
        }

        // update champion point
        if (p.distanceSquaredTo(x.point) < p.distanceSquaredTo(champion)) {
            champion = x.point;
        }

        Node b1, b2;    // b1 is at the same side of point p
        if (less(p, x.point, level)) {
            b1 = x.left;
            b2 = x.right;
        }
        else {
            b1 = x.right;
            b2 = x.left;
        }

        // vertical point
        Point2D verticalPoint;
        if (level % 2 == 0) { // even level
            verticalPoint = new Point2D(x.point.x(), p.y());
        }
        else {
            verticalPoint = new Point2D(p.x(), x.point.y());
        }

        // organize method
        Point2D p1 = nearest(b1, p, level + 1, champion);
        Point2D p2 = null;
        if (p.distanceSquaredTo(champion) > p.distanceSquaredTo(verticalPoint)) {
            p2 = nearest(b2, p, level + 1, champion);
        }

        if (p2 == null || p.distanceSquaredTo(p1) < p.distanceSquaredTo(p2)) {
            return p1;
        }
        else {
            return p2;
        }
    }

    /** is point p is less than (at left or bottom of) point q at given level? */
    private static boolean less(Point2D p, Point2D q, int level) {
        if (level % 2 == 0) {   // even level
            return p.x() < q.x();
        }
        else {                  // odd level
            return p.y() < q.y();
        }
    }

    /**
     * return -1 if rectangle is at left/bottom of point p,
     * 0 if point p split rectangle, 1 if rectangle is at
     * right/above of rectangle.
     */
    private static int compare(RectHV rect, Point2D p, int level) {
        double min, max;
        double line;

        if (level % 2 == 0) {   // even level
            min = rect.xmin();
            max = rect.xmax();
            line = p.x();
        }
        else {                  // odd level
            min = rect.ymin();
            max = rect.ymax();
            line = p.y();
        }

        if (max < line) {           // at left/bottom
            return -1;
        }
        else if (min > line) {    // at right/above
            return 1;
        }
        else {                    // split rectangle
            return 0;
        }
    }

    /** unit testing of the methods (optional). */
    public static void main(String[] args) {
        KdTree kdTree = new KdTree();

        In in = new In(args[0]);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            kdTree.insert(new Point2D(x, y));
        }

        Point2D origin = new Point2D(0, 0);
        RectHV rect = new RectHV(0.5, 0, 1, 1);

        StdOut.println("Is empty: " + kdTree.isEmpty());
        StdOut.println("Number of points: " + kdTree.size());
        StdOut.println("Contain origin point: " + kdTree.contains(origin));
        StdOut.println("Right half of rectangle contains: " + kdTree.range(rect));
        StdOut.println("Nearest point of origin point: " + kdTree.nearest(origin));
        kdTree.draw();
    }
}
