package com.entrepidea.algo.tests.tree;

import com.entrepidea.algo.tests.tree.supports.TreeNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
 * @Description: to check if a tree has a path, the sum of the nodes on which equals to a given number.
 * @Source: https://leetcode.com/problems/path-sum/
 * @Date: 08/22/19
 * @Solution: https://www.geeksforgeeks.org/root-to-leaf-path-sum-equal-to-a-given-number/
 *
 * */
public class LE112PathSum {

    private TreeNode root;
    @Before
    public void setup(){
        root = new TreeNode(5);
        TreeNode cur = root;
        cur.lChild = new TreeNode(4);
        cur.rChild = new TreeNode(8);
        cur.lChild.lChild = new TreeNode(11);
        cur.rChild.lChild = new TreeNode(13);
        cur.rChild.rChild = new TreeNode(4);
        cur.rChild.rChild.rChild = new TreeNode(1);
        cur.lChild.lChild.lChild = new TreeNode(7);
        cur.lChild.lChild.rChild = new TreeNode(2);
    }

    private boolean hasPathSum(TreeNode node, int sum) {
        if (node == null) {
            return (sum == 0);
        } else {
            boolean ans = false;
            int subSum = sum - node.val;
            if (subSum == 0 && node.lChild == null && node.rChild == null) {
                return true;
            }
            if (node.lChild != null) {
                ans = ans || hasPathSum(node.lChild, subSum);
            }

            if (node.rChild != null) {
                ans = ans || hasPathSum(node.rChild, subSum);
            }
            return ans;
        }
    }


    @Test
    public void test(){
        Assert.assertTrue(hasPathSum(root,22));
    }


}
