package com.entrepidea.algo.data_structure.list;

import com.entrepidea.algo.data_structure.list.support.LinkedListUtils;
import com.entrepidea.algo.data_structure.list.support.ListNode;
import org.junit.Test;

/**
 * Given a linked list and a arbitray number K, break the list into multiple segments of K elements, reverse the elements in each segment.
 * Source: https://practice.geeksforgeeks.org/problems/reverse-a-linked-list-in-groups-of-given-size/1
 * date: 12/14/18
 * */
public class ReverseListInGroup {

    LinkedListUtils llu = LinkedListUtils.getInstance();


    private ListNode reverseList(ListNode head){
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

    private ListNode catList(ListNode head, ListNode nl){
        if(head==null){
            head = nl;
        }
        else{
            ListNode p = head;
            while(p.next!=null){
                p = p.next;
            }
            p.next = nl;
        }
        return head;
    }

    @Test
    public void test(){
        ListNode head = llu.constructList(9);
        llu.printList(head);
        ListNode p = head;
        int k = 6;
        int count = 0;
        ListNode start = p;
        ListNode newList = null;
        while(p.next!=null){
            p = p.next;
            count++;
            if(count ==k-1) {
                //System.out.println(p.val);
                if(p.next!=null) {
                    ListNode temp = p.next;
                    p.next = null;
                    ListNode newHead = reverseList(start);
                    newList = catList(newList, newHead);
                    start = temp;
                    p = start;
                }
                count = 0;
            }
        }
        ListNode newHead = reverseList(start);
        newList = catList(newList, newHead);
        llu.printList(newList);

    }
}
