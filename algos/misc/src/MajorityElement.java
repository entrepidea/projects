//Given an array of size n, find the majority element. 
//The majority element is the element that appears more than ⌊ n/2 ⌋ times.


//You may assume that the array is non-empty and the majority element always exist in the array.

//Credits:
//Special thanks to @ts for adding this problem and creating all test cases.

public class Solution {
    public int majorityElement(int[] nums) {
        //map an element to its frequency (number of appearances) in the array
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            if (map.containsKey(nums[i]))
                map.put(nums[i], map.get(nums[i]) +1);
            else map.put(nums[i], 1);
        }
        
        //pay attention: transfer a set to an array 
        Integer[] keys = map.keySet().toArray(new Integer[map.size()]);
        for (int i = 0; i < keys.length; i++) {
            if (map.get(keys[i]) >= len/2.)
               //. after 2 cannot be omitted, or return 2 for [3,2,3]
               return keys[i];
        }
        
        return -1; //this line is needed for compilation purpose
    }
}
