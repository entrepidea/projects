package com.entrepidea.algo.tests.list;

/**
*
* @Desc: this problem appears in Leetcode and a bunch of interview questions, as shown below
* MOrgan Stanley Interview 07-30-13, 2:30 with James Lin
  TODO 1. How to reverse a single linked list, what's its big O notation;
* Citi onsite (Jersey City) 08/20/13, from 09AM to 11:30AM.
 TODO reverse linked list best performance. The recursive way to reverse the linked list wouldn't be more performant, but he suggested break the linkedlist in the middle before using recursion. I don't know if it's a viable solution, not tried yet.
 @Source: https://leetcode.com/problems/reverse-linked-list/
*
*/

import com.entrepidea.algo.tests.list.support.LinkedListUtils;
import com.entrepidea.algo.tests.list.support.ListNode;
import org.junit.Test;
import com.entrepidea.algo.tests.list.support.LinkedListUtils.*;

public class LE206ReverseList {


    LinkedListUtils llu = LinkedListUtils.getInstance();

    //recursive version
    //the method below is NOT my code
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

    @Test
	public void testReverseList1(){
        ListNode head = llu.constructList(10);

        System.out.println("The original linked list");
        llu.printList(head);

        ListNode reversed = reverseListR(head);
        System.out.println("The reversed linked list");
        llu.printList(reversed);
	}


	//the method below is my own code
    private ListNode reverseListR2(ListNode head){
        if(head.next==null){
            return head;
        }
        if(head.next.next==null){
            ListNode ret = head.next;
            ListNode p = head.next;
            p.next = head;
            head.next = null;
            return ret;
        }
        else{
            ListNode temp = reverseListR2(head.next);
            ListNode newHead = temp;
            while(temp.next!=null){
                temp = temp.next;
            }
            head.next = null;
            temp.next = head;
            return newHead;
        }
    }
    @Test
    public void testReverseList2(){
        ListNode head = llu.constructList(30);
        System.out.println("original linked list");
        llu.printList(head);
        ListNode newHead = reverseListR2(head);
        System.out.println("reversed linked list");
        llu.printList(newHead);
    }


    //non-recursive version
    //The method below is NOT mine
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


    @Test
    public void testReverseList3(){
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);

        System.out.println("The original linked list");
        llu.printList(head);

        ListNode reversed = reverseList(head);
        System.out.println("The reversed linked list");
        llu.printList(reversed);

    }


    //the method below is my OWN code
    //the idea is to find the last node and the node right before it, then reverse the link;
    // then set the pointer of the prev node to null, the null will then be used as the condition to find the new last node, and iterate the process.
    private ListNode reverseList2(ListNode head){
        ListNode p = head;
        if(p.next==null){
            return p;
        }
        ListNode newHead = llu.findLastNode(head);
        ListNode prevNode = null;
        ListNode lastNode = null;
        while(lastNode!=head) {
            while (p.next != null) {
                if (p.next.next == null) {
                    prevNode = p;
                }
                p = p.next;
            }
            lastNode = p;
            lastNode.next = prevNode;
            prevNode.next = null;
            p = head;
        }
        return newHead;
    }


    @Test
    public void testReverseList4(){
        ListNode head = llu.constructList(30);
        System.out.println("Original linked list:");
        llu.printList(head);
        ListNode newHead = reverseList2(head);
        System.out.println("Reversed Linked list:");
        llu.printList(newHead);
    }



    //redo 02/23/19, recursive version
    ListNode reverseList3(ListNode head){
        if(head == null || head.next == null){
            return head;
        }

        ListNode newHead = reverseList3(head.next);
        ListNode temp = newHead;
        while(temp.next!=null){
            temp  = temp.next;
        }
        temp.next = head;
        head.next = null;

        return newHead;

    }
    @Test
    public void testReverseList5(){
        ListNode head = llu.constructList(10);
        llu.printList(head);
        ListNode newHead = reverseList3(head);
        llu.printList(newHead);
    }

    //redo, 02/25/19, non-recursive version
    ListNode reverseList4(ListNode head){
        if(head==null || head.next==null){
            return head;
        }
        ListNode cur = head;

        while (cur.next != null) {
            cur = cur.next;
        }

        ListNode newHead = cur;
        cur = head;
        ListNode pre = null;

        while(head.next!=null) {
            while (cur.next != null) {
                pre = cur;
                cur = cur.next;
            }
            cur.next = pre;
            pre.next = null;
            cur = head;
            pre = null;
        }

        return newHead;
    }

    @Test
    public void testReverseList6(){
        ListNode head = llu.constructRandomNumberList(10,100);
        llu.printList(head);
        ListNode newHead = reverseList4(head);
        llu.printList(newHead);
    }


    //this is just a memory refresh on 11/06/19
    private ListNode reverseList7(ListNode head){
        if(head==null || head.next==null){
            return head;
        }
        ListNode temp = head;
        ListNode newHead = reverseList7(head.next);
        ListNode p = newHead;
        while(p.next!=null){
            p = p.next;
        }
        p.next = temp;
        temp.next = null;
        return newHead;
    }
    @Test
    public void testReverseList7(){
        ListNode head = llu.constructListFromArray(new int[]{1,2,3,4,5});
        llu.printList(head);

        //try using recursion for this.
        llu.printList(reverseList7(head));

    }




}







