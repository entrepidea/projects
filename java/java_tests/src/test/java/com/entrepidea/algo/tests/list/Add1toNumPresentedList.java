package com.entrepidea.algo.tests.list;


/**
 * source: https://www.geeksforgeeks.org/add-1-number-represented-linked-list/
 * desc: add 1 to a number presented as a linked list and show the resultant linked list
 * idea: reverse the linked list and do the math and reverse it back.
 * date: 12/29/18
 *
 * */
import com.entrepidea.algo.tests.list.support.LinkedListUtils;
import com.entrepidea.algo.tests.list.support.ListNode;
import org.junit.Test;

public class Add1toNumPresentedList {

    LinkedListUtils llu = LinkedListUtils.getInstance();

    ListNode reverse(ListNode head){
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
    public void testReverse(){
        ListNode head = llu.constructListFromArray(new int[]{1,2,3,4,5});
        ListNode newHead = reverse(head);
        llu.printList(newHead);
    }

    @Test
    public void test(){
        ListNode head = llu.constructListFromArray(new int[]{1,2,9,9});//testeed: 1999; tested:9999.
        llu.printList(head);
        //reverse the list
        ListNode newHead = reverse(head);
        //llu.printList(newHead);


        ListNode p = newHead;
        int remainder = (p.val+1)%10;
        int carry = (p.val+1)/10;
        p.val = remainder;
        p = p.next;
        while(p.next!=null){
            if(carry>0){
                p.val += carry;
            }
            remainder = p.val%10;
            carry = p.val/10;
            p.val = remainder;
            p = p.next;
        }
        //last element
        if(carry>0){
            p.val += carry;
        }
        remainder = p.val%10;
        carry = p.val/10;
        p.val = remainder;
        if(carry>0){
            ListNode newNode = new ListNode(carry);
            p.next = newNode;
        }
        head = reverse(newHead);
        llu.printList(head);
    }
}
