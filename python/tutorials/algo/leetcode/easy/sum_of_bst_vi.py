"""
Given the root of a Binary Search Tree and a target number k, return true if there exist two elements in the BST such that their sum is equal to the given target.
https://leetcode.com/problems/two-sum-iv-input-is-a-bst/

NOTE: the idea is to BFS traverse the tree and save the values of the nodes to a temp array (complements) for comparison.

Date: 11/27/21

"""
import sys
sys.path.append('utils/')
from TreeUtils import TreeNode
from TreeUtils import insert_level_order
from TreeUtils import bfs
from typing import Optional

def bst_sum(node: Optional[TreeNode], k : int) ->bool:
	complements = []
	q = []
	q.append(node)
	while len(q) > 0:
		for i in range(len(q)):
			node = q.pop(0)
			if node:
				#print(node.data)
				if node.data != '':
					val = k - node.data
					if val in complements:
						#print(complements)
						return True
					else:
						complements.append(node.data)
				if node.left is not None:
					q.append(node.left)
				if node.right is not None:
					q.append(node.right)
		
	return False


if __name__ == '__main__':
	arr = [5,3,6,2,4,None,7]
	k = 9
	root = None
	root = insert_level_order(arr, root, 0, len(arr))
	print(bst_sum(root,k))

	arr = [5,3,6,2,4,None,7]
	k = 28
	root = None
	root = insert_level_order(arr, root, 0, len(arr))
	print(bst_sum(root,k))
	
	arr = [2,1,3]
	k = 4
	root = None
	root = insert_level_order(arr, root, 0, len(arr))
	print(bst_sum(root,k))
	
	arr = [2,1,3]
	k = 1
	root = None
	root = insert_level_order(arr, root, 0, len(arr))
	print(bst_sum(root,k))
