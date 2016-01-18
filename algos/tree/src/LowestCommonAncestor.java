//Lowest common ancestor of a binary tree
//Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

//According to the definition of LCA on Wikipedia: 
//“The lowest common ancestor is defined between two nodes v and w 
//as the lowest node in T that has both v and w as descendants 
//(where we allow a node to be a descendant of itself).”
//
//        _______3______
//       /              \
//    ___5__          ___1__
//   /      \        /      \
//   6      _2       0       8
//         /  \
//         7   4
//
//For example, the lowest common ancestor (LCA) of nodes 5 and 1 is 3. 
//Another example is LCA of nodes 5 and 4 is 5, 
//since a node can be a descendant of itself according to the LCA definition.

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

//Key: find the path from the root to each node,
//if both paths are of the same length,
//then search from bottom to up until we get a common ancestor.
//If the paths are not of the same length,
//we need to truncate the longer path to the same length as the shorter path.
//
//Since the parent info of a node is not recorded,
//we cannot start from height, then trace back to ancestors.

public class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        LinkedList<TreeNode> pathP = pathToNode(root, p);
        LinkedList<TreeNode> pathQ = pathToNode(root, q);
        
        int sizePathP = pathP.size();
        int sizePathQ = pathQ.size();
        
        if (sizePathP == sizePathQ)
           return findAncestor(pathP, pathQ);
        else if (sizePathP < sizePathQ) {
                while (pathQ.size() > sizePathP)
                      pathQ.removeLast();
                //pathQ has the same size as pathQ now.         
                if (pathQ.get(sizePathP-1) == p) //p is an ancestor of q
                   return p;
                else return findAncestor(pathP, pathQ);
        }
        else {
              while (pathP.size() > sizePathQ)
                    pathP.removeLast();
                    
              if (pathP.get(sizePathQ-1) == q)
                 return q;
              else return findAncestor(pathP, pathQ);
        }
    }
    
    LinkedList<TreeNode> pathToNode(TreeNode root, TreeNode n) {
        LinkedList<TreeNode> path = new LinkedList<TreeNode>();
        
        if (root == null)
           return path;
        
        if (root == n) {
            path.add(root);
            return path;
        }
        
        //root != n
        LinkedList<TreeNode> leftPath = pathToNode(root.left, n);
        if (leftPath.size() > 0) {
            leftPath.addFirst(root);
            return leftPath;
        }
        else {
              LinkedList<TreeNode> rightPath = pathToNode(root.right, n);
              //KEY: the following if-else part is needed.
              //otherwise, for example like
              //   1
              //  / \
              // 2   3
              //When we run pathToNode(1, 3),
              //which calls leftPath = pathToNode(2, 3);
              //which calls leftPath = pathToNode(the left child of 2, 3),
              //so leftPath = []
              //but if omit the if part, directly use
              // rightPath.addFirst(root);
              //   return rightPath;
              //then rightPath = [2] for pathToNode(2, 3);
              //we think the node is not in left tree, must be in right tree,
              //but this assumption might not be true 
              //when root does not contain node in its subtree,
              //as shown in the above example when we run pathToNode(2, 3).
              if (rightPath.size() > 0) {
                 rightPath.addFirst(root);
                 return rightPath;
              }
              else return path;
        }
    }
    
    //Find the lowest common ancestor of two paths of the same length
    TreeNode findAncestor(LinkedList<TreeNode> pathP, LinkedList<TreeNode> pathQ) {
        TreeNode lastPathP = pathP.get(pathP.size()-1);
        TreeNode lastPathQ = pathQ.get(pathQ.size()-1);
        
        while (lastPathP != lastPathQ) {
            pathP.removeLast();
            pathQ.removeLast();
            lastPathP = pathP.get(pathP.size()-1);
            lastPathQ = pathQ.get(pathQ.size()-1);
        }
        return lastPathP;
    }
}
