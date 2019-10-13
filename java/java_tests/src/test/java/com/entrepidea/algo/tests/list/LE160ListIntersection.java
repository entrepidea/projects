package com.entrepidea.algo.tests.list;

import com.entrepidea.algo.tests.list.support.LinkedListUtils;
import com.entrepidea.algo.tests.list.support.ListNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
 * @Description: to find out if two linked lists have shared node that interconnects each other.
 * @Source: https://leetcode.com/problems/intersection-of-two-linked-lists/
 * @Date: 09/08/19
 * */
public class LE160ListIntersection {

    private ListNode head1, head2;

    @Before
    public void createLists(){
        head1 = LinkedListUtils.getInstance().constructListFromArray(new int[]{4,1,8,4,5});
        head2 = LinkedListUtils.getInstance().constructListFromArray(new int[]{5,0,1});

        ListNode temp1 = head1;
        ListNode sharedNode = temp1.next.next;
        ListNode temp2 = head2;
        while(temp2.next!=null){
            temp2 = temp2.next;
        }
        temp2.next = sharedNode;
    }

    @Test
    public void testCreatedList(){
        LinkedListUtils.getInstance().printList(head2);
    }

    private ListNode interception(){
        ListNode temp1 = head1;
        ListNode temp2 = head2;
        while(temp1!=null){
            while(temp2!=null){
                if(temp2 == temp1){
                    return temp2;
                }
                temp2 = temp2.next;
            }
            temp1 = temp1.next;
            temp2 = head2;
        }
        return null;
    }

    @Test
    public void testInterception(){
        Assert.assertTrue(interception().val==8);
    }
}
