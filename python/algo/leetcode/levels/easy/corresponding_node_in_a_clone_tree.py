"""
Given two binary trees original and cloned and given a reference to a node target in the original tree.

The cloned tree is a copy of the original tree.

Return a reference to the same node in the cloned tree.

Note that you are not allowed to change any of the two trees or the target node and the answer must be a reference to a node in the cloned tree.

https://leetcode.com/problems/find-a-corresponding-node-of-a-binary-tree-in-a-clone-of-that-tree/

NOTE: this script used a python package: binarytree, in lieu of the one I created in the 'util' package.

11/25/22

"""

from binarytree import build
from binarytree import Node

#https://leetcode.com/problems/find-a-corresponding-node-of-a-binary-tree-in-a-clone-of-that-tree/discuss/2046988/Python%3A-Double-DFS-Intuition-Easily-Explained-with-time-and-space-complexity
#details of explanation.
def get_target_copy(original : Node, cloned : Node, target : Node) -> Node:
	def dfs(root, clone):
		if not root:
			return None
		if root == target:
			return clone
		l = dfs(root.left, clone.left)
		if l:
			return l
		r = dfs(root.right, clone.right)
		if r:
			return r

		return None

	return dfs(original, cloned)					

if __name__ == '__main__':
	values = [7,4,3,None,None,6,19]
	orig = build(values)
	cloned = orig.clone()

	print('\nthe original tree looks like: ')	
	print(orig)

	print('the cloned tree - looks exactly the same to the original: ')
	print(cloned)

	print('pre order travesal of the tree: ')
	print(orig.preorder)

	#specify a target node - pick the 3rd one.
	target = orig.preorder[2]
	print(f'pick up the 3rd node: {target.value}')

	node = get_target_copy(orig, cloned, target)

	#print(node.value)
	print(f'find the same node in the cloned tree: {node.value}')
	
	
