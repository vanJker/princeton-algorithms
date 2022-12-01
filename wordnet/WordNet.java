/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.Map;

public class WordNet {
    private Digraph digraph;                // digraph of word net
    private Map<Integer, String> st;        // symbol table of vertices
    private Map<String, Integer> nouns;     // all nouns (since a synset may contain more nouns)
    private SAP sap;                        // shortest ancestral path

    /** constructor takes the name of the two input files */
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }

        st = new HashMap<>();
        nouns = new HashMap<>();

        // setup vertices and st from synsets
        int numV = 0;  // number of vertices
        In in = new In(synsets);

        while (in.hasNextLine()) {
            numV++;
            String line = in.readLine();
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            String synset = fields[1];
            st.put(id, synset);

            // add all nouns in the synset to the set of nouns
            String[] ws = synset.split(" ");
            for (int i = 0; i < ws.length; i++)
                nouns.put(ws[i], id);
        }
        in.close();

        // setup digraph and its edges
        digraph = new Digraph(numV);
        in = new In(hypernyms);

        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);

            // add the hypernym relationships
            for (int i = 1; i < fields.length; i++) {
                int hypernymId = Integer.parseInt(fields[i]);
                digraph.addEdge(id, hypernymId);
            }
        }
        in.close();

        // The input to the constructor does not correspond to a rooted DAG.
        DirectedCycle directedCycle = new DirectedCycle(digraph);
        if (directedCycle.hasCycle()) {
            throw new IllegalArgumentException();
        }
        sap = new SAP(digraph);
    }

    /** returns all WordNet nouns */
    public Iterable<String> nouns() {
        // a synset may contain more than one noun
        // thus, we store nouns in an additional set
        // using map for immutable class
        return nouns.keySet();
    }

    /** is the word a WordNet noun? */
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return nouns.containsKey(word);
    }

    /** distance between nounA and nounB */
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        else if (!(isNoun(nounA) && isNoun(nounB))) {
            throw new IllegalArgumentException();
        }

        int v = nouns.get(nounA);
        int w = nouns.get(nounB);
        return sap.length(v, w);
    }

    /**
     * a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
     * in the shortest ancestral path.
     */
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        else if (!(isNoun(nounA) && isNoun(nounB))) {
            throw new IllegalArgumentException();
        }

        int v = nouns.get(nounA);
        int w = nouns.get(nounB);
        int ancestor = sap.ancestor(v, w);
        if (ancestor == -1) { // no such sap, just return null
            return null;
        }
        return st.get(ancestor);
    }

    /** do unit testing of this class */
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0], args[1]);

        int n = 10;
        StdOut.println(n + " nouns in the WordNet: ");
        for (String noun : wordNet.nouns()) {
            StdOut.println(noun);
            n--;
            if (n == 0) break;
        }

        String[] words = new String[] { "1840s", "A", "A a" };
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            StdOut.println("Is noun [" + word + "] a WordNet noun? " + wordNet.isNoun(word));
        }
    }
}
