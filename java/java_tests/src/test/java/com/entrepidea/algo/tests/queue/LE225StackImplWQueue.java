package com.entrepidea.algo.tests.queue;

import org.junit.Test;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Source: https://leetcode.com/problems/implement-stack-using-queues/
 * @Desc: use a queue to implement a stack
 * @Note: I took advantage of priorityQueue, whose order follows a embedded sorting algorithm (fed in via one of the queue's constructors).
 * Of course, using a deque might be an easier option.
 * Created by jonat on 11/6/2019.
 */
public class LE225StackImplWQueue {

    public static class MyStack<T>{

        private Queue<InternalData<T> > buf;

        private class InternalData<T>{

            public InternalData(T t, int p){
                data = t;
                priority = p;
            }
            public T data;
            public int priority;
        }

        private int count=0;

        public MyStack(int initCap){
            buf = new PriorityQueue<>(initCap, (a,b)->(b.priority-a.priority));
        }

        public T pop(){
            return buf.remove().data;
        }
        public void push(T t){
            InternalData<T> ele = new InternalData<>(t, count++);
            buf.add(ele);
        }

        public boolean isEmpty(){
            return buf.isEmpty();
        }
    }


    @Test
    public void test(){
        MyStack<Integer> s = new MyStack<>(16);
        s.push(11);
        s.push(22);
        s.push(33);
        s.push(44);
        while(!s.isEmpty()){
            System.out.println(s.pop());
        }
    }

    @Test
    public void testPriorityQueue(){
        Queue<Integer> q = new PriorityQueue<>(16, (a,b)-> (b-a));
        q.add(3);
        q.add(1);
        q.add(4);
        q.add(2);

        while(!q.isEmpty()){
            System.out.println(q.remove());
        }

    }


    //another approach: - use two queues
    public static class MyStack2<T>{

        private Queue<T> q1;
        private Queue<T> q2;

        private T top;

        MyStack2(){
            q1 = new LinkedList<>();
            q2 = new LinkedList<>();
        }


        public void push(T t){
            q1.add(t);
            top = t;
        }

        public T pop(){
            if(q1.size()==1){
                return q1.remove();
            }
            while(q1.size()>1){
                q2.add(q1.remove());
            }
            top = q1.remove();
            Queue<T> temp = q1;
            q1 = q2;
            q2 = temp;
            return top;
        }

        public boolean isEmpty(){
            return q1.isEmpty();
        }

    }

    @Test
    public void test2(){
        MyStack2<Integer> s = new MyStack2<>();
        s.push(11);
        s.push(22);
        s.push(33);
        s.push(44);
        while(!s.isEmpty()){
            System.out.println(s.pop());
        }
    }


}
