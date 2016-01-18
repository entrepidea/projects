//Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.
//
//According to the definition of LCA on Wikipedia: 
//“The lowest common ancestor is defined between two nodes v and w as the lowest node in T 
//that has both v and w as descendants (where we allow a node to be a descendant of itself).”
//
//        _______6______
//       /              \
//    ___2__          ___8__
//   /      \        /      \
//   0      _4       7       9
//         /  \
//         3   5
//For example, the lowest common ancestor (LCA) of nodes 2 and 8 is 6. 
//Another example is LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself 
//according to the LCA definition.

//source: https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/

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
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //Find the path from the root to the node.
        //Since this is a binary search tree, it is easier to find the path.
        //If it is an ordinary binary search tree, then we need to use recursive way to find the path.
        //Then trace back the lowest common ancestor of two nodes by
        //tracing back from each node to their ancestors.
        LinkedList<TreeNode> pathP = getPath(root, p);
        LinkedList<TreeNode> pathQ = getPath(root, q);
        
        TreeNode lca = getLowestCommonAncestor(pathP, pathQ);
        return lca;
    }
    
    LinkedList<TreeNode> getPath(TreeNode root, TreeNode n) {
        LinkedList<TreeNode> lst = new LinkedList<TreeNode>();
        if (root == null)
           return lst;
           
        //root != null
        TreeNode curr = root;
        lst.add(curr);
        while (curr != null && curr.val != n.val) {
            if (curr.val < n.val)
               curr = curr.right;
            else if (curr.val > n.val)
                    curr = curr.left;
                 
            lst.add(curr);
        }
        
        return lst;
    }
    
    TreeNode getLowestCommonAncestor(LinkedList<TreeNode> pathP, LinkedList<TreeNode> pathQ) {
        if (pathP == null || pathQ == null)
           return null;
           
        int idxP = pathP.size()-1;
        int idxQ = pathQ.size()-1;
        
        if (idxP > idxQ)
            idxP = idxQ;
        else if (idxQ > idxP)
                idxQ = idxP; 
         
        //Now idxQ = idxP.       
        TreeNode currP = pathP.get(idxP);
        TreeNode currQ = pathQ.get(idxQ);        
        
        while (currP != currQ) {
            idxP--;
            idxQ--;
            currP = pathP.get(idxP);
            currQ = pathQ.get(idxQ);
        }
            
        return currP;
    }
}
