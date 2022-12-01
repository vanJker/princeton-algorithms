/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class ShortestAncestralPath {
    private int ancestral;      // ancestral of v and w in SAP, -1 for no such path
    private int dist;           // length of SAP

    /** compute the SAP between v and w */
    public ShortestAncestralPath(Digraph G, int v, int w) {
        ancestral = -1;
        dist = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);

        for (int i = 0; i < G.V(); i++) {
            if (!(bfsV.hasPathTo(i) && bfsW.hasPathTo(i)))
                continue;
            int tmp = bfsV.distTo(i) + bfsW.distTo(i);
            if (tmp < dist) {
                ancestral = i;
                dist = tmp;
            }
        }
    }

    /** compute the SAP between any one of source of the v and w */
    public ShortestAncestralPath(Digraph G, Iterable<Integer> v, Iterable<Integer> w) {
        ancestral = -1;
        dist = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);

        for (int i = 0; i < G.V(); i++) {
            if (!(bfsV.hasPathTo(i) && bfsW.hasPathTo(i)))
                continue;
            int tmp = bfsV.distTo(i) + bfsW.distTo(i);
            if (tmp < dist) {
                ancestral = i;
                dist = tmp;
            }
        }
    }

    /** ancestral of SAP */
    public int ancestor() {
        return ancestral;
    }

    /** length of SAP */
    public int length() {
        if (ancestral == -1) {
            return -1;
        }
        return dist;
    }
}
