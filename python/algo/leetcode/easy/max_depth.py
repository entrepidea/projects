"""
find the max depth of a tree - the longest path from root to a leaf.
https://leetcode.com/problems/maximum-depth-of-binary-tree/

date: 10/09/21

"""

import sys
sys.path.append('utils/')

from TreeUtils import TreeNode
from TreeUtils import insert_level_order

from typing import Optional
def max_depth(p : Optional[TreeNode]) -> int:
    if p is None:
        return 0
    left = max_depth(p.left)
    right = max_depth(p.right)
    return max(left,right)+1

if __name__ == '__main__':
    #arr = [3,9,20,None,None,15,7]
    #arr = [1,None,2]
    arr = [1]
    p = None
    p = insert_level_order(arr,p,0,len(arr))
    print(max_depth(p))
