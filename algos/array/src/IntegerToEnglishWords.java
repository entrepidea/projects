////source: https://leetcode.com/problems/integer-to-english-words/
//
//Convert a non-negative integer to its english words representation. Given input is guaranteed to be less than 231 - 1.
//
//For example,
//
//123 -> "One Hundred Twenty Three"
//12345 -> "Twelve Thousand Three Hundred Forty Five"
//1234567 -> "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"

//key ideas:
//1. divide integer into roots: 0, [1, 19], 20, 30, 40, 50, 60, 70, 80, 90
//2. consider how to get [0,99].
//3. divide integer by three-digit sub-group.
//4. use recursion to handle each three-digit subgroup.
public class Solution {
    public String numberToWords(int num) {
        //The following two groups are like the word-root for counting.
        String[] one2Nineteen = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
                    "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
        String[] twenty2Ninety = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
              
        if (num == 0)
           return "Zero";
        else if (num < 20) //1-19
                return one2Nineteen[num];
             else if (num < 100) //[20, 99]   
                     return twenty2Ninety[num/10] + (num%10 == 0? "" : " " + one2Nineteen[num%10]); 
                            //if num%10 == 0, then one2Nineteen[0] returns "".
                            //The (num%10 == 0 ? "" : " " + one2Nineteen[num%10]) is needed,
                            //or we would have gotten "Twenty " instead of "Twenty".
                  else if (num < 1000) //[100, 999]
                          return one2Nineteen[num/100] + " Hundred" + (num%100 == 0 ? "" : " " + numberToWords(num%100)); 
                                 //when num is in [100,999], num/100 is in [1, 9]
                       else if (num < 1000000) //[1,000, 999,999]
                               return numberToWords(num/1000) + " Thousand" + 
                                      (num%1000 == 0 ? "" : " " + numberToWords(num%1000));
                                      //when num is in [1000, 999999], num/1000 is [1, 999],
                                      //so we need to call numberToWords(num/1000) for the thousand-group.
                            else if (num < 1000000000) //[1,000,000, 999,999,999]
                                    return numberToWords(num/1000000) + " Million" + 
                                           (num%1000000 == 0 ? "" : " " + numberToWords(num%1000000));
                                 else //[1,000,000,000, 2,147,483,647]
                                      return one2Nineteen[num/1000000000] + " Billion" + 
                                             (num%1000000000 == 0 ? "" : " " + numberToWords(num%1000000000));
                                             //for the given number, the billion digit can only be 1 or 2,
                                             //so we use one2Nineteen[num/1000000000].
    }
}

