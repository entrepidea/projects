package com.entrepidea.algo.leetcode.easy;

import com.entrepidea.algo.data_structure.tree.supports.TreeNode;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Source: https://leetcode.com/articles/symmetric-tree/
 * @Date: 07/27/19
 * */
public class LE101SymmetricTree {
    TreeNode root;
    @Before
    public void setUp() throws Exception {

        //construct a tree
        /**

           1
         /   \
        2     2
      /  \   / \
     3    4 4   3

         **/
        TreeNode curr;
        root = new TreeNode(1);
        TreeNode node = new TreeNode(2);
        root.lChild = node;
        node = new TreeNode(2);
        root.rChild = node;
        curr = root.lChild;
        node = new TreeNode(3);
        curr.lChild = node;
        node = new TreeNode(4);
        curr.rChild = node;
        curr = root.rChild;
        curr.lChild = new TreeNode(4);
        curr.rChild = new TreeNode(3);
    }

    private void inOrderTraverse(TreeNode root, List<Integer> placeHolder){
        if (root==null){
            return;
        }
        inOrderTraverse(root.lChild,placeHolder);
        placeHolder.add(root.val);
        inOrderTraverse(root.rChild,placeHolder);
    }

    @Test
    public void testSymmetricTree(){
        //the idea is to inorder traverse the tree and create an array or list to store the node and check if the
        //array is symmetric.
        //1. traversal
        List<Integer> l = new ArrayList<>();
        inOrderTraverse(root,l);
        for (int i: l){
            System.out.print(i);
        }
        System.out.println();
        //2. symmetric array check.
        //System.out.println(l.size()/2);
        int maxInx = l.size()-1;
        int index = 0;
        while(l.get(index)==l.get(maxInx-index) && index<maxInx){
            index++;
        }
        if(index==maxInx){
            System.out.println("Symmetric tree!");
        }

    }
}
