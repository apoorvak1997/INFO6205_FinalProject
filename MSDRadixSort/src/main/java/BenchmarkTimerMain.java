import java.io.*;
import java.text.Collator;
import java.util.*;
import java.util.function.Supplier;
import java.util.List;
import java.util.Locale;
// Import Pinyin

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import util.Benchmark_Timer;
import util.LazyLogger;

public class BenchmarkTimerMain {
    public static void main(String args[]) throws IOException {
        int n=10000;
        int runs=500;
        BufferedReader in = null;
        String[] myList = new String[999998];
        try {
            in = new BufferedReader(new FileReader("/Users/abhisheksatbhai/Desktop/my_neu/PSA/Project/INFO6205_FinalProject/MSDRadixSort/src/main/resource/shuffledChinese.txt"));
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

        //sort(convertedNames);


        //Random random=new Random();
        //Integer[] arr={5,3,1,7,2,9,3,7,1,4};
        for(int i=0;i<1;i++) {
//        Integer[] array_sorted = new Integer[n];
//        Integer[] array_random = new Integer[n];
//        Integer[] array_reverse = new Integer[n];
//        Integer[] array_partial = new Integer[n];


//        for (int j = 0; j < n; j++) {
//            array_random[j] = random.nextInt(n);
//            array_sorted[j] = array_random[j];
////            System.out.println(array_sorted[j]);
////            System.out.println("------------------------");
//
//        }
//        Arrays.sort(array_sorted);
//        for(int l=0;l<array_sorted.length;l++)
//        {
//            if(l<=n/2)
//            {
//                array_partial[l]=array_sorted[l];
//            }
//            else{
//                array_partial[l]=array_random[l];
//            }
//            array_reverse[n - l-1] = array_sorted[l];
//        }

            //InsertionSort ins=new InsertionSort();
            LSDStringSort lsd=new LSDStringSort();
            MSDRadixSort_Three msd= new MSDRadixSort_Three();

//
            Benchmark_Timer<String[]> timer_r=new Benchmark_Timer<>("Benchmarking",null,(x)-> {
                try {
                    preprocessing.preprocessing("LSDString", "STRING", 1000000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            },null);

            //Benchmark_Timer<Integer[]> timer_r2=new Benchmark_Timer<>("Benchmarking",null,(x)->msd.sort(x,0,x.length),null);
//        Benchmark_Timer<Integer[]> timer_r3=new Benchmark_Timer<>("Benchmarking",null,(x)->ins.sort(x,0,x.length),null);
//        Benchmark_Timer<Integer[]> timer_r4=new Benchmark_Timer<>("Benchmarking",null,(x)->ins.sort(x,0,x.length),null);
//            Supplier sup_reverse=( )-> {
//                try {
//                     preprocessing.preprocessing("LSDString", "STRING", 1000000);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            },null);
        Supplier sup_random=() -> convertedNames;
//
//        Supplier sup_sorted=() -> array_sorted;
//        Supplier sup_partial=() -> array_partial;

            double time=timer_r.runFromSupplier(sup_random,0);

           System.out.println("When n is "+n+" mean time is "+time+" for LSD");
//        System.out.println();
//        double time_random=timer_r.runFromSupplier(sup_random,runs);
//
//        System.out.println("When n is "+n+" mean time is "+time_random+" for a random array");
//        System.out.println();
//
//        double time_sorted=timer_r.runFromSupplier(sup_sorted,runs);
//        System.out.println("When n is "+n+" mean time is "+time_sorted+" for a sorted array");
//        System.out.println();
//        double time_partial=timer_r.runFromSupplier(sup_partial,runs);
//        System.out.println("When n is "+n+" mean time is "+time_partial+" for a partial sorted array");
//        System.out.println();







        }

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
    final static LazyLogger logger= new LazyLogger(Benchmark_Timer.class);
}
