"""
Given a n-ary tree, find its maximum depth.

The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.

Nary-Tree input serialization is represented in their level order traversal, each group of children is separated by the null value (See examples).

https://leetcode.com/problems/maximum-depth-of-n-ary-tree/


NOTE: Be noted that this tree is not a binary tree. TODO 



"""
#import sys
#sys.path.append('utils')
#from TreeUtils import TreeNode
#from TreeUtils import insert_level_order
#from TreeUtils import inorder_traverse
#from typing import Optional

class Node:
    def __init__(self, val):
        self.data = val
        self.arr = []
        pass

    def __repr__(self):
        return f'{self.data}'

    def __str__(self):
        return f'{self.data}'

    def add(self, node):
        self.arr.append(node)

def construct_tree_from_array()->Node:
    root = Node(1)
    n = Node(3)
    n1 = n
    root.add(n)
    n = Node(2)
    root.add(n)
    n = Node(4)
    root.add(n)
    n = Node(5)
    n1.add(n)
    n = Node(6)
    n1.add(n)
    return root

def traverse(node, tmp):
    if len(node.arr) == 0:         
        print(node)
        return
    tmp[0] = tmp[0] + 1
    print(node)
    for n in node.arr:
        traverse(n,tmp)


if __name__ == '__main__':
    root = construct_tree_from_array()
    tmp = [1]
    traverse(root,tmp)
    print(f'count={tmp[0]}')
    
