import java.io.*;
import java.text.CollationKey;
import java.text.Collator;
import java.util.*;


import java.text.Collator;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

// Import Pinyin
//import javafx.util.Pair;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class MSDRadixSort {
    /**
     * Sort an array of Strings using MSDStringSort.
     *
     * @param a the array to be sorted.
     */
    public static void main(String args[]) throws IOException {
        // runs for 1M names
        preprocessing.preprocessing("MSDPair", "PAIR", 999998);
    }


    public static ChineseToPinyin[] convertToPinyinPair(String[] shuffledChinese){
        ChineseToPinyin[] pinyinConvertedChinese = new ChineseToPinyin[shuffledChinese.length];
        for (int i=0; i<shuffledChinese.length;i++){
            ChineseToPinyin pair = new ChineseToPinyin(toPinYin(shuffledChinese[i]), shuffledChinese[i]);
            pinyinConvertedChinese[i] = pair;
        }
        return pinyinConvertedChinese;
    }

    public static void sortchinese(String[] s) throws
    FileNotFoundException {
        List<String> list = Arrays.asList(s);

//        Collator spCollator = Collator.getInstance(Locale.CHINESE);
        Collections.sort(list);
    }

    public static void msdRadixSortPair(ChineseToPinyin[] pinyinConvertedChinese) {
        int n = pinyinConvertedChinese.length;
        aux = new ChineseToPinyin[n];
        sortPair(pinyinConvertedChinese, 0, n-1, 0);
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

    private static void sortPair(ChineseToPinyin[] a, int lo, int hi, int d) {
        if (hi < lo+cutoff) InsertionSortChineseToPinyinMSD.sort(a, lo, hi, d);
        else {
            int[] count = new int[radix + 2];        // Compute frequency counts.

            for (int i = lo; i <= hi; i++) {
                count[charAt(a[i].pinyin, d) + 2]++;
            }
            for (int r = 0; r < radix + 1; r++)      // Transform counts to indices.
                count[r + 1] += count[r];

            for (int i = lo; i <= hi; i++) {  // Distribute.
                int counter = charAt(a[i].pinyin, d) + 1;
                aux[count[counter]++] = a[i];
            }

            for (int i = lo; i <= hi; i++) {
                a[i] = aux[i - lo];
            }
            for (int r = 0; r < radix; r++) {
                sortPair(a, lo + count[r], lo + count[r + 1] - 1, d + 1);
            }
        }
    }

    private static int charAt(String s, int d) {

        if (d < s.length()) {
            int value= s.charAt(d);
//            System.out.println(value);
            return value;
        }
        else {
            return -1;
        }
    }

    public static String toPinYin(String string){
        char[] c = string.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        for(int i = 0; i< c.length; i++){
            stringBuffer.append(toChar(c[i]));
        }
        return stringBuffer.toString();
    }

    private static String toChar(char c){
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        try{
            String[] pinYin = PinyinHelper.toHanyuPinyinStringArray(c, format);
            if(pinYin != null){
                return pinYin[0];
            }
        }
        catch(BadHanyuPinyinOutputFormatCombination ex){
            ex.printStackTrace();
        }
        return String.valueOf(c);
    }

    private static final int radix = 256;
    private static final int cutoff = 15;
    private static ChineseToPinyin[] aux;       // auxiliary array for distribution

}

