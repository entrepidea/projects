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
public class Solution {
    public int hIndex(int[] citations) {
        int len = citations.length;
        if (len == 0)
           return 0;
        //else if (len == 1) 
        //        return citations[0]; //WRONG
        //warning: wrong logic for len == 1. 
        //A counter example is [100]. The expected result is 1, not 100.
                
        //len >= 1, we handle the case of len == 0 first, 
        //otherwise, an error in statement new Integer[len].
        //Sort array in descending order. We want to use Collections.reverseOrder(),
        //But this comparator only works for class type, not primitive type.
        Integer sorted[] = new Integer[len];
        for (int i = 0; i < len; i++)
            sorted[i] = citations[i];
         
        Arrays.sort(sorted, Collections.reverseOrder());
        
        for (int i = 0; i < len; i++)
            citations[i] = sorted[i];
            
        //Now citations are sorted in descending order.
        for (int i = 0; i < len; i++)
            //key to identify hIndex value
            if (citations[i] >= i+1 && (i+1 >= len || citations[i+1] < i+2))
               return i+1;
        
        //When we exit the above for loop, we have
        //citations[i] < i+1 for every element. 
        //Specifically, citations[0] < 1 or citations[0] = 0. 
        //Note that citations are sorted in descending order.
        //So we have cases like [0] or [0, 0]. In this situation, we will return 0.
        return 0;
    }
}
