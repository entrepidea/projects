package com.entrepidea.algo.data_structure.list;

import com.entrepidea.algo.data_structure.list.support.LinkedListUtils;
import com.entrepidea.algo.data_structure.list.support.ListNode;
import org.junit.Test;

/**
 * desc: sort a linked list using bubble sorting.
 * source: https://www.geeksforgeeks.org/c-program-bubble-sort-linked-list/
 * date: 01/07/19
 * */

public class BubbleSortedLinkedList {

    LinkedListUtils llu = LinkedListUtils.getInstance();


    private void swap(ListNode x, ListNode y){
        int temp = x.val;
        x.val = y.val;
        y.val = temp;
    }
    @Test
    public void test1(){
        ListNode head = llu.constructListFromArray(new int[]{10,30,20,5});
        boolean isSorted = false;
        while(!isSorted){
            isSorted = true;
            ListNode p = head;
            while(p.next!=null){
                if(p.val>p.next.val){
                    swap(p, p.next);
                    isSorted = false;
                }
                p = p.next;
            }
        }

        ListNode p = head;
        while(p!=null){
            System.out.print(p.val);
            System.out.print(",");
            p = p.next;
        }
        System.out.println();

    }

    //optimal on the top of test1: the stopping point doesn't have to be the last node of the list
    @Test
    public void test2(){
        ListNode head = llu.constructListFromArray(new int[]{10,30,20,5,6,1,100});
        boolean isSorted = false;
        ListNode endPtr = null;
        while(!isSorted){
            isSorted = true;
            ListNode p = head;
            while(p.next!=endPtr){
                if(p.val>p.next.val){
                    swap(p, p.next);
                    isSorted = false;
                }
                p = p.next;
            }
            endPtr = p;
        }

        ListNode p = head;
        while(p!=null){
            System.out.print(p.val);
            System.out.print(",");
            p = p.next;
        }
        System.out.println();
    }
}
