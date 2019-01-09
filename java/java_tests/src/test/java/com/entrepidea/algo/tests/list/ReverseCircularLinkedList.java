package com.entrepidea.algo.tests.list;

/**
 * desc: reverse a circular linked list
 * source: https://www.geeksforgeeks.org/reverse-circular-linked-list/
 * date: 01/01/2019
 * */

import org.junit.Test;

import static com.entrepidea.algo.tests.list.LinkedListUtils.ListNode;

public class ReverseCircularLinkedList {
    LinkedListUtils llu = LinkedListUtils.getInstance();

    @Test
    public void test(){
        ListNode head = llu.constructListFromArray(new int[]{1,2,3,4});
        ListNode p = head;
        while(p.next!=null){
            p = p.next;
        }
        p.next = head; //now the linked list is a circular list.

        //print list
        System.out.println("original circular list:");
        p = head;
        while(p.next!=head){
            System.out.print(p.val);
            System.out.print(",");
            p = p.next;
        }
        System.out.println(p.val);

        p = head.next;
        ListNode prev = head;

        while (p.next != head) {
            p = p.next;
            prev = prev.next;
        }
        ListNode newHead = p;
        p.next = prev;
        prev.next = null;

        while(head.next!=null) {
            p = head.next;
            prev = head;
            while(p.next!=null){
                p = p.next;
                prev = prev.next;
            }
            p.next = prev;
            prev.next = null;
        }
        head.next = newHead;

        System.out.println("reversed circular list:");
        p = newHead;
        while(p.next!=newHead){
            System.out.print(p.val);
            System.out.print(",");
            p = p.next;
        }
        System.out.println(p.val);
    }
}
