package com.entrepidea.algo.data_structure.tree.supports;

import java.util.List;

public class TreeUtilities {

    //the method below translate an array into a BST
    //See: https://www.geeksforgeeks.org/construct-complete-binary-tree-given-array/
    public static TreeNode insertLevelNode(final int[] arr, TreeNode root, int level, int arrLen){
        if(level<arrLen){
            TreeNode temp = new TreeNode(arr[level]);
            root = temp;
            root.lChild = insertLevelNode(arr, root.lChild, 2*level+1, arrLen);
            root.rChild = insertLevelNode(arr,root.rChild,2*level+2, arrLen);
        }
        return root;
    }

    //BST traversal -> DST -> inOrder
    public static void inorder(TreeNode node, List<TreeNode> l){
        if(node==null){
            return;
        }
        inorder(node.lChild, l);
        //System.out.print(node.val);
        l.add(node);
        inorder(node.rChild,l);
    }
}
