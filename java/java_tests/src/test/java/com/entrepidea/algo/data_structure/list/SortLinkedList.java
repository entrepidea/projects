package com.entrepidea.algo.data_structure.list;

import com.entrepidea.algo.data_structure.list.support.LinkedListUtils;
import com.entrepidea.algo.data_structure.list.support.ListNode;
import org.junit.Test;


public class SortLinkedList {

    LinkedListUtils llu = LinkedListUtils.getInstance();

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode (int val){
            this.val = val;
        }

        public TreeNode(ListNode node){
            this.val = node.val;
        }
    }

    // Recursive function to perform in-order traversal of the tree
    public static void inorder(TreeNode root)
    {
        // return if the current node is empty
        if (root == null) {
            return;
        }

        // Traverse the left subtree
        inorder(root.left);

        // Display the data part of the root (or current node)
        System.out.print(root.val + "\t");

        // Traverse the right subtree
        inorder(root.right);
    }
    @Test
    public void test(){
        //ListNode head = llu.constructRandomNumberList(10, 100);
        ListNode head = llu.constructListFromArray(new int[]{2,94,28,54});
        llu.printList(head);

        //create a B-tree.
        TreeNode root = new TreeNode(head);
        ListNode p = head.next;
        while(p!=null){
            TreeNode start = root;
            if(start.left ==null && start.right ==null){
                if(p.val<=start.val){
                    start.left = new TreeNode(p);
                }
                else{
                    start.right = new TreeNode(p);
                }

            }
            else {
                while (start.left!=null && p.val<=start.val){
                    start = start.left;
                }
                while (start.right!=null && p.val>start.val){
                    start = start.right;
                }
                if (p.val <= start.val) {
                       start.left = new TreeNode(p);
                    } else {
                        start.right = new TreeNode(p);
                    }
            }

            p = p.next;
        }

        //In-order DFS
        //TreeNode start = root;
        inorder(root);
    }
}
