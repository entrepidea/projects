package com.entrepidea.algo.leetcode.easy;

import com.entrepidea.algo.data_structure.tree.supports.TreeNode;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/**
 * TODO
 * */
public class LE437PathSumIII {
    private TreeNode root;

    //below construct a tree out of a list.
    @Before
    public void setup(){

        List<Integer> list = Arrays.stream(new Integer[]{10,5,-3,3,2,null,11,3,-2,null,1}).collect(Collectors.toList());
        List<TreeNode> nodeList = new ArrayList<>();

        root = new TreeNode(list.remove(0));
        nodeList.add(root);
        while(!list.isEmpty()){
            TreeNode node = nodeList.remove(0);
            Integer ele = list.remove(0);
            if(ele!=null){
                node.lChild = new TreeNode(ele);
                nodeList.add(node.lChild);
            }
            if(list.isEmpty()) break;
            ele = list.remove(0);
            if(ele!=null){
                node.rChild = new TreeNode(ele);
                nodeList.add(node.rChild);
            }
        }
    }

    private void inOrder(TreeNode root){
        TreeNode node = root;
        if(node==null){
            return;
        }
        System.out.println(node.val);
        inOrder(node.lChild);
        inOrder(node.rChild);
    }

    @Test
    public void testInorderTraverse(){
        inOrder(root);
    }

}
