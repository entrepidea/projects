package com.entrepidea.algo.leetcode.easy.tree;

import com.entrepidea.algo.data_structure.tree.supports.TreeNode;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @Souece: https://leetcode.com/problems/binary-tree-level-order-traversal-ii/
 * @Description: show a binary tree from bottom-up level order
 * @Date: 08/02/19
 * */
public class LE107BinaryTreeLevelOrderTraversalII {

    private TreeNode root;
    @Before
    public void setup(){
        root = new TreeNode(3);
        TreeNode curr = root;
        curr.lChild = new TreeNode(9);
        curr.rChild = new TreeNode(20);
        curr = curr.rChild;
        curr.lChild = new TreeNode(15);
        curr.rChild = new TreeNode(7);
    }

    @Test
    public void test(){
        //BFS
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        List<TreeNode> l = new ArrayList<TreeNode>(){
            {
                add(root);
            }
        };

        Map<Integer, List<TreeNode>> m = new HashMap<Integer, List<TreeNode> >(){{
            put(1, l);
        }};

        Map<TreeNode, Integer> m2 = new HashMap<TreeNode, Integer>(){
            {
                put(root, 1);
            }
        };

        TreeNode node;
        while((node=q.poll())!=null){
            if(node.lChild!=null){
                q.add(node.lChild);
                int level = m2.get(node);
                m2.putIfAbsent(node.lChild, level+1);
                List<TreeNode> list = m.get(level+1);
                if(list==null) {
                    list = new ArrayList<>();

                }
                list.add(node.lChild);
                m.put(level+1, list);
            }
            if(node.rChild!=null){
                q.add(node.rChild);
                int level = m2.get(node);
                m2.putIfAbsent(node.rChild, level+1);
                List<TreeNode> list = m.get(level+1);
                if(list==null) {
                    list = new ArrayList<>();

                }
                list.add(node.rChild);
                m.put(level+1, list);
            }
        }

        /*
        for(Iterator<Integer> iter = m.keySet().iterator();iter.hasNext();){
            Integer key = iter.next();
            System.out.print("level="+key+",value=");
            presentList(m.get(key));
        }*/

        List<Integer> list = m.keySet().stream().sorted((o1,o2) ->{ return o2-o1;}).collect(Collectors.toList());
        for (Integer level : list){
            System.out.print("level="+level+",value=");
            presentList(m.get(level));
        }
    }

    private void presentList(List<TreeNode> l){
        l.stream().forEach(System.out::println);
    }
}
