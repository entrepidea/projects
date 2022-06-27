"""
inorder traverse a binary tree
https://leetcode.com/problems/binary-tree-inorder-traversal/

date: 10/09/21


"""
import sys
sys.path.append('utils/')

from TreeUtils import TreeNode
from TreeUtils import insert_level_order

def in_order(root):
	if root != None:
		in_order(root.left)
		print(root.data,end=" ")
		in_order(root.right)

if __name__ == '__main__':
	arr = [1, 2, 3, 4, 5, 6, 6, 6, 6]
	n = len(arr)
	root = None
	root = insert_level_order(arr, root, 0, n)
	in_order(root)
