/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private Digraph digraph;

    /** constructor takes a digraph (not necessarily a DAG) */
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        digraph = new Digraph(G);
    }

    /** length of the shortest ancestral path between v and w; -1 if no such path */
    public int length(int v, int w) {
        if (v < 0 || v >= digraph.V() || w < 0 || w >= digraph.V()) {
            throw new IllegalArgumentException();
        }
        ShortestAncestralPath sap = new ShortestAncestralPath(digraph, v, w);
        return sap.length();
    }

    /**
     * a common ancestor of v and w that participates in the shortest ancestral path;
     * -1 if no such path
     */
    public int ancestor(int v, int w) {
        if (v < 0 || v >= digraph.V() || w < 0 || w >= digraph.V()) {
            throw new IllegalArgumentException();
        }
        ShortestAncestralPath sap = new ShortestAncestralPath(digraph, v, w);
        return sap.ancestor();
    }

    /**
     * length of the shortest ancestral path between any vertex in v and any vertex in w;
     * -1 if no such path
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        if (!v.iterator().hasNext() || !w.iterator().hasNext()) {
            throw new IllegalArgumentException();
        }
        for (Integer x : v) {
            if (x == null)
                throw new IllegalArgumentException();
        }
        for (Integer y : w) {
            if (y == null)
                throw new IllegalArgumentException();
        }

        ShortestAncestralPath sap = new ShortestAncestralPath(digraph, v, w);
        return sap.length();
    }

    /** a common ancestor that participates in the shortest ancestral path; -1 if no such path */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        if (!v.iterator().hasNext() || !w.iterator().hasNext()) {
            throw new IllegalArgumentException();
        }
        for (Integer x : v) {
            if (x == null)
                throw new IllegalArgumentException();
        }
        for (Integer y : w) {
            if (y == null)
                throw new IllegalArgumentException();
        }

        ShortestAncestralPath sap = new ShortestAncestralPath(digraph, v, w);
        return sap.ancestor();
    }

    /** do unit testing if this class */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
