package com.entrepidea.algo.tests.list;

import com.entrepidea.algo.tests.list.support.LinkedListUtils;
import com.entrepidea.algo.tests.list.support.ListNode;
import org.junit.Test;

/**
 * @Source: https://leetcode.com/problems/remove-linked-list-elements/
 * @Desc: Remove elements of designated value from a list.
 * Created by jonat on 11/5/2019.
 */
public class LE203RemoveEleFromList {

    private ListNode foo(ListNode head, int val){
        if(head.val==val){
            while(head!=null && head.val==val){
                head = head.next;
            }
        }
        if(head==null){
            return head;
        }

        ListNode p = head;
        ListNode prev;
        while(p!=null){
            prev = p;
            p = p.next;
            if(p.val==val) {
                while (p!=null && p.val == val) {
                    p = p.next;
                }
                prev.next = p;
            }

        }
        return head;
    }

    @Test
    public void test(){
        ListNode head = LinkedListUtils.getInstance().constructListFromArray(new int[]{6,6,6,1,2,6,6,6,3,4,5,6});
        head = foo(head,6);
        if(head!=null) {
            LinkedListUtils.getInstance().printList(head);
        }
    }
}
