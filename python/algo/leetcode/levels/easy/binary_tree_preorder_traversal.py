"""
preorder traversal of a binary tree
https://leetcode.com/problems/binary-tree-preorder-traversal/

date: 10/11/21

note: the result is not correct. TODO

"""
import sys
sys.path.append('utils/')

from TreeUtils import TreeNode
from TreeUtils import insert_level_order
from TreeUtils import preorder_traverse
from TreeUtils import inorder_traverse

from typing import Optional

def preorder(node : Optional[TreeNode], rlt):
    if node is None:
        return 
    print(f'node.data={node.data}')
    rlt.append(node.data)
    preorder(node.left, rlt)
    preorder(node.right, rlt)

if __name__ == '__main__':
    arr = [1,None,2,3]
    node = None
    node = insert_level_order(arr,node,0,len(arr))
    rlt = []
    preorder(node, rlt)
    print(rlt)
