package com.entrepidea.algo.tests.list;

import org.junit.Test;

/**
 * rotate a linked list from a arbitrary given position.
 * source: https://www.geeksforgeeks.org/rotate-a-linked-list/
 * date: 12/14/18
 * */
import static com.entrepidea.algo.tests.list.LinkedListUtils.ListNode;
public class RotateList {

    LinkedListUtils llu = LinkedListUtils.getInstance();

    @Test
    public void test(){
        ListNode head = llu.constructList(10);
        llu.printList(head);
        ListNode p = head;
        int k = 9;
        int count = 0;
        while(count++<k-1){
            p = p.next;
        }
        ListNode start = p.next;
        p.next = null;
        ListNode newHead = start;
        while(start.next!=null){
            start = start.next;
        }
        start.next = head;
        llu.printList(newHead);
    }
}
