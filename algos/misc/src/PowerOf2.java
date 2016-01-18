//Given an integer, write a function to determine if it is a power of two.

//Credits:
//Special thanks to @jianchao.li.fighter for adding this problem and creating all test cases.

//Source: https://leetcode.com/problems/power-of-two/

public class Solution {
    public boolean isPowerOfTwo(int n) {
        if (n <= 0)
           return false;
           
        if (n == 1)
           return true;
           
        while (n != 1) { //continue to extract the last bit until only one single bit 1 is met
            if ((n & 1) != 0) //Warning: () around n & 1 is indispensable
               return false; //extract the least significant bit of n
            n >>= 1; //push the previous last significant bit out of n
        }
        
        return true;
    }
}
