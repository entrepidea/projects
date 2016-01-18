//Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.
//
//For example,
//Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
//
//Window position                Max
//---------------               -----
//[1  3  -1] -3  5  3  6  7       3
// 1 [3  -1  -3] 5  3  6  7       3
// 1  3 [-1  -3  5] 3  6  7       5
// 1  3  -1 [-3  5  3] 6  7       5
// 1  3  -1  -3 [5  3  6] 7       6
// 1  3  -1  -3  5 [3  6  7]      7
//
//Therefore, return the max sliding window as [3,3,5,5,6,7].
//
//Note:
//You may assume k is always valid, ie: 1 ≤ k ≤ input array's size for non-empty array.
//
//Follow up:
//Could you solve it in linear time?
//
//Source: https://leetcode.com/problems/sliding-window-maximum/

//Key ideas:
//1. Maintain a linked list of (index, value) pairs:
//   a. the values in the list are decreasing but the indices are increasing
//      Intuitively, a smaller element can be added to the list only if
//      it has higher index (this means this "newer" element 
//      has a chance to become the maximum once we move towards right).
//   a. the value of the first element is the maximum 
//      in the current sliding window
//   b. once we see an element, call it a new comer, do the following:
//      I. if the new comer is >= an element in the list,
//      replace that the element with the newer comer's index and value,
//      delete all the rest of the list since
//      we now have a new comer with bigger values.
//      II. if the new comer is < an element in the list,
//          move to the next element in the list.
//   c. Remember to remove element that is out of the current sliding window.     
public class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int len = nums.length;
        //There are (len-k+1) sliding window of size-k in an array of size len
        int numWindows = len - k +1;
        int[] vals = new int[numWindows];
        
        //Find the maximum number in the first slide window.
        if (len == 0)
           return (new int[0]); 
           //Warning: cannot write return vals;
           //That statement fails case like [], 0,
           //The return should be []. 
           //However, with return vals,
           //the return is [0], wrong answer.
        
        LinkedList<Pair> lst = new LinkedList<Pair>();
        lst.add(new Pair(0, nums[0]));
        
        for (int i = 1; i < k; i++)
            update(lst, i, nums[i]);
        //After the above for-statement, lst has at most k pairs.
        //lst can have k pairs only if the values of 
        //the first k elements of nums is strictly decreasing.
        //
        //In the case where there is at least one redundant element,
        //lst will only remember the element with the bigger index,
        //hence lst has fewer than k elements.
               
        vals[0] = lst.get(0).val;
        for (int i = 1; i < numWindows; i++) {
            //eliminate element that is out of the window
            //Since we slide the window one element towards the right,
            //only one element is added,
            //thus only one element MAYBE pushed out of the window,
            //since lst has at most k elements.
            //TODO: can we give a better explanation?
            if (lst.get(0).idx < i)
               lst.remove(0);
               
            update(lst, i+k-1, nums[i+k-1]);
            vals[i] = lst.get(0).val;
        }
        
        return vals;
    }
    
    void update(LinkedList<Pair> lst, int idx, int val) {
        int size = lst.size();    
        Pair curr;
        int i = 0;
        while (i < size) {
            curr = lst.get(i);
            if (curr.val <= val) {
                //replace the current item in lst by (idx, val)
                //delete the remaining elms in the list
                lst.set(i, new Pair(idx, val));
                
                //all the elements in [i+1, size-1] needs to be deleted
                //There are a total of (size-1-(i+1)+1) = size-i-1 elements.
                //Note that once we delete the element indexed at i+1,
                //the succeeding element is indexed at i+1 also.  
                for (int j = 0; j < size-i-1; j++)
                    lst.remove(i+1);
                  
                return;  
            }
            else i++; //move to the next item
        }
        
        //val is smaller than all the elements in lst, add (idx, val) to the end
        lst.add(new Pair(idx, val));
    }
}

class Pair {
    int idx;
    int val;
    Pair(int idx, int val) {this.idx = idx; this.val = val;}
}
