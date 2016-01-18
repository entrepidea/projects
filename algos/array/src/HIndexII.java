//source: https://leetcode.com/problems/h-index/

//Given an array of citations (each citation is a non-negative integer) of 
//a researcher, write a function to compute the researcher's h-index.

//According to the definition of h-index on Wikipedia: 
//"A scientist has index h if h of his/her N papers have at least h citations each,
// and the other N − h papers have no more than h citations each."

//For example, given citations = [3, 0, 6, 1, 5], 
//which means the researcher has 5 papers in total and 
//each of them had received 3, 0, 6, 1, 5 citations respectively. 
//Since the researcher has 3 papers with at least 3 citations each 
//and the remaining two with no more than 3 citations each, his h-index is 3.

//Note: If there are several possible values for h, 
//the maximum one is taken as the h-index.

//Credits:
//Special thanks to @jianchao.li.fighter for adding this problem and 
//creating all test cases.

//key idea: understand the concept of hIndex.
//Identify the conditions of being an hIndex and corner cases.

//Follow up for H-Index: What if the citations array is sorted in ascending order? 
//Could you optimize your algorithm?

public class Solution {
    public int hIndex(int[] citations) {
        int len = citations.length;
        for (int i = len-1; i >= 0; i--)
            if (citations[i] >= len-i && (i-1 < 0 || citations[i-1] < len-i+1))
               return len-i;
               
        return 0;
    }
}
