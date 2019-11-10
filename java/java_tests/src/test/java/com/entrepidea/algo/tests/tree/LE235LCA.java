package com.entrepidea.algo.tests.tree;

import com.entrepidea.algo.tests.tree.supports.TreeNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Source: https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
 * @Desc: Lowest Common Ancestor of a Binary Search Tree
 * @Note: BST's left is always less than or equal to the parent, right greater than or equal to the parent, this is the base of algorithm
 * Created by jonat on 11/7/2019.
 */
public class LE235LCA {
    private TreeNode lca(TreeNode node, int x, int y){
        if(node==null){
            return node;
        }
        if(x<=node.val && y>=node.val || x>=node.val && y<=node.val){
            return node;
        }
        if(x<=node.val && y<=node.val){
            return lca(node.lChild,x,y);
        }
        if(x>=node.val && y>=node.val){
            return lca(node.rChild,x,y);
        }
        return null;
    }

    private TreeNode TreeFromArray(Integer[] arr){
        Queue<Integer> q = new LinkedList<>();
        for(Integer x: arr){
            q.add(x);
        }
        Queue<TreeNode> parentQ = new LinkedList<>();
        TreeNode root = new TreeNode(q.poll());
        parentQ.add(root);
        while(!q.isEmpty()){
            TreeNode parent = parentQ.poll();
            TreeNode l = new TreeNode(q==null?null:q.poll());
            TreeNode r = new TreeNode(q==null?null:q.poll());
            parent.lChild = l;
            parent.rChild = r;
            parentQ.add(l);
            parentQ.add(r);
        }
        return root;
    }

    private void displayTree(TreeNode root){
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            TreeNode node = q.poll();
            System.out.print(node+", ");
            if(node.lChild!=null) {
                q.add(node.lChild);
            }
            if(node.rChild!=null) {
                q.add(node.rChild);
            }

            System.out.println();
        }
    }

    @Test
    public void testConstructTreeFromArray(){
        TreeNode root = TreeFromArray(new Integer[]{6,2,8,0,4,7,9,null,null,3,5});
        displayTree(root);
    }

    @Test
    public void testLCA(){
        TreeNode root = TreeFromArray(new Integer[]{6,2,8,0,4,7,9,null,null,3,5});
        Assert.assertEquals(6, lca(root,2,8).val.intValue());
        Assert.assertEquals(2, lca(root,2,4).val.intValue());
        Assert.assertEquals(2, lca(root,0,5).val.intValue());
        Assert.assertEquals(4, lca(root,3,5).val.intValue());
        Assert.assertEquals(6, lca(root,0,9).val.intValue());
        Assert.assertEquals(6, lca(root,4,7).val.intValue());
    }
}
