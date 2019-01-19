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
        ListNode head = llu.constructListFromArray(new int[]{27,20,37,64,33,66});

        //Print the original linked list.
        llu.printList(head);

        //If the list has only zero or one node, there is no need to sort.
        if (head == null || head.next == null)
            return;

        //Starting from the second node, try to insert it to the already partial-sorted array,
        //for example, we would to insert 20 to 27->null,
        //then insert 37 to 20->27->null and so on.
        ListNode toBeIns = head.next; //toBeIns means the node to be inserted to partially sorted linked list.
        head.next = null; //artificially cut the linked list into two lists:
           // one sorted starting from head,
           // the other are the nodes to be inserted, starting from toBeIns.
        ListNode nextToBeIns;
        ListNode curr; //the current node in the linked list
        ListNode prev; //the node before the current node
        while (toBeIns != null) {
            //insert toBeIns to the partially sorted linked list from head to the node before toBeIns
            curr = head;
            prev = null;

            while (curr != null && curr.val < toBeIns.val) {
                prev = curr;
                curr = curr.next;
            }
            //two possibilities:
            //1. curr == null,
            //   this means the value of node to be inserted (toBeIns) is largest in the partially sorted linked list
            //   and we just need to add toBeIns to the end.
            //2. curr != null and curr.val >= toBeIns.val, we need to insert toBeIns right before curr

            nextToBeIns = toBeIns.next; //update the node to be inserted in the next round
            toBeIns.next = curr; //insert toBeIns to the front of curr.

            //Insert toBeIns to the end of the prev. Note that prev may be null,
            //in that case, we need to update the head of the sorted linked list.
            if (prev == null)
                head = toBeIns;
            else
                prev.next = toBeIns;

            toBeIns = nextToBeIns;
        }

        //Print the sorted linked list.
        llu.printList(head);
    }
}
