package com.entrepidea.algo.pattern.fast_slow_pointers;

import com.entrepidea.algo.data_structure.list.support.LinkedListUtils;
import com.entrepidea.algo.data_structure.list.support.ListNode;
import org.junit.Assert;
import org.junit.Test;
/**
 * @Desc: to detect if a linked list has a cycle  or not.
 * @Source: https://www.geeksforgeeks.org/detect-loop-in-a-linked-list/
 * @Note; fast - slow pointer problem. If the slow pointer can catch up with the fast one, there is a cycle.
 * @Date: 12/08/19
 * */
public class LinkedListCycle {

    private boolean hasCycle(ListNode head){
        if(head==null || head.next==null){
            return false;
        }
        ListNode fastPtr = head;
        ListNode slowPtr = head;
        while(fastPtr!=null  && slowPtr!=null){
            fastPtr = fastPtr.next.next;
            slowPtr = slowPtr.next;
            if(fastPtr==slowPtr){
                return true;
            }
        }
        return false;
    }

    @Test
    public void test(){
        ListNode head = LinkedListUtils.getInstance().constructList(10);
        LinkedListUtils.getInstance().printList(head);
        Assert.assertFalse(hasCycle(head));
        ListNode midNode = LinkedListUtils.getInstance().findMidNode(head);
        //System.out.println(midNode.val);
        Assert.assertEquals(5,midNode.val);
        ListNode lastNode = LinkedListUtils.getInstance().findLastNode(head);
        Assert.assertEquals(9,lastNode.val);
        Assert.assertNull(lastNode.next);
        lastNode.next = midNode;
        Assert.assertTrue(hasCycle(head));

    }
}
