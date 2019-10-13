package com.entrepidea.algo.tests.list;
/**
 * desc: to check if a linked list is a palindrome
 * source: https://www.geeksforgeeks.org/function-to-check-if-a-singly-linked-list-is-palindrome/
 * date: 12/31/18
 * */
import com.entrepidea.algo.tests.list.support.LinkedListUtils;
import com.entrepidea.algo.tests.list.support.ListNode;
import org.junit.Test;

import java.util.Stack;

public class PalindromeLinkedList {
    LinkedListUtils llu = LinkedListUtils.getInstance();


    private ListNode reverse(ListNode head){
        if(head==null || head.next==null){
            return head;
        }
        else{
            ListNode newHead = reverse(head.next);
            ListNode p = newHead;
            while(p.next!=null){
                p = p.next;
            }
            p.next = head;
            head.next = null;
            return newHead;
        }
    }
    @Test
    public void test(){
        ListNode head = llu.constructListFromArray(new int[]{1,2,3,3,2,1});
        llu.printList(head);
        //break the list in half
        ListNode fastNode = head;
        ListNode slowNode = head;
        while(fastNode.next!=null && fastNode.next.next!=null){
            fastNode = fastNode.next.next;
            slowNode = slowNode.next;
        }
        ListNode newHead = new ListNode(slowNode.val);
        newHead.next = slowNode.next;
        //reverse the 2nd half
        newHead = reverse(newHead);
        llu.printList(newHead);

        ListNode p = newHead;
        while(p.next!=null && p.val==head.val){
            p = p.next;
            head = head.next;
        }
        if(p.next==null){
            System.out.println("Palindrome");
        }
    }

    //use a stack
    @Test
    public void test2(){
        ListNode head = llu.constructListFromArray(new int[]{1,2,3,3,2,1});
        Stack<ListNode> s = new Stack<>();
        ListNode p = head;
        while(p!=null){
            s.push(p);
            p = p.next;
        }
        p = head;
        while(s.pop().val==p.val && !s.isEmpty()){
            p = p.next;
        }
        if(s.isEmpty()){
            System.out.println("Palindrome");
        }
    }
}
