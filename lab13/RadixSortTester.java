import static org.junit.Assert.*;
import org.junit.Test;

public class RadixSortTester {

    /**
     * Array that will cause CountingSort.naiveCountingSort to fail, but
     * CountingSort.betterCountingSort can handle.
     **/
    private static String[] someString = {"ab", "bc", "da", "cb", "aba"};

    private static String[] answer = {"ab", "aba", "bc", "cb", "da"};

    /**
     * Array that both sorts should sort successfully.
     **/
//    private static int[] nonNegative = {9, 5, 2, 1, 5, 3, 0, 3, 1, 1};

    @Test
    public void testNaiveWithNonNegative() {
        String[] sortedString = RadixSort.sort(someString);
        assertArrayEquals(answer, sortedString);
    }
}