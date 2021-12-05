import javafx.util.Pair;
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

public class MSDRadixSort_Three {
    private static final int radix = 256;
    private static final int cutoff = 15;
    private  static String PATH = "/Users/apoorvak/Desktop/PSA_SORTS/INFO6205_FinalProject/MSDRadixSort/src/main/resource/";
    private  static String DestinationFileName = "sortedChinese";
    private  static String SourceFileName = "shuffledChinese";
    private static String[] aux;       // auxiliary array for distribution


    static String[] readShuffledChinese(String path) throws IOException {
        BufferedReader in = null;
        String[] shuffledChinese  = new String[999998];
        try {
            in = new BufferedReader(new FileReader(path));
            String name;
            int i=0;
            while ((name = in.readLine()) != null && i!=999998) {
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


    public static String toPinYin(String string){
        char[] c = string.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        for(int i = 0; i< c.length; i++){
            stringBuffer.append(toChar(c[i]));
        }
        return stringBuffer.toString();
    }

    public static String[] convertToPinyinPair(String[] shuffledChinese){
        String[] pinyinConvertedChinese = new String[shuffledChinese.length];
        for (int i=0; i<shuffledChinese.length;i++){
            pinyinConvertedChinese[i] = toPinYin(shuffledChinese[i]);
        }
        return pinyinConvertedChinese;
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


    private static void writeSortedChinesePair(String[] pinyinConvertedChinese) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(PATH+DestinationFileName+".txt");

        for(int i=0;i<pinyinConvertedChinese.length;i++){
            out.println(pinyinConvertedChinese[i]);
        }
        out.close();
    }

    public static void main(String args[]) throws IOException {
        // Read shuffled chinese
        String[] shuffledChinese = readShuffledChinese(PATH + SourceFileName + ".txt");

        // Convert Chinese to Pinyin
        String[] pinyinConvertedChinese = convertToPinyinPair(shuffledChinese);

        Map<String, List<String>> mapping = new HashMap<>();
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
    }
}
