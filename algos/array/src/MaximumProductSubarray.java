//source: https://leetcode.com/problems/maximum-product-subarray/

//maximum product subarray

//Find the contiguous subarray within an array (containing at least one number) 
//which has the largest product.

//For example, given the array [2,3,-2,4],
//the contiguous subarray [2,3] has the largest product = 6.

public class Solution {
    public int maxProduct(int[] nums) {
        int len = nums.length;
        
        //The array is assumed to have at least one element
        if (len == 1)
           return nums[0];
           
        //len > 1: there are more than one element in the array
        //key idea: use two intermediate arrays pos and neg
        //max[i] records the maximum value ended at nums[i],
        //note that max[i] can be positive, negative or zero. 
        //min[i] records the minimum value ended at nums[i],
        //note that min[i] can be positive, negative or zero. 
        int[] max = new int[len];
        int[] min = new int[len];
        max[0] = min[0] = nums[0];
        for (int i = 1; i < len; i++) {
            if (nums[i] >= 0) {
               max[i] = Math.max(nums[i] * max[i-1], nums[i]); 
               //max[i-1] can be negative, 
               //for example, given [-2, 3], when i = 1, max[0] = -2.  
               min[i] = Math.min(nums[i] * min[i-1], nums[i]); 
               //min[i-1] can be positive, 
               //for example, given [3, -2], when i = 1, min[0] = 3.
            }
            else { //nums[i] < 0
                   max[i] = Math.max(nums[i] * min[i-1], nums[i]);
                   min[i] = Math.min(nums[i] * max[i-1], nums[i]);
            }
        }
        
        //find out the maximum product of any subarray, 
        //it must be the maximum of array max.
        int val = max[0];
        for (int i = 1; i < len; i++)
            if (val < max[i])
               val = max[i];
               
        return val;
    }
}
