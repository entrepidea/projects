package com.entrepidea.algo.leetcode.easy;

import com.entrepidea.algo.data_structure.tree.supports.TreeNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class LE590MinimumDiffBST {

    private List<TreeNode> BFT(TreeNode root){
        BlockingQueue<TreeNode> q = new ArrayBlockingQueue<>(10);
        List<TreeNode> ret = new ArrayList<>();
        q.add(root);
        TreeNode temp = root;
        while(q.peek()!=null){
            ret.add(q.poll());
            if(temp.lChild!=null){
                q.add(temp.lChild);
            }
        }
        return ret;
    }

    private void DFT(TreeNode root, List<TreeNode> l){
        TreeNode temp = root;
        if(temp==null){
            return;
        }
        l.add(temp);
        DFT(temp.lChild,l);
        DFT(temp.rChild,l);
    }

    private TreeNode constructTree(){
        TreeNode root = new TreeNode(1);
        root.rChild = new TreeNode(3);
        root.rChild.lChild = new TreeNode(2);
        return root;

    }



    private int diff(TreeNode root){
        List<TreeNode> l = new ArrayList<>();
        DFT(root,l);
        l.sort(Comparator.comparingInt(x -> x.val));
        l.forEach(System.out::println);
        int min = l.get(l.size()-1).val;
        for(int i=0;i<l.size()-1;i++){
            int diff = Math.abs(l.get(i).val-l.get(i+1).val);
            if(diff<min){
                min  =diff;
            }
        }
        return min;
    }

    @Test
    public void testTraversal(){
        TreeNode root = constructTree();
        ///List<TreeNode> l = BFT(root);
        List<TreeNode> l = new ArrayList<>();
        DFT(root,l);
        l.forEach(System.out::println);
    }

    @Test
    public void test(){
        TreeNode root = constructTree();
        Assert.assertEquals(1, diff(root));
    }
}
