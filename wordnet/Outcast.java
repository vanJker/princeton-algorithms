/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordnet;    // the WordNet about outcast detection

    /** constructor takes a WordNet object */
    public Outcast(WordNet wordnet) {
        if (wordnet == null) {
            throw new IllegalArgumentException();
        }
        this.wordnet = wordnet;
    }

    /** given an array of WordNet nouns, return an outcast */
    public String outcast(String[] nouns) {
        if (nouns == null) {
            throw new IllegalArgumentException();
        }
        for (String s : nouns) {
            if (s == null)
                throw new IllegalArgumentException();
        }

        int maxDist = 0;
        String lrn = null;      // least related noun
        for (String x : nouns) {
            int dist = 0;
            for (String y : nouns) {
                dist += wordnet.distance(x, y);
            }

            if (dist > maxDist) {
                maxDist = dist;
                lrn = x;
            }
        }
        return lrn;
    }

    /** test client */
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
