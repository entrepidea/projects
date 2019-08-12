package com.entrepidea.algo.tests.tree;

import com.entrepidea.algo.tests.tree.supports.TreeNode;
import org.junit.Before;
import org.junit.Test;
import sun.reflect.generics.tree.Tree;

import java.util.*;

/**
 * @Source: https://leetcode.com/problems/balanced-binary-tree/
 * @Description:
 * To determine if a tree is a balanced binary tree.
 * My idea is that I do a BFS and determine the level and the height of each node. If a node found it has a sibling (another node of the the same level),
 * the height of these two nodes are compared and if the difference is greater than 1, we found a winner!
 *
 * @Date: 08/11/19
 *
 * */
public class LE110BalancedBTree {

    TreeNode root;
    @Before
    public void setup(){
        root = new TreeNode(3);
        TreeNode curr = root;
        curr.lChild = new TreeNode(9);
        curr.rChild = new TreeNode(20);
        curr = curr.rChild;
        curr.lChild = new TreeNode(15);
        curr.rChild = new TreeNode(7);
        //curr = curr.rChild;
        //curr.rChild = new TreeNode(13);
    }

    private int getHeight(TreeNode node){
        if (node==null){
            return 0;
        }

        int lh = getHeight(node.lChild);
        int rh = getHeight(node.rChild);
        return 1 + Math.max(lh, rh);
    }

    @Test
    public void testTreeHeight(){
        System.out.println(getHeight(root.rChild));
    }

    class LevelHeight{
        public int level;
        public int height;
        public LevelHeight(int l, int h){
            level = l;
            height = h;
        }
    }

    public boolean checkBalancedBinaryTree(){
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        TreeNode node;
        Map<TreeNode, LevelHeight> nodeLevelHeightMap = new HashMap<>();
        Map<Integer, List<TreeNode>> levelMap = new HashMap<>();
        nodeLevelHeightMap.put(root, new LevelHeight(1, getHeight(root)));
        List<TreeNode> l = new ArrayList<>();
        l.add(root);
        levelMap.put(1,l);
        while((node = q.poll())!=null){
            if(node.lChild!=null){
                if(nodeLevelHeightMap.get(node.lChild)==null){
                    int height = getHeight(node.lChild);
                    int level = nodeLevelHeightMap.get(node).level;
                    level +=1;
                    nodeLevelHeightMap.put(node.lChild, new LevelHeight(level, height));
                    if(levelMap.get(level)!=null){ //found siblings.
                        List<TreeNode> siblings = levelMap.get(level);
                        for(Iterator<TreeNode> iter = siblings.iterator();iter.hasNext();){
                            TreeNode n = iter.next();
                            int h = nodeLevelHeightMap.get(n).height;
                            int h2 = nodeLevelHeightMap.get(node.lChild).height;
                            if((Math.abs(h2-h))>1){
                                return true;
                            }
                        }
                        siblings.add(node.lChild);
                        levelMap.put(level, siblings);
                    }
                    else{ //not found siblings
                        List<TreeNode> li = new ArrayList<>();
                        li.add(node.lChild);
                        levelMap.put(level, li);
                    }
                }
                q.add(node.lChild);
            }
            if(node.rChild!=null){
                if(nodeLevelHeightMap.get(node.rChild)==null){
                    int height = getHeight(node.rChild);
                    int level = nodeLevelHeightMap.get(node).level;
                    level +=1;
                    nodeLevelHeightMap.put(node.rChild, new LevelHeight(level, height));
                    if(levelMap.get(level)!=null){ //found siblings.
                        List<TreeNode> siblings = levelMap.get(level);
                        for(Iterator<TreeNode> iter = siblings.iterator();iter.hasNext();){
                            TreeNode n = iter.next();
                            int h = nodeLevelHeightMap.get(n).height;
                            int h2 = nodeLevelHeightMap.get(node.rChild).height;
                            if((Math.abs(h2-h))>1){
                                return true;
                            }
                        }
                        siblings.add(node.rChild);
                        levelMap.put(level, siblings);
                    }
                    else{
                        List<TreeNode> li = new ArrayList<>();
                        li.add(node.rChild);
                        levelMap.put(level, li);
                    }
                }
                q.add(node.rChild);
            }
        }

        return false;
    }

    @Test
    public void test(){
        if(checkBalancedBinaryTree()){
            System.out.println("Found!");
        }
        else{
            System.out.println("Not Found!");
        }
    }
}
