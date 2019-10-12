package com.entrepidea.algo.tests.tree.supports;

public class TreeNode {
    public int val;
    public TreeNode lChild;
    public TreeNode rChild;

    public TreeNode(){
        this(-1,null,null);
    }

    public TreeNode(int val, TreeNode l, TreeNode r){
        this.val = val;
        lChild = l;
        rChild = r;
    }

    public TreeNode(int val){
        this(val,null,null);
    }

    @Override
    public String toString(){
        return new Integer(val).toString();
    }
}