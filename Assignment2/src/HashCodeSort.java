import java.util.ArrayList;
import java.util.List;

public class HashCodeSort<X extends Comparable<X>> {

    /**
     * Sort the given list "a" and return the result
     * @param a the list to be sorted
     * @return the result
     */
    public List<X> sort(List<X> a) {
        int n = a.size();
        int[] indices = new int[n];
        int[] hashes = new int[n];
        init(a, indices, hashes);
        insertionSort(n, indices, hashes);
        verify(n, indices, hashes, a);
        return createResult(n, indices, hashes, a);
    }

    // Sort the arrays indices and hashes by comparing hashes
    private void insertionSort(int n, int[] indices, int[] hashes) {
        // TODO implement me (8 points)
    }

    // Verify that the arrays are in true order according to natural ordering on X
    private void verify(int n, int[] indices, int[] hashes, List<X> a) {
        // TODO implement me (8 points)
    }

    // Exchange elements i-1 and i in the array
    private void exchange(int[] array, int i) {
        final int temp = array[i];
        array[i] = array[i-1];
        array[i-1] = temp;
    }

    // Build the resulting List        from the indices   
    private List<X> createResult(int n, int[] indices, int[] hashes, List<X> a) {
        List<X> result = new ArrayList<X>();
        for(int i=0;i<n;i++) result.add(a.get(indices[i]));
        return result;
    }

    // Initialize the indices and hashes arrays from the list "a"
    private void init(List<X> a, int[] indices, int[] hashes) {
        int index = 0;
        for (Object x : a) {
            indices[index] = index;
            hashes[index++] = x.hashCode();
        }
    }
}