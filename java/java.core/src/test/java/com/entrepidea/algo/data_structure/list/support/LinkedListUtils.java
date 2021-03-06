package com.entrepidea.algo.data_structure.list.support;

import java.util.concurrent.ThreadLocalRandom;

/*
* A helper class designed to be used by other classes in this package
*
* */
public class LinkedListUtils {
    private static LinkedListUtils ourInstance = new LinkedListUtils();

    public static LinkedListUtils getInstance() {
        return ourInstance;
    }

    private LinkedListUtils() {
    }


    /***************************************************************
     *
     * Below are the help methods for this checkBalancedBinaryTree
     *
     ****************************************************************/
    //construct a single linked list for testing
    public ListNode constructList(int len){
        ListNode head = new ListNode(0);
        ListNode p = head;
        for(int i=1;i<len;i++){
            ListNode node = new ListNode(i);
            p.next = node;
            p = p.next;
        }
        return head;
    }

    public ListNode constructRandomNumberList(int len, int numRange){
        int[] arr = ThreadLocalRandom.current().ints(0,numRange).limit(len).toArray();
        return constructListFromArray(arr);
    }


    public ListNode constructListFromArray(int[] arr){
        ListNode head = new ListNode(arr[0]);
        ListNode p = head;
        for(int i=1;i<arr.length;i++){
            ListNode node = new ListNode(arr[i]);
            p.next = node;
            p = p.next;
        }
        return head;
    }

    //print the list for testing
    public void printList(ListNode head){
        ListNode p = head;
        while(p!=null){
            System.out.print(p.val);
            System.out.print('\t');
            p = p.next;
        }
        System.out.println();
    }



    public ListNode findLastNode(ListNode head){
        ListNode p = head;
        while(p.next!=null){
            p = p.next;
        }
        return p;
    }

    public ListNode findMidNode(ListNode head){
        ListNode pFast = head;
        ListNode pSlow = head;
        if(pFast == null || pFast.next==null){
            return pFast;
        }
        while(pFast!=null){
            pFast=  pFast.next.next;
            pSlow = pSlow.next;
        }
        return  pSlow;
    }
}
