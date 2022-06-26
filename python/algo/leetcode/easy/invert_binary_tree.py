"""
invert binary tree
https://leetcode.com/problems/invert-binary-tree/
date: 10/19/21
"""

import sys
sys.path.append('utils/')

from TreeUtils import TreeNode
from TreeUtils import insert_level_order
from TreeUtils import inorder_traverse
from TreeUtils import preorder_traverse
from TreeUtils import postorder_traverse

def construct_tree(arr) -> TreeNode:
    if arr is None:
        return None
    if len(arr) == 1:
        return TreeNode(arr[0])
    node = None
    node = insert_level_order(arr,node,0,len(arr))
    
    return node

def invert(root : TreeNode) -> TreeNode:
    if root is None:
        return None
    left = invert(root.left)
    right = invert(root.right)
    root.left = right
    root.right = left
    return root

#breadth first search
def bfs(root : TreeNode):
    rlt = []
    q = []
    q.append(root)
    while len(q) !=0:
        level = []
        for i in range(len(q)):
            node = q.pop(0)
            #print(f'node\'s value : {node.data}')
            level.append(node.data)
            if node.left:
                q.append(node.left)
            if node.right:
                q.append(node.right)

        rlt.append(level)                            
    return rlt        

if __name__ == '__main__':
    arr = [4,2,7,1,3,6,9]
    head = construct_tree(arr)

    """
    print ('Before inverting, the tree looks like:')
    p = head
    #inorder traverse: left-parent-right
    print('inorder traverse', end=':')
    print(inorder_traverse(p))
    
    p = head
    #preorder traverse: parent-left-right
    print('preorder traverse', end=':')
    print(preorder_traverse(p))
    
    p = head
    #post traverse: left-right-parent
    print('postorder traverse', end=':')
    print(postorder_traverse(p))

    """

    p = head
    #breadth first search
    print('breadth first traverse', end =': ')
    print(bfs(p))

    p = head
    head = invert(p)
    p = head
    print('inverted tree', end =': ')
    print(bfs(p))


