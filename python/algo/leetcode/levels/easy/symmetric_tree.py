"""
A symmetrical tree is a tree mirroring itself.
https://leetcode.com/problems/symmetric-tree/

date: 10/09/21

note: inorder traversal and load into an array and compare the data using ith == (len - i -1)th

"""

import sys
sys.path.append('utils/')

from TreeUtils import TreeNode
from TreeUtils import insert_level_order

from typing import Optional
def is_symmetric(p : Optional[TreeNode]) -> bool:
    rlt = []
    in_order(p, rlt)
    print(rlt)
    for i in range(0,len(rlt)):
        if rlt[i] != rlt[len(rlt)-1-i]:
            return False
    return True

def in_order(p : Optional[TreeNode], rlt):
    if p is not None:
        in_order(p.left, rlt)
        rlt.append(p.data)
        in_order(p.right,rlt)
        
if __name__ == '__main__':
    
    arr = [1,2,2,3,4,4,3]
    p = None
    p = insert_level_order(arr,p,0,len(arr))
    print(is_symmetric(p))

    arr = [1,2,2,None,3,None,3]
    p = None
    p = insert_level_order(arr,p,0,len(arr))
    print(is_symmetric(p))

