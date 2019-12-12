package com.entrepidea.algo.data_structure.list;

import org.junit.Test;

/*
* A single link list, how do you move the head to the middle of the link? (MS phone interview 05/14/18)
* Idea:
* find the middle node N(mid) and the node right before it N(mid-1).
*
* */
public class MoveHeadNode {
    static class Node {
        public Node(int v){
            val = v;
        }
        public int val;
        public Node next;
    }

    public  Node initList(){
        Node head = new Node(0);
        Node temp = head;
        for(int i=1;i<10;i++){
            Node node = new Node(i);
            temp.next = node;
            temp = node;
        }
        return head;
    }

    //checkBalancedBinaryTree init is done
    public  void printList(Node head){

        Node node = head;
        while(node.next!=null){
            System.out.print(node.val+",");
            node = node.next;
        }
        System.out.println();
    }

    public  int len(Node head){
        Node node = head;
        int count = 0;
        while(node.next!=null){
            count++;
            node = node.next;
        }
        return count;
    }

    public  Node locateMid(Node head){
        int loc = Math.round(len(head)/2)+1;
        Node temp = head;
        for(int i=0;i<loc;i++){
            temp = temp.next;
        }
        return temp;
    }

    public Node beforeMid(Node head){
        Node midNode = locateMid(head);
        Node p = head;
        while(p.next!=midNode){
            p = p.next;
        }
        return p;
    }

    public  Node moveHead(Node head){
        Node newHead = head.next;
        Node temp = head;
        while(temp.next!=null){
            temp = temp.next;
        }
        Node tail = temp;
        temp.next = head;
        return newHead;
    }

    @Test
    public void test(){
        //create a linked list
        Node head = initList();
        System.out.println("Original list: ");
        printList(head);
        //System.out.println(locateMid(head).val);
        Node midNode = locateMid(head);
        Node beforeMidNode = beforeMid(head);
        Node p = head;
        Node newHead = head.next; // new head
        beforeMidNode.next = p;
        p.next = midNode;
        System.out.println("New list: ");
        printList(newHead);

    }
}
