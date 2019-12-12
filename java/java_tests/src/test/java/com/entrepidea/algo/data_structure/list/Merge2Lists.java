package com.entrepidea.algo.data_structure.list;

import com.entrepidea.algo.data_structure.list.support.LinkedListUtils;
import com.entrepidea.algo.data_structure.list.support.ListNode;
import org.junit.Test;

/**
 *Merge a linked list into another linked list at alternate positions
 * source: https://www.geeksforgeeks.org/merge-a-linked-list-into-another-linked-list-at-alternate-positions/
 * date: 12/20/18
 * */
public class Merge2Lists {

    LinkedListUtils llu = LinkedListUtils.getInstance();
    @Test
    public void test(){
        ListNode l1 = llu.constructListFromArray(new int[]{5,7,17,13,11,17,9,23,91 });
        ListNode l2 = llu.constructListFromArray(new int[]{12,10,2,4,6});

        ListNode l = new ListNode(0);
        ListNode p = l;
        while(l1.next!=null && l2.next!=null){
            p.next = l1;
            l1 = l1.next;
            p = p.next;
            p.next = l2;
            l2 = l2.next;
            p = p.next;
        }
        if(l1.next!=null){
            p.next = l1.next;
        }
        if(l2.next!=null){
            p.next = l2.next;
        }

        llu.printList(l.next);
    }
}
