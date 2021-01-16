package com.entrepidea.algo.leetcode.easy.tree;

import com.entrepidea.algo.data_structure.tree.supports.TreeNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @Source: https://leetcode.com/problems/minimum-depth-of-binary-tree/
 * @Description: find out the minimum depth of a tree
 * @Date: 08/13/19
 *
 * */
public class LE111MinDepthTree {
    private TreeNode root;

    @Before
    public void setup(){
        root = new TreeNode(3);
        TreeNode curr = root;
        curr.lChild = new TreeNode(9);
        curr.rChild = new TreeNode(20);
        curr = curr.rChild;
        curr.lChild = new TreeNode(15);
        curr.rChild = new TreeNode(7);

    }


    private int treeLength(TreeNode node){
        if(node==null){
            return 0;
        }
        if(node.lChild==null && node.rChild ==null){
            return 1;
        }
        if(node.lChild==null){
            return treeLength(node.rChild)+1;
        }
        if(node.rChild==null){
            return treeLength(node.lChild)+1;
        }
        return Math.min(treeLength(node.lChild), treeLength(node.rChild))+1;
    }

    @Test
    public void test(){

        Assert.assertTrue(treeLength(root)==2);
    }
}
