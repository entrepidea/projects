package com.entrepidea.algo.tests.list;

import com.entrepidea.algo.tests.list.support.LinkedListUtils;
import com.entrepidea.algo.tests.list.support.ListNode;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Source: https://leetcode.com/problems/min-stack/
 * @Description: create a simple stack
 * @Date: 09/04/19
 *
 * */

public class LE155MinStack {

    private ListNode head;
    private ListNode tail;

    public ListNode begin(){
        return head;
    }

    public void push(int x){
        ListNode temp = head;
        if(temp==null){
            temp = new ListNode(x);
            head = temp;
            tail = temp;
            return;
        }
        tail.next = new ListNode(x);
        tail = tail.next;
    }
    public int getMin(){
        ListNode temp = head;
        int min = temp.val;
        while(temp!=null){
           if(temp.val<min){
               min = temp.val;
           }
           temp = temp.next;
        }
        return min;
    }
    public boolean isEmpty(){
        return head == null;
    }
    public void pop(){
        if(isEmpty()){
            return;
        }
        ListNode temp = head;
        while(temp!=null && temp.next!=tail){
            temp = temp.next;
        }
        temp.next = null;
        tail = temp;
    }

    public int top(){
        return tail.val;
    }

    @Test
    public void testPush(){
        LE155MinStack s = new LE155MinStack();
        s.push(1);
        s.push(2);
        s.push(3);
        s.push(-1);

        LinkedListUtils.getInstance().printList(s.begin());
    }

    @Test
    public void testGetMin(){
        LE155MinStack s = new LE155MinStack();
        s.push(1);
        s.push(2);
        s.push(3);
        s.push(-1);
        Assert.assertTrue(s.getMin()==-1);
    }

    @Test
    public void testPopandTop(){
        LE155MinStack s = new LE155MinStack();
        s.push(1);
        s.push(2);
        s.push(3);
        s.pop();
        LinkedListUtils.getInstance().printList(s.begin());
        Assert.assertTrue(s.top()==2);

    }
}
