package com.entrepidea.algo.tests.list;

import com.entrepidea.algo.tests.list.support.LinkedListUtils;
import com.entrepidea.algo.tests.list.support.ListNode;
import org.junit.Test;


public class MergeSortLinkedList {

    public ListNode constructList(){
        return LinkedListUtils.getInstance().constructRandomNumberList(10,100);
    }

    public ListNode breakInto2Lists(ListNode head){
        if(head==null || head.next==null){
            return head;
        }
        int count = 0;
        ListNode mid = head;
        ListNode prev = head;
        ListNode p = head;
        while(p!=null){
            if(count%2!=0){
                prev = mid;
                mid = mid.next;
            }
            p = p.next;
            count++;
        }
        prev.next = null;
        return breakInto2Lists(merge(head,mid));
    }

    public ListNode merge(ListNode l1, ListNode l2){
        if(l1==null){
            return l2;
        }
        if(l2 == null){
            return l1;
        }
        ListNode head = null;
        if(l1.val<=l2.val){
            head = new ListNode(l1.val);
        }
        else{
            head = new ListNode(l2.val);
        }
        ListNode p = head;
        while(true){
            if(l1 == null){
                p.next = l2;
                break;
            }
            if(l2 == null){
                p.next = l1;
                break;
            }
            if(l1.val<=l2.val){
                p.next = l1;
                l1 = l1.next;
            }
            else{
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
        }
        return head;

    }

    @Test
    public void test(){
        LinkedListUtils luu = LinkedListUtils.getInstance();
        ListNode head = constructList();
        luu.printList(head);

        ListNode newHead = breakInto2Lists(head);
        luu.printList(newHead);
    }

/*    private ListNode sortList(ListNode head){
        ListNode newHead = new ListNode(head.val);

    }*/
}
