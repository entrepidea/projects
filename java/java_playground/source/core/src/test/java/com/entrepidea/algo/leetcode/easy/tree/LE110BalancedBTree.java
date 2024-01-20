package com.entrepidea.algo.leetcode.easy.tree;

import com.entrepidea.algo.data_structure.tree.supports.TreeNode;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Source: https://leetcode.com/problems/balanced-binary-tree/
 * @Description:
 * To determine if a tree is a balanced binary tree.
 * My idea is that I do a BFS and determine the level and the height of each node. If a node found it has a sibling (another node of the the same level),
 * the height of these two nodes are compared and if the difference is greater than 1, we found a winner!
 *
 * @Date: 08/11/19
 *
 * */
public class LE110BalancedBTree {


    private int getHeight(TreeNode node){
        if (node==null){
            return 0;
        }

        int lh = getHeight(node.lChild);
        int rh = getHeight(node.rChild);
        return 1 + Math.max(lh, rh);
    }

    private boolean isBalanced(TreeNode node){
        if (node==null){
            return true;
        }
        int lh = getHeight(node.lChild);
        int rh = getHeight(node.rChild);

        if(Math.abs(lh-rh)<=1 && isBalanced(node.lChild) && isBalanced(node.rChild)){
            return true;
        }
        return false;
    }

    @Test
    public void test2(){
        TreeNode root;
        root = new TreeNode(3);
        TreeNode curr = root;
        curr.lChild = new TreeNode(9);
        curr.rChild = new TreeNode(20);
        curr = curr.rChild;
        curr.lChild = new TreeNode(15);
        curr.rChild = new TreeNode(7);
        //curr = curr.rChild;
        //curr.rChild = new TreeNode(13);
        Assert.assertTrue(isBalanced(root));
    }

    @Test
    public void test3(){
        TreeNode r = new TreeNode(1);
        TreeNode cur = r;
        cur.lChild = new TreeNode(2);
        cur.rChild = new TreeNode(2);
        cur = cur.lChild;
        cur.lChild = new TreeNode(3);
        cur.rChild = new TreeNode(3);
        cur = cur.lChild;
        cur.lChild = new TreeNode(4);
        cur.rChild = new TreeNode(4);
        Assert.assertFalse(isBalanced(r));
    }
}
