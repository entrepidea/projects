package com.entrepidea.algo.tests.list;

import org.junit.Test;

/**
 * source: https://www.geeksforgeeks.org/write-a-c-function-to-print-the-middle-of-the-linked-list/
 * method2 and method3 are interesting solutions.
 * */
public class FindMiddleElement {

    LinkedListUtils llu = LinkedListUtils.getInstance();

    @Test
    public void test(){
        LinkedListUtils.ListNode head = llu.constructList(10);
        llu.printList(head);
        LinkedListUtils.ListNode fastP = head;
        LinkedListUtils.ListNode slowP = head;

        while(fastP!=null && fastP.next!=null){
            fastP = fastP.next.next;
            slowP = slowP.next;
        }
        System.out.println(slowP.val);
    }


    @Test
    public void test2(){
        LinkedListUtils.ListNode head = llu.constructList(10);
        llu.printList(head);
        LinkedListUtils.ListNode mid = head;
        int count = 0;
        while(head!=null){
            if(count%2!=0){
                mid = mid.next;
            }
            count++;
            head = head.next;
        }

        System.out.println(mid.val);
    }
}
