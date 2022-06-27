"""
Given the root of a Binary Search Tree (BST), return the minimum absolute difference between the values of any two different nodes in the tree.
https://leetcode.com/problems/minimum-absolute-difference-in-bst/

NOTE: inorder searching also sort the tree if the tree is BST. So we can just get the diff from adjacent elements and compare it with the set min. Idea is from: https://leetcode.com/problems/minimum-absolute-difference-in-bst/discuss/1549518/Python-Easy-97.5-faster-89.65-less-space.-Inorder-sorted

Date: 11/05/21

"""

import sys
sys.path.append('utils/')
from TreeUtils import TreeNode
from TreeUtils import insert_level_order
from typing import Optional
def abs_diff(node : Optional[TreeNode])->int:
    #python3's int is unbounded, below is from python2.
    #https://stackoverflow.com/questions/7604966/maximum-and-minimum-values-for-ints
    #diff = sys.maxint
    arr = []
    def inorder(node :Optional[TreeNode]):
        if node is None:
            return
        inorder(node.left)
        arr.append(node.data)
        inorder(node.right)
    
    inorder(node) #the tree is BST, meaning it's already sorted if searched inorder.
    if len(arr)==2:
        return abs(arr[1]-arr[0])
    minabs = abs(arr[1] - arr[0])
    for x in range(2, len(arr)):
        if abs(arr[x] - arr[x-1])<minabs:
            minabs = abs(arr[x]-arr[x-1])
                
    return minabs

if __name__ == '__main__':
    arr = [4,2,6,1,3]
    root = None
    root = insert_level_order(arr,root,0,len(arr))
    print(abs_diff(root))
