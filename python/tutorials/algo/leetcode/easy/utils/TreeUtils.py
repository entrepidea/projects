"""
a bunch of Tree related utilities

note: the code below is copied from: https://www.geeksforgeeks.org/construct-complete-binary-tree-given-array/

date: 10/09/21
"""
from typing import Optional

class TreeNode:
    def __init__(self, data):
        self.data = '' if data is None else data 
        self.left = self.right = None

# Function to convert an array to a binanry tree
def insert_level_order(arr, root, i, n):
     
    # Base case for recursion
    if i < n:
        temp = TreeNode(arr[i])
        root = temp
 
        # insert left child
        root.left = insert_level_order(arr, root.left,
                                     2 * i + 1, n)
 
        # insert right child
        root.right = insert_level_order(arr, root.right,
                                      2 * i + 2, n)
    return root        

def inorder_traverse(node : Optional[TreeNode])->[]:
    rlt = []
    in_order(node, rlt)
    return rlt

def in_order(node : Optional[TreeNode], rlt):
    if node is None:
        return
    in_order(node.left,rlt)
    rlt.append(node.data)
    in_order(node.right,rlt)

def preorder_traverse(node : Optional[TreeNode])->[]:
    rlt = []
    pre_order(node, rlt)
    return rlt

def pre_order(node : Optional[TreeNode], rlt):
    if node is None:
        return
    rlt.append(node.data)
    pre_order(node.left,rlt)
    pre_order(node.right,rlt)

def postorder_traverse(node : Optional[TreeNode])->[]:
    rlt = []
    post_order(node, rlt)
    return rlt

def post_order(node : Optional[TreeNode], rlt):
    if node is None:
        return
    post_order(node.left,rlt)
    post_order(node.right,rlt)
    rlt.append(node.data)

#https://stackoverflow.com/questions/952914/how-to-make-a-flat-list-out-of-a-list-of-lists
def flatten(t):
    return [item for sublist in t for item in sublist]

#breadth first search
def bfs(root : TreeNode)->[]:
    rlt = []
    q = []
    q.append(root)
    while len(q) > 0:
        level = []
        for i in range(len(q)):
            node = q.pop(0)
            if node:
                level.append(node.data)
                if node.left:
                    q.append(node.left)
                if node.right:
                    q.append(node.right)
        rlt.append(level)
    return flatten(rlt)

