package com.entrepidea.algo.leetcode.easy;

import com.entrepidea.algo.data_structure.tree.supports.TreeNode;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Source: https://leetcode.com/problems/invert-binary-tree/
 * @Desc: invert a tree.
 * Created by jonat on 11/6/2019.
 */
public class LE226InvertTree {

    private TreeNode invertTree(TreeNode root){
        if(root==null){
            return root;
        }
        TreeNode leftNode = invertTree(root.lChild);
        TreeNode rightNode = invertTree(root.rChild);
        root.lChild = rightNode;
        root.rChild = leftNode;
        return root;
    }

    private void BFTranverse(TreeNode root){
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            TreeNode node = q.poll();

            if(node!=null) {
                System.out.println(node.val);
                q.add(node.lChild);
                q.add(node.rChild);
            }
        }
    }

    @Test
    public void test(){
        TreeNode root = new TreeNode(4);
        TreeNode node = root;
        node.lChild = new TreeNode(2);
        node.rChild = new TreeNode(7);
        node.lChild.lChild = new TreeNode(1);
        node.lChild.rChild = new TreeNode(3);
        node.rChild.lChild = new TreeNode(6);
        node.rChild.rChild = new TreeNode(9);
        //original
        BFTranverse(root);

        root = invertTree(node);
        //inverse
        BFTranverse(root);

    }
}
