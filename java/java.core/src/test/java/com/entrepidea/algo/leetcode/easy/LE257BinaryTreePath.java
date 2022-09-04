package com.entrepidea.algo.leetcode.easy;

import com.entrepidea.algo.data_structure.tree.supports.TreeNode;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * @Desc: exhaust all the paths of a binary tree.
 * @Source: https://leetcode.com/problems/binary-tree-paths/
 * @Date: 12/13/19
 *
 * */

public class LE257BinaryTreePath {

    private TreeNode root;

    @Before
    public void setUp() {

        //construct a tree
        /**

          1
         / \
        2   3
       / \   \
      4  5    6

         **/

        TreeNode curr;
        root = new TreeNode(1);
        TreeNode node = new TreeNode(2);
        root.lChild = node;
        node = new TreeNode(3);
        root.rChild = node;
        curr = root.lChild;
        node = new TreeNode(4);
        curr.lChild = node;
        node = new TreeNode(5);
        curr.rChild = node;
        curr = root.rChild;
        curr.rChild = new TreeNode(6);
    }

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        preorderPath(root, "", paths);
        return paths;
    }

    private void preorderPath(TreeNode root, String currPath, List<String> paths) {
        if(root == null) {
            return;
        }
        currPath += currPath.length() == 0 ? root.val : "->" + root.val;
        // if not a leaf node, continue with pre-order traversal
        if(root.lChild != null || root.rChild != null)  {
            preorderPath(root.lChild, currPath, paths);
            preorderPath(root.rChild, currPath, paths);
        } else { // current traversal reaches leaf node, add to existing paths
            paths.add(currPath);
        }
    }

    @Test
    public void test(){
        List<String> l =binaryTreePaths(root);
        for (String s : l){
            System.out.println(s);
        }
    }
}
