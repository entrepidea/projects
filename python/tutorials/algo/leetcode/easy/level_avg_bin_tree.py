"""
To find out the avergage of each level of a binary tree
https://leetcode.com/problems/average-of-levels-in-binary-tree/

NOTE: code below use breadth first search approach. Use api from statistics package to calculate avergage. 
Or you can use lambda and reduce for the same purpose. See also: https://www.geeksforgeeks.org/find-average-list-python/

Date: 11/23/21


"""
import sys
sys.path.append('utils/')

from TreeUtils import TreeNode
from TreeUtils import insert_level_order
from TreeUtils import bfs

from typing import Optional

from statistics import mean

def level_avg(node : Optional[TreeNode]) ->[float]:
	rlt = []
	q = []
	q.append(node)
	while len(q)>0:
		level = []
		for i in range(len(q)):
			node = q.pop(0)
			if node:
				if node.data != '':
					level.append(node.data)
				if node.left:
					q.append(node.left)
				if node.right:
					q.append(node.right)

		#print(level)

		rlt.append(mean(level))

	return rlt

if __name__ == '__main__':
	arr = [3,9,20,None,None,15,7]
	root = None
	root = insert_level_order(arr, root, 0, len(arr))
	print(level_avg(root))

	arr = [3,9,20,15,7]
	root = None
	root = insert_level_order(arr, root, 0, len(arr))
	print(level_avg(root))

