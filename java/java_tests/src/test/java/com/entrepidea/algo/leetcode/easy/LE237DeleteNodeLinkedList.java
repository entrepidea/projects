package com.entrepidea.algo.leetcode.easy;

import com.entrepidea.algo.data_structure.list.support.LinkedListUtils;
import com.entrepidea.algo.data_structure.list.support.ListNode;
import org.junit.Test;

/**
 * @Source: https://leetcode.com/problems/delete-node-in-a-linked-list/
 * @Desc: delete nodes of designated value from a linked list.
 * Created by jonat on 11/7/2019.
 */
public class LE237DeleteNodeLinkedList {

    private ListNode foo(ListNode head, int num){
        if((head==null || head.next==null)) {
            if(head.val!=num){
                System.out.println("Not found");
            }
            return null;
        }

        while(head.val==num){
            head = head.next;
        }

        ListNode prev = head;
        ListNode p = head.next;

        while(p!=null){

            if(p.val==num) {
                while (p!=null && p.val == num) {
                    p = p.next;
                }
                prev.next = p;
            }
            else {
                prev = p;
            }
            if(p!=null) {
                p = p.next;
            }
        }
        return head;
    }

    @Test
    public void test(){
        LinkedListUtils llu = LinkedListUtils.getInstance();
        ListNode root = llu.constructListFromArray(new int[]{5,5,5,4,5,5,5,1,9,5,5,5,5});
        ListNode p = foo(root,5);
        while(p!=null){
            System.out.print(p.val+", ");
            p = p.next;
        }
        System.out.println();
    }
}
