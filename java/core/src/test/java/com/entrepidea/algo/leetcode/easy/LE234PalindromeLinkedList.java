package com.entrepidea.algo.leetcode.easy;

import com.entrepidea.algo.data_structure.list.support.LinkedListUtils;
import com.entrepidea.algo.data_structure.list.support.ListNode;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Source: https://leetcode.com/problems/palindrome-linked-list/
 * @Desc: to check if a linked list is palindrome
 * @Note:
 * there is another implementation in LE206ReverseList, here I try to come up with one with better performance.
 * Idea is reverse only first half and compare.
 *
 * Created by jonat on 11/7/2019.
 */
public class LE234PalindromeLinkedList {

    LinkedListUtils llu = LinkedListUtils.getInstance();

    private ListNode midNode(ListNode head){
        if (head==null || head.next==null){
            return head;
        }
        ListNode fastNode = head;
        ListNode slowNode = head;
        while(fastNode!=null && fastNode.next!=null){
            fastNode = fastNode.next.next;
            slowNode = slowNode.next;
        }
        return slowNode;
    }

    private int len(ListNode head){
        int count= 0;
        ListNode p = head;
        while(p!=null){
            count++;
            p=p.next;
        }
        return count;
    }

    @Test
    public void testMidNode(){
        ListNode head = llu.constructListFromArray(new int[]{1,2,3,4,3,2,1});
        ListNode mid = midNode(head);
        ListNode p = head;
        while(p!=mid){
            System.out.println(p.val);
            p = p.next;
        }
        System.out.println(p.val);
        Assert.assertEquals(p,mid);

    }

    @Test
    public void testLen(){
        ListNode head = llu.constructListFromArray(new int[]{1,2,3,4,3,2,1});
        Assert.assertEquals(7, len(head));

        head = llu.constructListFromArray(new int[]{1,2,3,3,2,1});
        Assert.assertEquals(6, len(head));
    }

    private ListNode reverse(ListNode head){
        if(head==null || head.next==null){
            return head;
        }
        ListNode newHead = reverse(head.next);
        ListNode p = newHead;
        while(p.next!=null){
            p = p.next;
        }
        p.next = head;
        head.next = null;
        return newHead;
    }

    private boolean isPalinDrome(ListNode head){
        int l = len(head);
        ListNode mid = midNode(head);

        ListNode p = head;
        ListNode prev = p;
        while(p!=mid){
            prev = p;
            p=p.next;
        }
        prev.next = null;
        ListNode secHead= (l%2==0)?p:p.next;
        ListNode newHead = reverse(head);
        while(newHead!=null && secHead!=null && newHead.val==secHead.val){
            newHead = newHead.next;
            secHead = secHead.next;
        }
        if(newHead==null && secHead==null){
            return true;
        }
        else{
            return false;
        }

    }
    @Test
    public void testPalindrome(){
        ListNode head = llu.constructListFromArray(new int[]{1,2,3,3,2,1});
        Assert.assertTrue(isPalinDrome(head));

        head = llu.constructListFromArray(new int[]{1,7,3,3,2,1});
        Assert.assertFalse(isPalinDrome(head));

        head = llu.constructListFromArray(new int[]{1,2,3,4,3,2,1});
        Assert.assertTrue(isPalinDrome(head));

        head = llu.constructListFromArray(new int[]{1,7,3,4,3,2,1});
        Assert.assertFalse(isPalinDrome(head));


    }
}
