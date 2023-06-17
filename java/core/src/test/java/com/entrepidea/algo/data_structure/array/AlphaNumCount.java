package com.entrepidea.algo.data_structure.array;

public class AlphaNumCount {
    //1. Read a file, find out the occurrence of the alphanumeric letters, like "122113, Hello world", returns 1:3; 2:2; 3:1; H:1;e:1; l:3; o:2; r:1;d:1
    //Barcap onsite interview, 10/09/14
    //it's tempting to use a map for a solution. Below is another solution that seems more economical.
    public static void main(String... args){
        int[] numArr = new int[10];
        int[] alphaArr = new int[26];
        String testStr = "122113333, Hello world";

        int lowInt = '0' - 0, highInt = '9' -0, lowChar = 'a' - 0, highChar = 'z' - 0;

        char[] chars = testStr.toLowerCase().toCharArray();
        for(char c: chars){
            int tmp = c-0;
            if(tmp>=lowInt && tmp<=highInt){
               numArr[tmp-lowInt]++;
            }
            else if(tmp>=lowChar && tmp<=highChar){
                alphaArr[tmp-lowChar]++;
            }
        }

        for(int idx=0;idx<numArr.length;idx++){
            if(numArr[idx]!=0) {
                System.out.println((char)(idx+lowInt)+":" + numArr[idx]);
            }
        }
        for(int idx=0;idx<alphaArr.length;idx++){
            if(alphaArr[idx]!=0) {
                System.out.println((char)(idx+lowChar)+":" + alphaArr[idx]);
            }
        }

    }
}
