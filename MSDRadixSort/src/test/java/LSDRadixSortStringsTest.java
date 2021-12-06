
import javafx.util.Pair;
import org.junit.Test;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Collator;
import java.util.*;
import static org.junit.Assert.*;


public class LSDRadixSortStringsTest {
    @Test
    public void testSortingFirstFiveLSD() throws Exception {
        String[] shuffledChinese = {
                "刘持平",
                "洪文胜",
                "樊辉辉",
                "苏会敏",
                "高民政"
        };

        // Call our MSDRadixSort using Pair
        String[] pinyinConvertedChinese = preprocessing.convertToPinyin(shuffledChinese);

        // take the key form pairs before sorting
        String[] shuffledList = new String[pinyinConvertedChinese.length];
        for(int i=0;i<shuffledList.length;i++){
            shuffledList[i]= pinyinConvertedChinese[i];
        }

        // implement sorting

        LSDStringSort.sort(pinyinConvertedChinese);

        // take the key form pairs after sorting
        List<String> sortedList = new ArrayList<>();
        for(int i=0;i<pinyinConvertedChinese.length;i++){
            sortedList.add(pinyinConvertedChinese[i]);
        }

        assertEquals(sortUsingPinyin(shuffledList),sortedList);
    }

    @Test
    public void testSortingOrderingForEntireFile() throws Exception {
        String[] shuffledChinese =  preprocessing.readShuffledChinese("/Users/abhisheksatbhai/Desktop/my_neu/PSA/Project/INFO6205_FinalProject/MSDRadixSort/src/main/resource/shuffledChinese.txt", 999998);

        // Call our MSDRadixSort using Pair
        String[] pinyinConvertedChinese = preprocessing.convertToPinyin(shuffledChinese);

        // take the key form pairs before sorting
        String[] shuffledList = new String[999998];
        for(int i=0;i<shuffledList.length;i++){
            shuffledList[i]= pinyinConvertedChinese[i];
        }
        // implement sorting

        LSDStringSort.sort(pinyinConvertedChinese);

        // take the key form pairs after sorting
        List<String> sortedList = new ArrayList<>();
        for(int i=0;i<999998;i++){
            sortedList.add(pinyinConvertedChinese[i]);
        }

        assertEquals(sortUsingPinyin(shuffledList),sortedList);
    }

    public static List<String> sortUsingPinyin(String[] s) throws FileNotFoundException {
        List<String> list = Arrays.asList(s);
        Collections.sort(list);
        return list;
    }

}

