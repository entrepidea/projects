package com.entrepidea.algo.pattern.sliding_window;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Desc: find the longest substring from a string and the substring contains the required number of unique characters.
 * @Source: https://www.geeksforgeeks.org/find-the-longest-substring-with-k-unique-characters-in-a-given-string/
 * @Note: I created the algorithm, tests seemed good so far.
 * @Date: 12/06/19
 * */
public class LongestSubstringWithUniqK {

    private int longestSubString(final String str, int uniqueNum){
        char[] charArr = str.toCharArray();
        int maxLen = 0;

        for(int i=0;i<charArr.length-uniqueNum;i++){
            int[] alphaTab = new int[26];
            alphaTab[charArr[i] -'a']=1;
            int uniqueFoundCnt = 1;
            for(int j=i+1;j<charArr.length;j++){
                if(alphaTab[charArr[j]-'a']==0){ //found a different one
                    //check if required unique number is reached
                    if(uniqueFoundCnt==uniqueNum){
                        int len = 0;
                        for(int k=0;k<alphaTab.length;k++){
                            if(alphaTab[k]!=0){
                                len+=alphaTab[k];
                            }
                        }
                        if(len>maxLen){
                            maxLen = len;
                        }
                        break;
                    }
                    uniqueFoundCnt++;
                }
                alphaTab[charArr[j]-'a']++;
            }
            //border condition checking.
            if(uniqueFoundCnt==uniqueNum){
                int len = 0;
                for(int k=0;k<alphaTab.length;k++){
                    if(alphaTab[k]!=0){
                        len+=alphaTab[k];
                    }
                }
                if(len>maxLen){
                    maxLen = len;
                }
            }
        }
        return maxLen;
    }
    @Test
    public void test(){
        Assert.assertEquals(2,longestSubString("aabbcc",1));
        Assert.assertEquals(4,longestSubString("aabbcc",2));
        Assert.assertEquals(6,longestSubString("aabbcc",3));
        Assert.assertEquals(5,longestSubString("aabbc",3));
        Assert.assertEquals(5,longestSubString("abbcc",3));
        Assert.assertEquals(7,longestSubString("cabbccd",4));
        Assert.assertEquals(8,longestSubString("eaabbccd",5));
    }
}
