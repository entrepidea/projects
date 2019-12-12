package com.entrepidea.algo.data_structure.list;

import com.entrepidea.algo.data_structure.list.support.LinkedListUtils;
import com.entrepidea.algo.data_structure.list.support.ListNode;
import org.junit.Test;

public class DeleteMidElement {

    LinkedListUtils llu = LinkedListUtils.getInstance();

    @Test
    public void test() {
        ListNode head = llu.constructList(11);
        llu.printList(head);

        ListNode fastMov = head.next.next;
        ListNode slowMov = head.next;
        ListNode prevMid = slowMov;
        while(fastMov!=null && fastMov.next!=null){
            fastMov = fastMov.next.next;
            prevMid = slowMov;
            slowMov = slowMov.next;
        }
        prevMid.next = slowMov.next;

        llu.printList(head);
    }
}
