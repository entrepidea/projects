package com.entrepidea.algo.tests.list;

import com.entrepidea.algo.tests.list.support.LinkedListUtils;
import com.entrepidea.algo.tests.list.support.ListNode;
import org.junit.Assert;
import org.junit.Test;
/**
 * @Description: check if a linked list has a cycle
 * @Source: https://leetcode.com/problems/linked-list-cycle/
 * @Solution: below is the smartest way, called Floyd cycle.
 * @Date: 08/27/19
 *
 * */
public class LE141LinkedListCycle {

    private ListNode createCycledList(){
        ListNode head = LinkedListUtils.getInstance().constructListFromArray(new int[]{3,2,0,-4});
        ListNode secNode = head.next;
        ListNode temp = head;
        while(temp.next!=null){
            temp = temp.next;
        }
        temp.next = secNode;
        return head;
    }
    private boolean hasCycle(ListNode head){
        ListNode slowP = head;
        ListNode fastP = head;
        while(fastP!=null&&slowP!=null){
            slowP = slowP.next;
            if(fastP.next==null){
                return false;
            }
            fastP = fastP.next.next;
            if(fastP==slowP){
                return true;
            }
        }
        return false;
    }

    private ListNode createNonCycledList(){
        return LinkedListUtils.getInstance().constructListFromArray(new int[]{3,2,0,-4});
    }

    @Test
    public void test(){
        ListNode head = createCycledList();
        Assert.assertTrue(hasCycle(head));
        Assert.assertFalse(hasCycle(createNonCycledList()));
    }
}
