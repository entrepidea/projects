package com.entrepidea.algo.tests.stack;

import org.junit.Test;

import java.util.Stack;

/**
 * @Source: https://leetcode.com/problems/implement-queue-using-stacks/
 * @Desc: use stack to implement queue
 * @Note: use two stacks.
 * Created by jonat on 11/6/2019.
 */
public class LE232QueueImnplWStack {

    public static class MyQueue<T> {

        private Stack s1;
        private Stack s2;
        private T head;

        MyQueue(){
            s1 = new Stack();
            s2 = new Stack();
        }
        public void add(T ele){
            s1.add(ele);
        }

        public T poll(){
            if(s1.size()==1){
                return (T)s1.pop();
            }
            while(s1.size()>1){
                s2.push(s1.pop());
            }
            head = (T)s1.pop();
            while(!s2.isEmpty()){
                s1.push(s2.pop());
            }
            return head;
        }

        public boolean isEmpty(){
            return s1.isEmpty();
        }
    }

    @Test
    public void test(){
        MyQueue<Integer> q = new MyQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        q.poll();
        q.add(4);

        while(!q.isEmpty()){
            System.out.println(q.poll());
        }
    }
}
