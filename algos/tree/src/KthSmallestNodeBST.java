//Given a binary search tree, write a function kthSmallest to 
//find the kth smallest element in it.
//
//Note: 
//You may assume k is always valid, 1 ≤ k ≤ BST's total elements.

//Follow up:
//What if the BST is modified (insert/delete operations) often 
//and you need to find the kth smallest frequently? 
//How would you optimize the kthSmallest routine?

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

//O(n) time, O(1) space, appropriate for frequent update case:
//use recursion and the number of descendants
//to reduce either the tree or value k.
public class Solution {
    public int kthSmallest(TreeNode root, int k) {
        //bug: with the following statement,
        //we get wrong answer: for tree [2,1] and k=1,
        //the return is 2
        //if (k == 1) //By assumption, k is always valid
        //   return root.val;
        //But when k = 1, root might contain more than one node.
        //Originally, I add this statement to handle
        //the base case of recursion of kthSmallest.
        //if the root has exactly one node, by assumption of k's validity,
        //k can only be 1.
        //But k = 1 does not mean that root has only one node,
        //it only implies that the tree can have at least one node.
        
        //I over-reacted. In fact, if k is always valid,
        //then root.right and root.left cannot be null.
        int numLD = numDescendants(root.left); //num of left descendants
        int numRD = numDescendants(root.right); //num of right descendants
        if (numLD == k-1)
           return root.val;
        else if (numLD < k-1)
                return kthSmallest(root.right, k-numLD-1);//1 indicates root
             else //numLD >= k
                  return kthSmallest(root.left, k);
    }
    
    int numDescendants(TreeNode root) {
        if (root == null)
           return 0;
          
        //The following formula includes the case of single-node tree.
        return numDescendants(root.left) + 1 + numDescendants(root.right);
    }
}

//O(n) time, O(n) space, and not appropriate for frequent update case.
//idea: put in-order traversal in a linked list,
//then search list for the kth item.
//In the case of frequent insertion/deletion,
//remove the rightmost node, if it is a leaf, done,
//otherwise (the node must have a left branch),
//use the left branch's rightmost descendent to
//replace that node.
//problem for the above approach:
//we delete instead of search the kth element
//in the binary search tree.

public class Solution {
    public int kthSmallest(TreeNode root, int k) {
        LinkedList<TreeNode> lst = inorder(root);
        return kthSmallest(lst, k);
    }
    
    //inorder traverse a binary search tree,
    //put the result in a linked list.
    LinkedList<TreeNode> inorder(TreeNode root) {
        LinkedList<TreeNode> lst = new LinkedList<TreeNode>();
        if (root == null)
           return lst;
           
        LinkedList<TreeNode> left = inorder(root.left);
        LinkedList<TreeNode> right = inorder(root.right);
        
        left.add(root);
        left.addAll(right);
        return left;
    }
    
    //Find the kth smallest element in a linked list.
    int kthSmallest(LinkedList<TreeNode> lst, int k) {
        int i = 1;
        TreeNode curr = lst.get(i-1);
        while (i < k) {
            i++;
            curr = lst.get(i-1);
        }
        
        return curr.val;
    }
}
