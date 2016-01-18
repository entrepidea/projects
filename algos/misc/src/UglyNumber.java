//Write a program to check whether a given number is an ugly number.
//
//Ugly numbers are positive numbers whose prime factors only include 2, 3, 5. 
//For example, 6, 8 are ugly while 14 is not ugly since it 
//includes another prime factor 7.
//
//Note that 1 is typically treated as an ugly number. 

//source: https://leetcode.com/problems/ugly-number/

public class Solution {
    public boolean isUgly(int num) {
        if (num <= 0)
           return false;
           
        if (num <= 6)
           return true;
           
        if (num % 5 == 0)
           return isUgly(num/5);
        if (num % 3 == 0)
           return isUgly(num/3);
        if (num % 2 == 0)
           return isUgly(num/2);

        //The above code has the same effect as the following.
        //That is due to the effect of return.
        /* 
        if (num % 5 == 0)
           return isUgly(num/5);
        else if (num % 3 == 0)
                return isUgly(num/3);
             else if (num % 2 == 0)
           return isUgly(num/2);
        */
        
        //num does not divide 2, 3, and 5, num is not 1
        return false;
    }
}
