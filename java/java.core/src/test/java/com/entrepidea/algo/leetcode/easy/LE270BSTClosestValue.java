package com.entrepidea.algo.leetcode.easy;

import com.entrepidea.algo.data_structure.tree.supports.TreeNode;
import org.junit.Assert;
import org.junit.Test;

public class LE270BSTClosestValue {

    private void inorderTraverse(TreeNode root, int target, int[] ref, TreeNode[] node){
        if(root==null){
            return;
        }
        inorderTraverse(root.lChild, target, ref, node);
        System.out.println(root.val);
        int dif = Math.abs(root.val-target);
        if(dif<ref[0]){
            ref[0] = dif;
            node[0] = root;
        }
        inorderTraverse(root.rChild,target, ref, node);
    }
    private int closestValue(TreeNode root, int target){

        return 0;
    }

    private TreeNode constructTree(){
        TreeNode root = new TreeNode(0);
        /*
        *       0
        *      /  \
        *     1    2
        *    / \  /
        *   3   4 5
        * */
        root.lChild = new TreeNode(1);
        root.rChild = new TreeNode(2);
        TreeNode node = root.lChild;
        node.lChild = new TreeNode(3);
        node.rChild = new TreeNode(4);
        node = root.rChild;
        node.lChild = new TreeNode(5);
        return root;
    }

    @Test
    public void testInorderTraverse(){
        TreeNode root = constructTree();
        int target = 3;
        int[] ref = new int[]{target};
        TreeNode[] node = new TreeNode[]{root};
        inorderTraverse(root, target, ref, node);
        //3,1,4,0,5,2
        //Assert.assertEquals(5, node[0].val.intValue());
        Assert.assertEquals(3, node[0].val.intValue());
    }
    @Test
    public void test(){

    }
}
