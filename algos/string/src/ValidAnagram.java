//Given two strings s and t, write a function to determine if t is an anagram of s.

//For example,
//s = "anagram", t = "nagaram", return true.
//s = "rat", t = "car", return false.

//Note:
//You may assume the string contains only lowercase alphabets.
//Source: https://leetcode.com/problems/valid-anagram/
public class Solution {
    public boolean isAnagram(String s, String t) {
        int lenS = s.length();
        int lenT = t.length();
        //two anagrams must have the same length
        if (lenS != lenT)
           return false;
          
        //lenS == lenT
        int countA[] = new int[26]; //count the frequencies of each letter
        int countB[] = new int[26];
        int i = 0;
        while (i != lenS) {
            countA[s.charAt(i)-'a']++;
            countB[t.charAt(i)-'a']++;
            i++;
        }
       
        for (int j = 0; j < 26; j++)
            if (countA[j] != countB[j]) //if the frequence does not match
               return false;
              
        return true;
    }
}
