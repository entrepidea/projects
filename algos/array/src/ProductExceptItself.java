//Given an array of n integers where n > 1, nums, 
//return an array output such that output[i] is equal to the product of 
//all the elements of nums except nums[i].
//Solve it without division and in O(n).
//
//For example, given [1,2,3,4], return [24,12,8,6].
//
//Follow up:
//Could you solve it with constant space complexity? 
//(Note: The output array does not count as extra space 
//for the purpose of space complexity analysis.)
//
//Key: how to write division without /. 
//Need to consider the case of -(-2^31) = 2^31 and thus over the limit of integer. 
//So we need to use long. Then we need to translate the result back to integer. 
//Just mimic the process of integer division by aligning divisor, 
//then find out the biggest quotient corresponding to that aligned divisor.
//Also note that product of integers might overflow the limit of int defined in Java.
//So if (dividend * divisor < 0)
//      return -divide(Math.abs(dividend), Math.abs(divisor));
//   may result in stack overflow exception in recursion since 
//   1. dividend * divisor might overflow
//   2. Also, Math.abs(dividend) might overflow if dividend is defined as integer.
//   Similarly, -dividend also might overflow if dividend is defined as an integer.
public class Solution {
    public int[] productExceptSelf(int[] nums) {
        int len = nums.length;
        
        if (len == 0)
           return null; //no array can have length of zero.
           
        int numOnes = getNumOnes(nums);
        int product[] = new int[len];
        
        int nonZeroIndex = 0;
        int prod = 1;
        if (numOnes == 1) {
            for (int i = 0; i < len; i++) {
                if (nums[i] != 0) {
                   product[i] = 0;
                   prod *= nums[i];
                }
                else nonZeroIndex = i;
            }
            
            product[nonZeroIndex] = prod;
            return product;
        }
        else if (numOnes >= 2)
                return product; 
                
        //no element is zero in the array
        for (int i = 1; i < len; i++)
            prod *= nums[i];
            
        product[0] = prod;
        for (int i = 1; i < len; i++)
            product[i] = divide(prod*nums[0], nums[i]);
        
        return product;
    }
    
    int getNumOnes(int[] nums) {
        int numZeros = 0;
        for (int i = 0; i < nums.length; i++)
            if (nums[i] == 0)
               numZeros++;
               
        return numZeros;
    }
    
    /*
    int divide(int dividend, int divisor) {
        //Warning: do not write 
        //if (dividend * divisor < 0)
        //   return -divide(Math.abs(dividend), Math.abs(divisor));
        //Reason: dividend * divisor might be too big to correctly get the sign of their product correct,
        //hence resulting in stack overflow exception
        
        if (dividend < 0 && divisor > 0)
           return -divide(-dividend, divisor);
           //warning: if dividend is too negative like -2^31, then -dividend can over the limit of positive integer
        else if (dividend > 0 && divisor < 0)
                return -divide(dividend, -divisor);
             else if (dividend < 0 && divisor < 0)
                     return divide(-dividend, -divisor);
           
        //dividend >= 0 && divisor > 0
        if (dividend < divisor)
           return 0;
           
        if (divisor == 1)
           return dividend;
           
        //left align divisor until it is larger than dividend
        int quotient = 1;
        int start = divisor;
        while (start*10 <= dividend) {
            start *= 10;
            quotient *= 10;
        }
        //start < dividend but start*10 > dividend
        
        //find the quotient corresponds to that left-aligned digit.
        int start2 = start;
        int quotient2 = quotient;
        while (start2 + start <= dividend) {
            start2 += start;
            quotient2 += quotient; 
        }
        
        return quotient2 + divide(dividend - start2, divisor);
    }
    */
    
    int divide(long dividend, long divisor) {
        //Warning: do not write 
        //if (dividend * divisor < 0)
        //   return -divide(Math.abs(dividend), Math.abs(divisor));
        //Reason: dividend * divisor might be too big to correctly get the sign of their product correct,
        //hence resulting in stack overflow exception
        
        if (dividend < 0 && divisor > 0)
           return -divide(-dividend, divisor);
           //warning: if dividend is too negative like -2^31, then -dividend can over the limit of positive integer
        else if (dividend > 0 && divisor < 0)
                return -divide(dividend, -divisor);
             else if (dividend < 0 && divisor < 0)
                     return divide(-dividend, -divisor);
           
        //dividend >= 0 && divisor > 0
        if (dividend < divisor)
           return 0;
           
        if (divisor == 1)
           return (int)dividend;
           
        //left align divisor until it is larger than dividend
        int quotient = 1;
        long start = divisor;
        while (start*10 <= dividend) {
            start *= 10;
            quotient *= 10;
        }
        //start < dividend but start*10 > dividend
        
        //find the quotient corresponds to that left-aligned digit.
        long start2 = start;
        long quotient2 = quotient;
        while (start2 + start <= dividend) {
            start2 += start;
            quotient2 += quotient; 
        }
        
        return (int)quotient2 + divide(dividend - start2, divisor);
    }
}
