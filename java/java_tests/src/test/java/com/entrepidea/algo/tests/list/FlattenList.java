package com.entrepidea.algo.tests.list;

/**
 * a linked list whose elements is a linked list themselves. Each linked list is sorted on its own right
 * source: https://www.geeksforgeeks.org/flattening-a-linked-list/
 * date: 12/02/18.
 * the idea is to use merge sort
 *
 * */
import com.entrepidea.algo.tests.list.support.DListNode;
import org.junit.Test;

public class FlattenList {

    private DListNode constructList(){
        /**
         *
         * the data structure
         *
         * 5->10->19->28
         * |   |   |   |
         * 7   20  22  35
         * |   |   |   |
         * 8       50  40
         * |           |
         * 30          45
         *
         * */
        DListNode head = new DListNode(5);
        DListNode p = head;
        DListNode temp = new DListNode(10);
        p.right = temp;
        p = temp;
        temp = new DListNode(19);
        p.right = temp;
        p=temp;
        temp = new DListNode(28);
        p.right = temp;

        p = head;
        temp = new DListNode(7);
        p.left = temp;
        p = temp;
        temp = new DListNode(8);
        p.left = temp;
        p = temp;
        temp = new DListNode(30);
        p.left = temp;


        p = head.right;
        temp = new DListNode(20);
        p.left = temp;


        p = head.right.right;
        temp = new DListNode(22);
        p.left = temp;
        p = temp;
        temp = new DListNode(50);
        p.left = temp;

        p = head.right.right.right;
        temp = new DListNode(35);
        p.left = temp;
        p = temp;
        temp = new DListNode(40);
        p.left = temp;
        p = temp;
        temp = new DListNode(45);
        p.left = temp;

        return head;
    }

    private void printList(DListNode head){
        while(head!=null){
            System.out.print(head.val);
            System.out.print('\t');
            DListNode p = head.left;
            while(p!=null){
                System.out.print(p.val);
                System.out.print('\t');
                p = p.left;
            }
            head = head.right;
        }
    }

    private DListNode merge(DListNode first, DListNode sec){
        DListNode dummy = new DListNode(0);
        DListNode rp = sec.right;
        DListNode newHead = dummy;


        while(true){
            if(first==null){
                dummy.left = sec;
                break;
            }
            if(sec==null){
                dummy.left = first;
                break;
            }

            if(first.val<=sec.val){
                dummy.left = first;
                first = first.left;
            }
            else{
                dummy.left = sec;
                sec = sec.left;
            }
            dummy = dummy.left;
        }
        newHead = newHead.left;
        newHead.right = rp;
        return newHead;
    }

    private DListNode flattenList(DListNode head){
            DListNode ret = new DListNode(head.val);
            ret.left = head.left;
            ret.right = head.right;
            DListNode  p =ret;
            while(p!=null && p.right!=null){
                DListNode newListHead = merge(p, p.right);
                p = newListHead;
            }
            return ret;
    }

    @Test
    public void test(){
        DListNode head = constructList();
        //printList(head);
        DListNode newHead = flattenList(head);
        while(newHead!=null){
            System.out.print(newHead.val);
            System.out.print('\t');
            newHead = newHead.left;
        }
        System.out.println();
    }
}
