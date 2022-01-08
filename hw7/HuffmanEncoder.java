import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        HashMap<Character, Integer> frequencyTable = new HashMap<>();
        for (char inputSymbol : inputSymbols) {
            if (!frequencyTable.containsKey(inputSymbol)) {
                frequencyTable.put(inputSymbol, 1);
            } else {
                frequencyTable.put(inputSymbol, frequencyTable.get(inputSymbol) + 1);
            }
        }
        return frequencyTable;
    }

    public static void main(String[] args) {
        char[] inputs = FileUtils.readFile(args[0]);
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        Map<Character, Integer> frequencyTable = buildFrequencyTable(inputs);
        BinaryTrie decodingTrie = new BinaryTrie(frequencyTable);
        ow.writeObject(decodingTrie);
        Map<Character, BitSequence> lookupTable = decodingTrie.buildLookupTable();
        List<BitSequence> bitSequences = new ArrayList<>();
        for (char input : inputs) {
            bitSequences.add(lookupTable.get(input));
        }
        BitSequence assembledSequence = BitSequence.assemble(bitSequences);
        ow.writeObject(assembledSequence);
    }
}
