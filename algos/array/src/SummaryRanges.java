//source: https://leetcode.com/problems/summary-ranges/

//Given a sorted integer array without duplicates, return the summary of its ranges.

//For example, given [0,1,2,4,5,7], return ["0->2","4->5","7"].

//Credits:
//Special thanks to @jianchao.li.fighter for adding this problem and creating all test cases.

public class Solution {
    public List<String> summaryRanges(int[] nums) {
        LinkedList<String> lst = new LinkedList<String>();
        int len = nums.length;
        if (len == 0) //needed, or index out of range for []
           return lst;
           
        int i = 0;
        int start;
        int end;
        String str = "";
        
        while (i < len) {
            start = nums[i];
            
            //find a range
            while (i+1 < len && nums[i+1] == nums[i]+1)
                  i++;
                  
            end = nums[i];
            if (start != end)
               str = start + "->" + end;
            else str = start + "";
            
            lst.add(str);
               
            //continue for next range
            i++;
        }
        
        return lst;
    }
}
