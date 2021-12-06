## INFO6205-MSDSort-final-project

![example](https://img.shields.io/badge/java-v1.8.0+-green.svg)

### Abstract
Implement MSD radix sort for a Chinese natural language which uses Unicode characters.
Additionally, completed literature survey of relevant papers on MSD Radix sort and compared result with Timsort, Dual-pivot
Quicksort, Huskysort, and LSD radix sort.

We All Algorithms using two methods :-
* Array of String 
* External class that stores the chinese words and its pinyin equivalent

### Paper and reports

* **PAPER - 1**: [MSD radix sort algorithm](https://github.com/apoorvak1997/INFO6205_FinalProject/blob/main/Papers:Report/Literature_Survey_Paper_1.docx)

* **PAPER - 2**: [MSD radix sort algorithm](https://github.com/apoorvak1997/INFO6205_FinalProject/blob/main/Papers:Report/Literature_Survey_Paper_2.docx)

* **REPORT**: [Final Project Report – Team 6](link)

* **SortedChinese Output File**: [sortedChinese.txt](https://github.com/apoorvak1997/INFO6205_FinalProject/blob/main/MSDRadixSort/src/main/resource/sortedChinese.txt)

### How to use

**1. Setup 1.8+ SDK**

**2. Install pinyin4j**

* Find the /pinyin4j-2.5.0.jar file in the root directory of the project.
* Go to your project structure and add jar to dependencies as a external libary

##### Main program

* [**Timer.java**](): The benchmark class for all algorithms.

* [**Preprocessing.java**](): The preprocessing class for all algorithms which performs read, convert to pinyin, sort and writes sorted output to file.

##### Algorithms Implemented Using Array of String

* Default Size for running sorts is 1 Million

*[**LSDStringSort**](): Implements sorting of chinese character using Array of string using LSD Radix sort

*[**MSDRadixSort_Three**](): Implements sorting of chinese character using Array of string using MSD Radix sort

*[**DualPivotQuickSortStrings**](): Implements sorting of chinese character using Array of string using Dual Pivot String Radix sort

*[**HuskySort**](): Implements sorting of chinese character using Array of string using Husky Sort

##### Algorithms Implemented Using External class that stores chinese word and its equivalent pinyin
*[**DualPivotQuickSort**](): Implements sorting of chinese character by taking input as  External class which maps chinese character to its equivalent pinyin using Dual Pivot Quick Sort

*[**MSDRadixSort**](): Implements sorting of chinese character by taking input as  External class which maps chinese character to its equivalent pinyin using MSD Radix Sort

*[**LSDStringSortChineseToPinyin**](): Implements sorting of chinese character by taking input as  External class which maps chinese character to its equivalent pinyin using LSD Radix Sort

##### Unit tests

* DualPivotQuickSortStringsTest.java: The QuickSortDualPivot String unit test file
* DualPivotQuickSortTest.java: The QuickSortDualPivot External class unit test file
* LSDRadixSortStringsTest.java: The LSDRadix Sort String unit test file
* LSDRadixSortTest.java: The LSDRadix Sort External class unit test file
* MSDRadixSortStringsTest.java: The MSDRadix Sort String unit test file
* MSDRadixSortTest.java: The MSDRadix Sort String unit test file

##### Resource files

* [Resources folder](https://github.com/apoorvak1997/INFO6205_FinalProject/tree/main/MSDRadixSort/src/main/resource), which includes:
** shuffledChinese.txt which contains 400K chinese names:- It has one million names and after that it has copies of 1M x 4. We simply change values of N in order to run the code for different test cases 
** sortedChinese.txt which contains sorted chinese names

##### Contributors:
Kethamaranahalli, Apoorva
Satbhai Abhishek Ravindra
Sneha Ravichandran

