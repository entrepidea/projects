"""
find the minimum depth of a binary tree
https://leetcode.com/problems/minimum-depth-of-binary-tree/

date: 10/10/21


"""
import sys
sys.path.append('utils/')

from typing import Optional

from TreeUtils import TreeNode
from TreeUtils import insert_level_order

def min_path(node : Optional[TreeNode]):
    if node is None or node.data is None:
        return 0
    left = min_path(node.left)
    right = min_path(node.right)
    return min(left,right)+1

if __name__ == '__main__':
    arr = [3,9,20,None,None,15,7]
    node = None
    node = insert_level_order(arr,node, 0, len(arr))
    print(min_path(node))

    arr = [2,None,3,None,4,None,5,None,6]
    node = None
    node = insert_level_order(arr,node, 0, len(arr))
    print(min_path(node))

