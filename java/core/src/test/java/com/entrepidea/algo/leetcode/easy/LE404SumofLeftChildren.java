package com.entrepidea.algo.leetcode.easy;

import com.entrepidea.algo.data_structure.tree.supports.TreeNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @Desc: find the sum of all the left children's values.
 * @Source: https://leetcode.com/problems/sum-of-left-leaves/
 * @Date: 05/15/20
 *
 * */
public class LE404SumofLeftChildren {

    private int sum(TreeNode root){
        TreeNode node = root;
        List<TreeNode> l = new ArrayList<>();
        List<TreeNode> leftChildren = new ArrayList<>();
        l.add(node);
        while(!l.isEmpty()){
            TreeNode n = l.remove(0);
            if(n.lChild!=null){
                leftChildren.add(n.lChild);
                l.add(n.lChild);
            }
            if(n.rChild!=null){
                l.add(n.rChild);
            }
        }
        return leftChildren.stream().mapToInt(e -> e.val).reduce(0, (a,b) -> a+b);
    }

    @Test
    public void test(){
        //construct a tree
        TreeNode root = new TreeNode(3);
        TreeNode node = root;
        node.lChild = new TreeNode(9);
        node.rChild = new TreeNode(20);
        node = node.rChild;
        node.lChild = new TreeNode(15);
        node.rChild = new TreeNode(7);

        Assert.assertEquals(sum(root), 24);
    }
}
