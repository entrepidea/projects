//Given a list of non negative integers, arrange them such that they form the largest number.

//For example, given [3, 30, 34, 5, 9], the largest formed number is 9534330.

//Note: The result may be very large, so you need to return a string instead of an integer.

//Credits:
//Special thanks to @ts for adding this problem and creating all test cases.

//source: https://leetcode.com/problems/largest-number/

//keys: 
//sort the number by the most significant digit,
//for example, 35 < 92 < 9, 2 < 25
//1. "" + 92 + 9 is ahead of "" + 9 + 92 in dictionary order.
//   but "" + 2 + 25 < "" + 25 + 2
//   Use concatenation + and compareTo method of String library.
//2. get rid of proceeding zeros.

public class Solution {
    public String largestNumber(int[] nums) {
        mergeSort(nums, 0, nums.length-1);
        return getLargestNumber(nums);
    }
    
    String getLargestNumber(int nums[]) {
        String str = "";
        for (int i = nums.length-1; i >= 0; i--)
            str += nums[i];
            
        if (str.length() > 0 && str.charAt(0) != '0')
           return str;
           
        //first letter is '0'   
        //consider the case of [0,0], which should return "0",
        //not "00"
        
        //all 0s?
        boolean allZeros = true;
        int firstNonZeroIdx = -1;
        for (int i = 0; i < str.length(); i++)
            if (str.charAt(i) != '0') {
                allZeros = false;
                firstNonZeroIdx = i;
                break;
            }
        
        if (allZeros == true)
           return "0";
        else return str.substring(firstNonZeroIdx);
    }
    
    void mergeSort(int[] nums, int start, int end) {
        if (start >= end)
           return;
           
        //start < end
        int mid = (start + end)/2;
        mergeSort(nums, start, mid);
        mergeSort(nums, mid+1, end);
        merge(nums, start, end);
    }
    
    void merge(int nums[], int start, int end) {
        int temp[] = new int[end-start+1];
        int i = start;
        int mid = (start + end)/2;
        int j = mid+1;
        int k = 0;
        int msdI, msdJ;
        String strI, strJ;
        while (i <= mid && j <= end) {
            strI = "" + nums[i] + nums[j];
            strJ = "" + nums[j] + nums[i];
            if (strI.compareTo(strJ) > 0) {//strJ is ahead of strI in alphabetic order
               temp[k] = nums[j];
               k++;
               j++;
            }
            else if (strI.equals(strJ)) {
                    temp[k] = nums[i];
                    temp[k+1] = nums[j];
                    k += 2;
                    i++;
                    j++;
            }
            else {
                  temp[k] = nums[i];
                  k++;
                  i++;
            }
        }
        
        while (i <= mid) {
            temp[k] = nums[i];
            k++;
            i++;
        }
        
        while (j <= end) {
            temp[k] = nums[j];
            k++;
            j++;
        }
        
        i = start;
        k = 0;
        while (i <= end) {
            nums[i] = temp[k];
            i++;
            k++;
        }
    }
}
