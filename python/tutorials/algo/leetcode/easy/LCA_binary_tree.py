"""
https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
TODO this is deferred to later.

"""
import sys
sys.path.append('utils/')

from TreeUtils import insert_level_order
from TreeUtils import TreeNode
from TreeUtils import inorder_traverse
from TreeUtils import bfs

def lca(root,p,q):
    pass

if __name__ == '__main__':
    arr = [6,2,8,0,4,7,9,None,None,3,5]
    root = None
    root = insert_level_order(arr,root,0,len(arr))
    rlt = inorder_traverse(root)
    rlt = bfs(root)
    print(rlt)
		#p = 2
		#q = 8
		#lca(root,p,q)

