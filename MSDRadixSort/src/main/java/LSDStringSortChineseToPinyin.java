
import java.io.*;
import java.text.Collator;
import java.util.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.text.CollationKey;
import java.text.Collator;
import java.util.*;


import java.text.Collator;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

// Import Pinyin

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class LSDStringSortChineseToPinyin {
    private final int ASCII_RANGE = 256;
    public static void main(String[] args) throws IOException {
        // runs for 1M names
        preprocessing.preprocessing("LSDPair", "PAIR", 999998);
    }
    /**
     * findMaxLength method returns maximum length of all available strings in an array
     *
     * @param a It contains an array of String from which maximum length needs to be found
     * @return int Returns maximum length value
     */

    private static int findMaxLength(ChineseToPinyin[] a) {
        int maxLength = a[0].pinyin.length();
        for (ChineseToPinyin c : a){
            maxLength = Math.max(c.getPinyin().length(),maxLength);

        }
        return maxLength;
    }

    /**
     * charAsciiVal method returns ASCII value of particular character in a String.
     *
     * @param str          String input for which ASCII Value need to be found
     * @param charPosition Character position of which ASCII value needs to be found. If character
     *                     doesn't exist then ASCII value of null i.e. 0 is returned
     * @return int Returns ASCII value
     */
    private static int charAsciiVal(String str, int charPosition) {
        if (charPosition >= str.length()) {
            return 0;
        }
        return str.charAt(charPosition);
    }

    /**
     * charSort method is implementation of LSD sort algorithm at particular character.
     *
     * @param a       It contains an array of String on which LSD char sort needs to be performed
     * @param charPosition This is the character position on which sort would be performed
     * @param from         This is the starting index from which sorting operation will begin
     * @param to           This is the ending index up until which sorting operation will be continued
     */
    private static void charSort(ChineseToPinyin[] a, int charPosition, int from, int to) {
        final int ASCII_RANGE = 256;
        int[] count = new int[ASCII_RANGE + 2];
        ChineseToPinyin[] result = new ChineseToPinyin[a.length];

        for (int i = from; i <= to; i++) {
            int c = charAsciiVal(a[i].getPinyin(), charPosition);
            count[c + 2]++;
        }

        // transform counts to indices
        for (int r = 1; r < ASCII_RANGE + 2; r++)
            count[r] += count[r - 1];

        // distribute
        for (int i = from; i <= to; i++) {
            int c = charAsciiVal(a[i].getPinyin(), charPosition);
            result[count[c + 1]++] = a[i];
        }

        // copy back
        if (to + 1 - from >= 0) System.arraycopy(result, 0, a, from, to + 1 - from);
    }

    /**
     * sort method is implementation of LSD String sort algorithm.
     *
     * @param a It contains an array of String on which LSD sort needs to be performed
     * @param from   This is the starting index from which sorting operation will begin
     * @param to     This is the ending index up until which sorting operation will be continued
     */
    public static void sort(ChineseToPinyin[] a, int from, int to) {
        int maxLength = findMaxLength(a);
        for (int i = maxLength - 1; i >= 0; i--)
            charSort(a, i, from, to);
    }

    /**
     * sort method is implementation of LSD String sort algorithm.
     *
     * @param a It contains an array of String on which LSD sort needs to be performed
     */
    public static void sort(ChineseToPinyin[] a) {
        sort(a, 0, a.length - 1);
    }
}

