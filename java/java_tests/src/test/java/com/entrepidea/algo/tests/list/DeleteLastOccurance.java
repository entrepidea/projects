package com.entrepidea.algo.tests.list;

import com.entrepidea.algo.tests.list.support.LinkedListUtils;
import com.entrepidea.algo.tests.list.support.ListNode;
import org.junit.Test;
/*
* desc: Given a liked list and a key to be deleted. Delete last occurrence of key from linked. The list may have duplicates.
 *source: https://www.geeksforgeeks.org/delete-last-occurrence-of-an-item-from-linked-list/
 *date: 12/18/18, 12/19/18
*
*
* */
public class DeleteLastOccurance {

    LinkedListUtils llu = LinkedListUtils.getInstance();
    @Test
    public void test(){
        ListNode head = llu.constructListFromArray(new int[]{1,11,2,3,4,5,2,2,8,9,10,2,2,3,8,3,1,1});
        System.out.println("original list:");
        llu.printList(head);
        int key = 1;
        ListNode p = head;
        ListNode prevNode = p;
        ListNode foundNode = p;
        if(p.val==key){//if the first element is equal to the key and no other occurance - special treatment.
            while(p.next!=null){
                p = p.next;
                if(p.val==key){
                    p = head;
                    break;
                }
            }
            if(p.next==null){
                head = head.next;
                p=head;
            }
        }
        while(p.next!=null){
            if(p.next.val ==key){
                foundNode = p.next;
                prevNode = p;
            }
            p = p.next;
        }
        if(p.val == key){ //last element is the key
            while(prevNode.next!=p){
                prevNode = prevNode.next;
            }
            prevNode.next = null;
        }
        else{
            prevNode.next = foundNode.next;
        }

        System.out.println("remove the last occurance: ");
        llu.printList(head);
    }
}
