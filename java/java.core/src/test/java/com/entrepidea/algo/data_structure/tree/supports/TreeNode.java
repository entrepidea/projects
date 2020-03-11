package com.entrepidea.algo.data_structure.tree.supports;

public class TreeNode {
    public Integer val;
    public TreeNode lChild;
    public TreeNode rChild;

    public TreeNode(){
        this(-1,null,null);
    }

    public TreeNode(Integer val, TreeNode l, TreeNode r){
        this.val = val;
        lChild = l;
        rChild = r;
    }

    public TreeNode(Integer val){
        this(val,null,null);
    }

    @Override
    public String toString(){
        return val!=null?val.toString():"null";
    }
}