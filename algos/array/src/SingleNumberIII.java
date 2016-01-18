//source: https://leetcode.com/problems/single-number-iii/

/*****
Given an array of numbers nums, in which exactly two elements appear only once and all the other elements appear exactly twice. Find the two elements that appear only once.

For example:

Given nums = [1, 2, 1, 3, 2, 5], return [3, 5].

Note:
The order of the result is not important. So in the above example, [5, 3] is also correct.
Your algorithm should run in linear runtime complexity. Could you implement it using only constant space complexity?
Credits:
Special thanks to @jianchao.li.fighter for adding this problem and creating all test cases.
******/

//key idea: count sort to find which element appears odd numbers.
//It can happen 0x122 0x32 0x122 0x32 0x36 0x56, where 0x means hexadecimal.
//so when we check the first digit, every group appears even numbers 
//(since the elements that appear exactly once -- 36 and 56 -- 
//share the same last digit).
//But some 8-bit group, like 3 (in 0x36 0x32 0x32) and 5 in (0x56), 
//appear odd numbers.
//xor INSIDE that specific group.

//KEY: 1. count sort to find out which subgroup appears odd numbers 
//        (which contains the number that appear exactly once);
//     2. xor the elements share that subgroup to catch a "lone wolf".
public class Solution {
    public int[] singleNumber(int[] nums) {
        int elm;
        int[] count = new int[256]; //cut an integer into 4 8-bit group. 
              //Each group has a value 0-255.
        int[] results = new int[2];
        int k = 0; //kth of element who appears exactly once
        for (int bitShift = 0; bitShift < 32; bitShift += 8) {
            //count the appearance of each 8-bit group
            for (int i = 0; i < nums.length; i++) {
                elm = (nums[i] >> bitShift) & 255;
                count[elm]++;
            }
            
            for (int j = 0; j < count.length; j++) {
                if (count[j] % 2 != 0) { //count[j] appears odd numbers
                   //xor the group whose value is j
                   for (int i = 0; i < nums.length; i++) {
                       elm = (nums[i] >> bitShift) & 255;
                       if (elm == j)
                          results[k] = results[k] ^ nums[i];
                   }
                   //Get results[k] after the for-loop. 
                   //Prepare for the next round by increasing k.
                   k++;
                   
                   if (k >= 2)
                      return results;
                }
            }
        }
        
        return results;
    }
}
