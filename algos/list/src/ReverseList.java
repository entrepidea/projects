//Reverse a singly linked list

public class ReverseList{
	public static void main(String[] args){
		//put code here. Use recursive 
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);

        System.out.println("The original linked lsit");
        print(head);

        ListNode reversed = reverseListR(head);
        System.out.println("The reversed linked lsit");
        print(reversed);
	}

    //recursive version
    public static ListNode reverseListR(ListNode head) {
        if (head == null || head.next == null)
           return head;
           
        ListNode prev = null;
        ListNode curr = head;
        while (curr.next != null) {
              prev = curr;
              curr = curr.next;
        }
         
        prev.next = null;    
        //curr is the tail element
        ListNode head2 = reverseListR(head);
        curr.next = head2;
        return curr;
    }

    //non-recursive version
    public static ListNode reverseList(ListNode head) {
        ListNode curr = null;
        ListNode succ = head;
        ListNode tmp;
        while (succ != null) {
            tmp = succ.next;
            succ.next = curr;
            curr = succ;
            succ = tmp;
        }
        //The above while-loop handles the case
        //when a list has zero or one element.
        
        return curr;
    }

    public static void print(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
              System.out.print(curr.val + " ");
              curr = curr.next;
        }
        System.out.println();
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}
