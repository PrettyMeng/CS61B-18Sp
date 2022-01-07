import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BinaryTrie implements Serializable {

    private Node root;

    // Trie node class, borrowed from https://algs4.cs.princeton.edu/55compression/Huffman.java.html
    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        MinPQ<Node> pq = new MinPQ<>();
        Set<Character> keys = frequencyTable.keySet();
        for (Character key: keys) {
            pq.insert(new Node(key, frequencyTable.get(key), null, null));
        }

        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        root = pq.delMin();

    }

    public Match longestPrefixMatch(BitSequence querySequence) {
//        int bitLength = querySequence.length();
        Node t = root;
        String s = "";
        int i = 0;
        while (!t.isLeaf()) {
            int bit = querySequence.bitAt(i);
            if (bit == 0) {
                t = t.left;
                s = s + "0";
            } else {
                t = t.right;
                s = s + "1";
            }
            i++;
        }
        return new Match(new BitSequence(s), t.ch);
    }

    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> table = new HashMap<>();
        buildLookupTableHelper(table, root, "");
        return table;
    }

    // destructive recursive method to build the table
    public void buildLookupTableHelper(Map<Character, BitSequence> table, Node root, String s) {
        if (root.isLeaf()) {
            table.put(root.ch, new BitSequence(s));
        } else {
            buildLookupTableHelper(table, root.left, s + "0");
            buildLookupTableHelper(table, root.right, s + "1");
        }
    }
}