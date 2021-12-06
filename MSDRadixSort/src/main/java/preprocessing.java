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

public class preprocessing {
    private  static String PATH = "/Users/abhisheksatbhai/Desktop/my_neu/PSA/Project/INFO6205_FinalProject/MSDRadixSort/src/main/resource/";
    private  static String DestinationFileName = "sortedChinese";
    private  static String SourceFileName = "shuffledChinese";


    public static void main (String[] args) throws IOException {
    }

    public static void dataprocessing(){

    }

    public static void preprocessing(String ChooseSort, String SortingType, int length) throws IOException {
        System.out.println("choose sort 5: "+ChooseSort);

        Map<String, List<String>> mapping = new HashMap<>();
        // Read shuffled chinese
        String[] shuffledChinese = readShuffledChinese(PATH + SourceFileName + ".txt", length);

        String[] pinyinConvertedChinese = new String[shuffledChinese.length];
        ChineseToPinyin[] pinyinConvertedChinesePair = new ChineseToPinyin[shuffledChinese.length];

        // Convert Chinses to pinyin, choose output type
        if (SortingType.equals("STRING")){
            // Convert Chinese to Pinyin
            pinyinConvertedChinese = convertToPinyin(shuffledChinese);

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
        }
        else if (SortingType.equals("PAIR")){
            pinyinConvertedChinesePair = convertToPinyinPair(shuffledChinese);
        }

//        Choose the sorting algorithm
        if("LSDString".equals(ChooseSort)) {
            LSDStringSort lsd = new LSDStringSort();
            lsd.sort(pinyinConvertedChinese);
        }
        else if("LSDPair".equals(ChooseSort)) {
            LSDStringSortChineseToPinyin lsdp = new LSDStringSortChineseToPinyin();
            lsdp.sort(pinyinConvertedChinesePair);
        }
        else if("MSDString".equals(ChooseSort)) {
            MSDRadixSort_Three msd = new MSDRadixSort_Three();
            msd.sort(pinyinConvertedChinese);
        }
        else if("MSDPair".equals(ChooseSort)) {
            MSDRadixSort msdp = new MSDRadixSort();
            msdp.msdRadixSortPair(pinyinConvertedChinesePair);
        }
        else if("DPQPair".equals(ChooseSort)){
            DualPivotQuickSort.sort(pinyinConvertedChinesePair);
        }
        else if ("DPQString".equals(ChooseSort)){
            DualPivotQuickSortStrings.sort(pinyinConvertedChinese);
        }



        // Write to file String or Pair
        if (SortingType.equals("STRING")){
            writeSortedChinese(pinyinConvertedChinese, mapping);
        }
        else if (SortingType.equals("PAIR")){
            writeSortedChinesePair(pinyinConvertedChinesePair);
        }

    }
    public static String[] readShuffledChinese(String path, int length) throws IOException {
        BufferedReader in = null;
        String[] shuffledChinese  = new String[length];
        try {
            in = new BufferedReader(new FileReader(path));
            String name;
            int i=0;
            while ((name = in.readLine()) != null && i!=length) {
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

    public static ChineseToPinyin[] convertToPinyinPair(String[] shuffledChinese){
        ChineseToPinyin[] pinyinConvertedChinese = new ChineseToPinyin[shuffledChinese.length];
        for (int i=0; i<shuffledChinese.length;i++){
            ChineseToPinyin pair = new ChineseToPinyin(toPinYin(shuffledChinese[i]), shuffledChinese[i]);
            pinyinConvertedChinese[i] = pair;
        }
        return pinyinConvertedChinese;
    }

    private static void writeSortedChinesePair(ChineseToPinyin[] pinyinConvertedChinese) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(PATH + DestinationFileName + ".txt");

        for(int i=0;i<pinyinConvertedChinese.length;i++){
            out.println(pinyinConvertedChinese[i].getChinese());
        }
        out.close();
    }

    private static void writeSortedChinese(String[] pinyinConvertedChinese, Map<String, List<String>> mapping) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(PATH+DestinationFileName+".txt");
        for (int i=0; i<pinyinConvertedChinese.length;i++){
            List<String> stringList = mapping.get(pinyinConvertedChinese[i]);
            out.println(stringList.toArray()[0]);
            if (stringList.size() > 1) {
                stringList.remove(stringList.toArray()[0]);
            }
        }
        out.close();
    }
}
