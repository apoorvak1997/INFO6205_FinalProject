import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DualPivotQuickSortStrings {
    private  static String PATH = "/Users/apoorvak/Desktop/PSA_SORTS/INFO6205_FinalProject/MSDRadixSort/src/main/resource/";
    private  static String DestinationFileName = "sortedChinese";
    private  static String SourceFileName = "shuffledChinese";
    private static Map<String, List<String>> mapping = new HashMap<>();

    // quicksort the array a[] using dual-pivot quicksort
    public static void sort(String[] a) {
//        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        assert isSorted(a);
    }

    // quicksort the subarray a[lo .. hi] using dual-pivot quicksort
    private static void sort(String[] a, int lo, int hi) {
        if (hi <= lo) return;

        // make sure a[lo] <= a[hi]
        if (less(a[hi], a[lo])) exch(a, lo, hi);

        int lt = lo + 1, gt = hi - 1;
        int i = lo + 1;
        while (i <= gt) {
            if       (less(a[i], a[lo])) exch(a, lt++, i++);
            else if  (less(a[hi], a[i])) exch(a, i, gt--);
            else                         i++;
        }
        exch(a, lo, --lt);
        exch(a, hi, ++gt);

        // recursively sort three subarrays
        sort(a, lo, lt-1);
        if (less(a[lt], a[gt])) sort(a, lt+1, gt-1);
        sort(a, gt+1, hi);

        assert isSorted(a, lo, hi);
    }



    /***************************************************************************
     *  Helper sorting functions.
     ***************************************************************************/

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    /***************************************************************************
     *  Check if array is sorted - useful for debugging.
     ***************************************************************************/
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }



    // print array to standard output
//    private static void show(Comparable[] a) {
//        for (int i = 0; i < a.length; i++) {
//            StdOut.println(a[i]);
//        }
//    }

    private static void writeSortedChinesePair(String[] pinyinConvertedChinese) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(PATH+DestinationFileName+".txt");
        for (int i=0; i<pinyinConvertedChinese.length;i++){
            List<String> stringList = mapping.get(pinyinConvertedChinese[i]);
//            out.println(convertedNames[i]);
            out.println(stringList.toArray()[0]);
            if (stringList.size() > 1) {
                stringList.remove(stringList.toArray()[0]);
            }
        }
        out.close();
    }

    public static String[] convertToPinyinPair(String[] shuffledChinese){
        String[] pinyinConvertedChinese = new String[shuffledChinese.length];
        for (int i=0; i<shuffledChinese.length;i++){
            pinyinConvertedChinese[i] = toPinYin(shuffledChinese[i]);
        }
        return pinyinConvertedChinese;
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

    private static String[] readShuffledChinese(String path) throws IOException {
        BufferedReader in = null;
        String[] shuffledChinese  = new String[999997];
        try {
            in = new BufferedReader(new FileReader(path));
            String name;
            int i=0;
            while ((name = in.readLine()) != null && i!=999997) {
                shuffledChinese[i] = name;
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return shuffledChinese;
    }


    // Read strings from standard input, sort them, and print.
    public static void main(String[] args) throws IOException {

        String[] shuffledChinese = readShuffledChinese(PATH + SourceFileName + ".txt");

        // Convert Chinese to Pinyin
        String[] pinyinConvertedChinese = convertToPinyinPair(shuffledChinese);


        for(int i=0;i< shuffledChinese.length;i++){
            if(!mapping.containsKey(pinyinConvertedChinese[i])){
                List<String> chinese = new ArrayList<>();
                mapping.put(pinyinConvertedChinese[i],chinese);
            }
            List<String> existingChinese = mapping.get(pinyinConvertedChinese[i]);
            existingChinese.add(shuffledChinese[i]);
            //chinese.add(myList[i]);
            mapping.put(pinyinConvertedChinese[i], existingChinese);
        }

        sort(pinyinConvertedChinese);

        writeSortedChinesePair(pinyinConvertedChinese);

        // Write to file
//        writeSortedChinesePair(pinyinConvertedChinese);
//        DualPivotQuickSort.sort(pinyinConvertedChinese);
//        writeSortedChinesePair(pinyinConvertedChinese);
    }

}
