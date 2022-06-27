"""
determine if a tree is balance
https://leetcode.com/problems/balanced-binary-tree/

date: 10/10/21

note: pay attention to the comments below.

"""

import sys
sys.path.append('utils/')

from typing import Optional

def max_depth(node):
    if node is None or node.data is None: #when the node's data is None, we treat the node as a None node too, otherwise we can't get the right answer.
        return 0
    left = max_depth(node.left)
    #print(f'val={node.data},depth={left}')
    
    right = max_depth(node.right)
    #print(f'val={node.data},depth={right}')

    if left == -1 or right == -1 or abs(left - right)>1:
        return -1
    return max(left,right)+1

def max_depth2(node):
    if not node or node.data is None:
        return 0
    left = max_depth2(node.left)
    if left == -1:
        return -1
    right = max_depth2(node.right)
    if right == -1:
        return -1
    return -1 if abs(left - right) > 1 else max(left, right) + 1

def is_balance(node) -> bool:
    return max_depth2(node) != -1

from TreeUtils import inorder_traverse
from TreeUtils import preorder_traverse
from TreeUtils import postorder_traverse
from TreeUtils import insert_level_order

if __name__ == '__main__':

    arr = [1,2,3,4,5,None,None,6,7]    
    node = None
    node = insert_level_order(arr, node, 0, len(arr))
    print(is_balance(node))

    arr = [3,9,20,None,None,15,7]
    node = None
    node = insert_level_order(arr, node, 0, len(arr))
    print(is_balance(node))
