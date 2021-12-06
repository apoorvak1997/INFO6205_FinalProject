
import java.io.*;


public class MSDRadixSort_Three {
    private static final int radix = 256;
    private static final int cutoff = 15;
    private static String[] aux;       // auxiliary array for distribution
    /**
     * Sort from a[lo] to a[hi] (exclusive), ignoring the first d characters of each String.
     * This method is recursive.
     *
     * @param a the array to be sorted.
     * @param lo the low index.
     * @param hi the high index (one above the highest actually processed).
     * @param d the number of characters in each String to be skipped.
     */
    public static void sort(String[] a) {
        int n = a.length;
        aux = new String[n];
        sort(a, 0, n-1, 0);
    }
    /**
     * Sort from a[lo] to a[hi] (exclusive), ignoring the first d characters of each String.
     * This method is recursive.
     *
     * @param a the array to be sorted.
     * @param lo the low index.
     * @param hi the high index (one above the highest actually processed).
     * @param d the number of characters in each String to be skipped.
     */
    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi < lo+cutoff) InsertionSortMSD.sort(a, lo, hi, d);
        else {
            int[] count = new int[radix + 2];        // Compute frequency counts.

            for (int i = lo; i <= hi; i++)
                count[charAt(a[i], d) + 2]++;

            for (int r = 0; r < radix + 1; r++)      // Transform counts to indices.
                count[r + 1] += count[r];
            int j=0;
            for (int i = lo; i <= hi; i++) {  // Distribute.
                int counter=charAt(a[i], d) + 1;
                aux[count[counter]++] = a[i];
            }

//             Copy back.
            for (int i = lo; i <= hi; i++) {
                a[i] = aux[i-lo];

            }

            for(int r=0;r<radix;r++){
                sort(a, lo+count[r],lo+count[r+1]-1,d+1);
            }
        }
    }


    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        else return -1;
    }


    public static void main(String args[]) throws IOException {
        // runs for 1M names
        preprocessing.preprocessing("MSDString", "STRING", 999998);
    }
}
