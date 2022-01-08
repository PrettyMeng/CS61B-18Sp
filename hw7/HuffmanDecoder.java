import java.util.ArrayList;

public class HuffmanDecoder {
    public static void main(String[] args) {
        ObjectReader reader = new ObjectReader(args[0]);
        BinaryTrie trie = (BinaryTrie) reader.readObject();
        BitSequence sq = (BitSequence) reader.readObject();
        ArrayList<Character> decoded = new ArrayList<>();
        while (sq.length() > 0) {
            Match matchResult = trie.longestPrefixMatch(sq);
            decoded.add(matchResult.getSymbol());
            sq = sq.allButFirstNBits(matchResult.getSequence().length());
        }
        ObjectWriter writer = new ObjectWriter(args[1]);
        char[] decodedCharArray = new char[decoded.size()];
        for (int i = 0; i < decoded.size(); i++) {
            decodedCharArray[i] = decoded.get(i);
        }
        FileUtils.writeCharArray(args[1], decodedCharArray);
    }
}
