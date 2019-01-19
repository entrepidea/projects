package com.entrepidea.algo.tests.list;

/**
 * desc: delete dups from a sorted linked list
 * source: https://practice.geeksforgeeks.org/problems/remove-duplicate-element-from-sorted-linked-list/1
 * date: 12/10/18
 * */
import org.junit.Test;
import static com.entrepidea.algo.tests.list.LinkedListUtils.ListNode;
public class DeleteDups {
    LinkedListUtils llu = LinkedListUtils.getInstance();

    @Test
    public void test() {
        //llu.constructList(1);
        int[] arr = {1,2,3,4,5,5,5,5,6,7,8,8,9,10,11,11,11,11,11,12};
        ListNode head =  llu.constructListFromArray(arr);
        llu.printList(head);

        ListNode p = head;

        while(p != null && p.next!=null){
            ListNode n = p.next;
            ListNode start = p ;
            while(n!=null && p.val == n.val){
                n = n.next;
            }
            start.next = n;
            p = n;
        }

        llu.printList(head);
    }
}
