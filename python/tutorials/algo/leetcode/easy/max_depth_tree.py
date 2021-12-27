"""
Given a n-ary tree, find its maximum depth.

The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.

Nary-Tree input serialization is represented in their level order traversal, each group of children is separated by the null value (See examples).

https://leetcode.com/problems/maximum-depth-of-n-ary-tree/


NOTE: Be noted that this tree is not a binary tree. TODO 



"""
import sys
sys.path.append('utils')
from TreeUtils import TreeNode
from TreeUtils import insert_level_order
from TreeUtils import inorder_traverse
from typing import Optional

def depth(root : Optional[TreeNode]) -> int:
    pass

if __name__ == '__main__':
    arr = [1,None,3,2,4,None,5,6]
    root = None
    root = insert_level_order(arr,root,0,len(arr))
    print(inorder_traverse(root))
    #print(depth(root))
