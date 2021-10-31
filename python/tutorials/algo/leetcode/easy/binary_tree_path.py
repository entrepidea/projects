"""
https://leetcode.com/problems/binary-tree-paths/

"""
import sys
sys.path.append('utils/')
from TreeUtils import TreeNode
from TreeUtils import insert_level_order
#from TreeUtils import inorder_traverse
#from TreeUtils import bfs

from typing import Optional

def path(root : Optional[TreeNode], rlt, s):
    if root == None:
        return
    if s=='':
        s += str(root.data)
    else:
        s = s+'=>'+str(root.data)
    
    if root.left == None and root.right == None:
        rlt.append(s)
    path(root.left,rlt,s)
    path(root.right,rlt,s)
    

if __name__ == '__main__':
    arr = [1,2,3,None,5]
    root = None
    root = insert_level_order(arr,root,0,len(arr))
    s=''
    rlt = []
    path(root,rlt,s)

    print(rlt)

    #print(root)
    #rlt = inorder_traverse(root)
    #print(rlt)
    #rlt = bfs(root)
    #print(rlt)
