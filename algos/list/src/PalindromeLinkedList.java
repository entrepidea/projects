//Given a singly linked list, determine if it is a palindrome.

//Follow up:
//Could you do it in O(n) time and O(1) space?

//Linear time complexity and constant space algorithm:
//1. Get the start and middle point by fast-slow-pointer approach.
//2. Reverse the second half linked list.
//3. Compare the first half and second half linked list.
//4. This approach has O(n) complexity and O(1) space.
public class Solution {
    public boolean isPalindrome(ListNode head) {
        //For efficiency reason, handle special case first.
        //list has at most one node.
        if (head == null || head.next == null) 
           return true;
        
        //In the following case, list has >= nodes.
        ListNode mid = getMiddlePoint(head);
        ListNode tail = reverseList(mid);
        
        while (head != null && tail != null) {
            if (head.val != tail.val)
               return false;
            head = head.next;
            tail = tail.next;
        }
        
        return true;
    }
    
    //reverse the linked list pointed by mid point.
    //The size of the original linked list may be odd,
    //hence the first half and last half may have different size.
    ListNode reverseList(ListNode mid) {
        ListNode prev = null;
        ListNode curr = mid;
        ListNode temp;
        while (curr != null) {
            temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        
        return prev;
    }
    
    //getMiddlePoint only calls when head has at least two nodes.
    ListNode getMiddlePoint(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        
        //goal: after one traversal, slow is the middle of the list,
        //while fast is the last node.
        //how-to: every time slow moves one step, 
        //fast moves two steps if possible.
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next;
            if (fast != null && fast.next != null)
               fast = fast.next;
        }
        
        return slow;
    }
}
 
//Linear time complexity and linear space complexity algorithm:
//1. Get the size of the list. 
//2. Save the elements in the list to an array.
//3. Use start and end index moving from both ends towards the middle,
//   stop at the first unmatched start-end pair.
//4. Analysis: O(n) complexity, O(n) space

public class Solution {
    public boolean isPalindrome(ListNode head) {
        int size = getSize(head);
        
        //Even though we do not use recursion,
        //for efficiency reason, handle special case first.
        if (size <= 1) 
           return true;
           
        int[] arr = new int[size];
        ListNode curr = head;
        int currIdx = 0;
        while (curr != null) {
            arr[currIdx] = curr.val;
            currIdx++;
            curr = curr.next;
        }
        
        int start = 0;
        int end = size-1;
        while (start < end) {
            if (arr[start] != arr[end])
               return false;
            start++;
            end--;
        }
        
        return true;
    }
    
    int getSize(ListNode head) {
        int size = 0;
        ListNode curr = head;
        while (curr != null) {
            size++;
            curr = curr.next;
        }
        
        return size;
    }
}
