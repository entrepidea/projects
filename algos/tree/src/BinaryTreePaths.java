//Given a binary tree, return all root-to-leaf paths.
//
//For example, given the following binary tree:
//
//   1
// /   \
//2     3
// \
//  5
//
//All root-to-leaf paths are:
//
//["1->2->5", "1->3"]

//source: https://leetcode.com/problems/binary-tree-paths/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> lst =new ArrayList<String>();
        if (root == null)
           return lst;

        //Warning: tree has only one node.
        //Then root.left and root.right both return empty list.           
        if (root.left == null && root.right == null) {
            lst.add(root.val + "");
            return lst;
        }
           
        List<String> left = binaryTreePaths(root.left);
        String str;
        for (int i = 0; i < left.size(); i++) {
            str = left.get(i);
            lst.add(root.val + "->" + str);
        }
        
        List<String> right = binaryTreePaths(root.right);
        for (int i = 0; i < right.size(); i++) {
            str = right.get(i);
            lst.add(root.val + "->" + str);
        }
          
        return lst;  
    }
}
