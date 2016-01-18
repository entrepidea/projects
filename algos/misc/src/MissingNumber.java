//Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, 
//find the one that is missing from the array.

//For example, Given nums = [0, 1, 3] return 2.

//Note:
//Your algorithm should run in linear runtime complexity. 
//Could you implement it using only constant extra space complexity?

//Credits:
//Special thanks to @jianchao.li.fighter for adding this problem and 
//creating all test cases.

public class Solution {
    //Considering the fact that 1+2+...+n = n*(n+1)/2,
    //use the above value minus actual sum,
    //we get the missing number.
    //In fact, there might be some tricky things to be tested,
    //for example, n*(n+1)/2 can be too big to be held by an integer
    //when n is big.
    public int missingNumber(int[] nums) {
        int n = nums.length;
        long sum = 0;
        for (int i = 0; i < n; i++)
            sum += nums[i];
            
        return (int)(n*(n+1)/2 - sum);
    }
}
