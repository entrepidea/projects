package com.entrepidea.algo.leetcode.easy;

import com.entrepidea.algo.data_structure.tree.supports.TreeNode;
import com.entrepidea.algo.data_structure.tree.supports.TreeUtilities;
import org.junit.Test;

import java.util.*;
/**
 * @Desc: find out the mode in Binary Search Tree
 * @Source: https://leetcode.com/problems/find-mode-in-binary-search-tree/
 * @Date: 10/21/20
 * */
public class LE501FindModeInBST {

    private TreeNode root;
    private int[] findMode(TreeNode root){
        return null;
    }

    private TreeNode constructTree(){
        final int[] testArr = new int[]{1,-1,2,2};
        int len = testArr.length;
        TreeNode tempRoot = new TreeNode(testArr[0]);
        root = TreeUtilities.insertLevelNode(testArr,tempRoot,0,len);
        return root;
    }

    @Test
    public void inorderTranversalTest(){
        TreeNode root = constructTree();
        List<TreeNode> l = new ArrayList<>();
        TreeUtilities.inorder(root,l);
        l.forEach(x -> System.out.println(x.val));
    }

    public static void inorder(TreeNode node, Map<Integer, Integer> map){
        if(node==null){
            return;
        }
        inorder(node.lChild, map);
        //System.out.print(node.val);
        //l.add(node);
        Integer key = node.val;
        if(map.get(key)!=null){
            Integer count = map.get(key);
            count++;
            map.put(key, count);
        }
        else{
            map.put(key, 1);
        }
        inorder(node.rChild,map);
    }

    @Test
    public void test(){
        TreeNode root = constructTree();
        List<TreeNode> l = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        inorder(root,map);
        int max = map.values().stream().mapToInt(v -> v).max().orElseThrow(NoSuchElementException::new);
        map.keySet().stream().filter(v -> map.get(v)==max).forEach(System.out::println);

    }
}
