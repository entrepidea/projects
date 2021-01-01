package com.entrepidea.algo.leetcode.easy.tree;

import com.entrepidea.algo.data_structure.tree.supports.TreeNode;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Source: https://leetcode.com/problems/maximum-depth-of-binary-tree/
 * @Description: to find out the max of a binary tree's depth
 * @Date: 08/02/19
 *
 * */
public class LE104BinaryTreeMaxDepth {


    TreeNode root;
    @Before
    public void setUp() throws Exception {

        //construct a tree
        /**
          1
         / \
        2   3
       / \   \
      4   5   6
         /
        7

         **/

        TreeNode curr;
        root = new TreeNode(1);
        TreeNode node = new TreeNode(2);
        root.lChild = node;
        node = new TreeNode(3);
        root.rChild = node;
        //curr = root.lChild;
        //node = new TreeNode(4);
        //curr.lChild = node;
        //node = new TreeNode(5);
        //curr.rChild = node;
        //curr = root.rChild;
        //curr.rChild = new TreeNode(6);
        //curr = curr.rChild;
        //curr.lChild = new TreeNode(7);
    }
    @Test
    public void testTreeDepth(){
        //first do a BFS, add a counter variable
        Queue<TreeNode> q = new ArrayBlockingQueue<>(16);
        q.add(root);
        TreeNode node;
        Map<TreeNode, Integer> m = new HashMap<>();
        List<Integer> l = new ArrayList<>();
        m.putIfAbsent(root,1);
        while((node = q.poll())!=null){
            if(node.lChild!=null){
                int level = m.get(node);
                m.putIfAbsent(node.lChild, level+1);
                q.add(node.lChild);
            }
            if(node.rChild!=null){
                int level = m.get(node);
                m.putIfAbsent(node.rChild, level+1);
                q.add(node.rChild);
            }
        }
        int max = 0;
        for(Integer v: m.values()){
            if (v>max){
                max = v;
            }
        }

        System.out.println(max);
    }
}
