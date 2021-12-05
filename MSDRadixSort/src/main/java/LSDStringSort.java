
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

public class LSDStringSort {


    private final int ASCII_RANGE = 256;
public static void main(String[] args) throws IOException {
    BufferedReader in = null;
    String[] myList = new String[999998];
    try {
        in = new BufferedReader(new FileReader("C:\\Users\\sneha\\IdeaProjects\\INFO6205\\src\\main\\java\\edu\\neu\\coe\\info6205\\sort\\counting\\ShuffledChinese"));
        String str;
        int i=0;
        while ((str = in.readLine()) != null && i<=999997) {
            myList[i]=str;
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


    Collator spCollator = Collator.getInstance(Locale.CHINESE);
    String[] convertedNames = convertToPinyin(myList);
    Map<String, List<String>> lookup = new HashMap<>();
    for(int i=0;i<convertedNames.length;i++){
        if(!lookup.containsKey(convertedNames[i])){
            List<String> chinese = new ArrayList<>();
            lookup.put(convertedNames[i],chinese );
        }
        List<String> existingChinese = lookup.get(convertedNames[i]);
        existingChinese.add(myList[i]);

    }


    String[] sortThis = new String[myList.length];

    sort(convertedNames);

    PrintWriter out = new PrintWriter("output.txt");
    //Set<String> set = mapping.get(convertedNames[0]);
    //out.println("   " +set.toArray()[0]);
    for (int i=0; i<convertedNames.length;i++){
        List<String> stringList = lookup.get(convertedNames[i]);
//            out.println(convertedNames[i]);
        out.println(stringList.toArray()[0]);
        if (stringList.size() > 1) {
            stringList.remove(stringList.toArray()[0]);
        }
    }
    out.close();
}
    public static String[] convertToPinyin(String[] c){
        String[] convertedNames = new String[c.length];
        for (int i=0; i<c.length;i++){
            convertedNames[i] = toPinYin(c[i]);
        }
        return convertedNames;
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
    /**
     * findMaxLength method returns maximum length of all available strings in an array
     *
     * @param strArr It contains an array of String from which maximum length needs to be found
     * @return int Returns maximum length value
     */

    private static int findMaxLength(String[] strArr) {
        int maxLength = strArr[0].length();
        for (String str : strArr)
            maxLength = Math.max(maxLength, str.length());
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
     * @param strArr       It contains an array of String on which LSD char sort needs to be performed
     * @param charPosition This is the character position on which sort would be performed
     * @param from         This is the starting index from which sorting operation will begin
     * @param to           This is the ending index up until which sorting operation will be continued
     */
    private static void charSort(String[] strArr, int charPosition, int from, int to) {
        final int ASCII_RANGE = 256;
        int[] count = new int[ASCII_RANGE + 2];
        String[] result = new String[strArr.length];

        for (int i = from; i <= to; i++) {
            int c = charAsciiVal(strArr[i], charPosition);
            count[c + 2]++;
        }

        // transform counts to indices
        for (int r = 1; r < ASCII_RANGE + 2; r++)
            count[r] += count[r - 1];

        // distribute
        for (int i = from; i <= to; i++) {
            int c = charAsciiVal(strArr[i], charPosition);
            result[count[c + 1]++] = strArr[i];
        }

        // copy back
        if (to + 1 - from >= 0) System.arraycopy(result, 0, strArr, from, to + 1 - from);
    }

    /**
     * sort method is implementation of LSD String sort algorithm.
     *
     * @param strArr It contains an array of String on which LSD sort needs to be performed
     * @param from   This is the starting index from which sorting operation will begin
     * @param to     This is the ending index up until which sorting operation will be continued
     */
    public static void sort(String[] strArr, int from, int to) {
        int maxLength = findMaxLength(strArr);
        for (int i = maxLength - 1; i >= 0; i--)
            charSort(strArr, i, from, to);
    }

    /**
     * sort method is implementation of LSD String sort algorithm.
     *
     * @param strArr It contains an array of String on which LSD sort needs to be performed
     */
    public static void sort(String[] strArr) {
        sort(strArr, 0, strArr.length - 1);
    }
}
