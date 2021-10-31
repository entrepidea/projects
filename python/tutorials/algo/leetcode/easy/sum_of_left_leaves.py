"""

Given the root of a binary tree, return the sum of all left leaves.
https://leetcode.com/problems/sum-of-left-leaves/

Date: 10/29/21

"""

import sys
sys.path.append('utils/')

from TreeUtils import TreeNode
from TreeUtils import insert_level_order

from typing import Optional

def add(node : Optional[TreeNode], rlt):
    if node is None:
        return 
    
    if node.left is not None:
        if node.left.data is not None:
            rlt.append(node.left.data)
        add(node.left, rlt)
    
    if node.right is not None:
        add(node.right,rlt)

if __name__ == '__main__':
    arr = [3,9,20,None,None,15,7]
    root = None
    root = insert_level_order(arr,root,0,len(arr))
    rlt = []
    add(root,rlt)
    print(sum(rlt))
