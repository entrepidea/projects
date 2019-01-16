package com.entrepidea.algo.tests.list;

import org.junit.Test;
/**
 * Insert sorting for a linked list
 * TODO there is bug.
 * */
import static com.entrepidea.algo.tests.list.LinkedListUtils.ListNode;
public class InsertSortingLinkedList {

    LinkedListUtils llu = LinkedListUtils.getInstance();

    @Test
    public void test(){
        ListNode head = llu.constructListFromArray(new int[]{27,20,37,64,33,66});

        llu.printList(head);
        ListNode startP = head.next;
        ListNode temp = startP;
        ListNode prev= new ListNode(-1);
        while(startP!=null){
            ListNode p = head;
            prev.next = head;
            while(p!=startP){
                if(p.val>startP.val){
                    temp = startP.next;
                    p.next = temp;
                    startP.next = p;
                    prev.next = startP;
                    break;
                }
                p = p.next;
                prev = prev.next;
            }
            if(p==startP){
                startP = startP.next;
                temp = startP;
            }
            else {
                if(p==head){
                    head = startP;
                }
                startP = temp;
            }
        }

        llu.printList(head);
    }

    @Test
    public void test2(){
        ListNode head = llu.constructListFromArray(new int[]{27,20,37,64,33,66});
        ListNode p = head;

        ListNode newHead = new ListNode(-1);
        ListNode p2 = newHead;
        while(p!=null){
            while(p.val>p2.val && p2.next!=null){
                    p2 = p2.next;
            }
            if(p2.next==null){
                p2.next = p;
            }
            else{
                ListNode temp = p2.next;
                p2.next = p;
                p.next = temp;
            }
            p = p.next;
        }
        llu.printList(newHead);
    }

    @Test
    public void test3(){
        //https://www.geeksforgeeks.org/insertion-sort-for-singly-linked-list/
        //TODO
    }
}
