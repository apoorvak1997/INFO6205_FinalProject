import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Timer {

   public static void main (String[] args) throws IOException {
       List<Integer> numbers = Arrays.asList(250000,500000,999998,1999996,3999992);
       List<String> sorting_algorithm_string= Arrays.asList("LSDString","MSDString","DPQString");
       List<String> sorting_algorithm_pair= Arrays.asList("LSDPair","MSDPair","DPQPair");
       int runs = 1;
        // run benchmark for arrays of string implementation

       for (String name: sorting_algorithm_string){
               for (int i : numbers){
                   // Record the start time
                   long start2 = System.currentTimeMillis();
                   // N runs
                   for (int j=1; j<=runs; j++){
                       preprocessing.preprocessing(name, "STRING", i);
                   }
                   // Record the end time
                   long end2 = System.currentTimeMillis();

                   // Print time
                   System.out.println("Sorting Algo : "+ name +" , Elapsed Time in milli seconds to run N : "+ i +" , Time : " + (end2-start2)/runs+" ms");

               }
       }
       // Run benchmark for  EXTERNAL CLASS which maps chinses to pinyin implementation
       for (String name: sorting_algorithm_pair){
           for (int i : numbers){
               // Record the start time
               long start2 = System.currentTimeMillis();
               // N runs
               for (int j=0; j<=runs; j++){
                   preprocessing.preprocessing(name, "PAIR", i);
               }
               // Record the end time
               long end2 = System.currentTimeMillis();

               // Print time
               System.out.println("Sorting Algo : "+ name +" , Elapsed Time in milli seconds to run N : "+ i +" , Time : " + (end2-start2)/runs+" ms");

           }
       }
   }
}
