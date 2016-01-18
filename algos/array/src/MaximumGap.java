//Given an unsorted array, find the maximum difference between the successive //elements in its sorted form.

//Try to solve it in linear time/space.

//Return 0 if the array contains less than 2 elements.

//You may assume all elements in the array are non-negative integers and fit in //the 32-bit signed integer range.

//Credits:
//Special thanks to @porker2008 for adding this problem and creating all test //cases.
//key idea: For linear sort algorithm, it naturally uses count sort.
//To make sure that memory consumption is linear also,
//we use bucket sort to get sub-group.
public class Solution {
    public int maximumGap(int[] nums) {
        /*  
        //This approach does not work, will result in out-of-memory exception.
        //Think of the case [0, 100000]
        int len = nums.length;
       
        int vals[] = new int[(int)(Math.pow(2,31))];
       
        for (int i = 0; i < len; i++)
            vals[nums[i]]++;
           
        int maxGap = 0;
        int start = -1;
        for (int j = 0; j < vals.length; j++) {
            if (vals[j] != 0) {
               if (start != -1) {
                  if (maxGap < j - start)
                     maxGap = j - start;
               }
               else start = j;
            }
        }
        */
        
        int len = nums.length;
        if (len < 2) //fewer than two elements in the array
           return 0;
          
        //The only way to sort linearly is bucket sort
        //(key: need to use count sort in underlying sort),
        //which works only with numbers of limited range.
        //Remember the example of sorting one million 32-bit integers?
       
        //We divide an integer by 8-bit as a group,
        //a 32-bit integer has 4 groups.
        //Sort each group, starting from the least significant 8-bit
        //all the way to the most significant 8-bit.
        int sorted[] = new int[len];
        for (int group = 0; group < 4; group++)
            if (group % 2 == 0)
               partialSort(nums, sorted, group);
            else partialSort(sorted, nums, group);
           
        //Now the original array is sorted and the sorted result
        //is in nums, not sorted, a total of four rounds:
        //nums->sorted, sorted->nums, nums->sorted, sorted->nums
        int maxGap = 0;
        int gap;
        for (int i = 1; i < len; i++) {
            gap = nums[i] - nums[i-1];
            if (gap > maxGap)
               maxGap = gap;
        }
       
        return maxGap;  
    }
   
    //group starts from 0 to 3.
    //The least significant 8-bit is group 0.
    //The most significant 8-bit is group 3.
    void partialSort(int[] src, int[] dest, int group) {
        int freq[] = new int[256]; //2^8 = 256, each 8-bit is in [0,255]
        int accuFreq[] = new int[256];
       
        int sig;
        int len = src.length;
        for (int i = 0; i < len; i++) {
            sig = (src[i] >> (group * 8)) & 255;
            freq[sig]++;
        }
       
        //calculate accumulated frequency, this is the key to count sort
        accuFreq[0] = freq[0];
        for (int j = 1; j < 256; j++)
            accuFreq[j] = accuFreq[j-1] + freq[j]; //warning: error-prone
           
        //use accumulated frequency for count sort
        for (int i = len-1; i >= 0; i--) {
            sig = (src[i] >> (group * 8)) & 255;
            dest[accuFreq[sig]-1] = src[i];
            accuFreq[sig]--;
        }
    }
}
