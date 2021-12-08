"""
Given a non-empty special binary tree consisting of nodes with the non-negative value, where each node in this tree has exactly two or zero sub-node. If the node has two sub-nodes, then this node's value is the smaller value among its two sub-nodes.
https://leetcode.com/problems/second-minimum-node-in-a-binary-tree/

Date: 12/02/21

"""
import sys
sys.path.append('utils/')

from TreeUtils import TreeNode
from TreeUtils import insert_level_order
from TreeUtils import bfs

from typing import Optional

def sec_min(root : Optional[TreeNode]) -> int:
    q = []
    q.append(root)
    rlt = []
    while len(q)>0:
        for i in range(len(q)):
            level = []
            node = q.pop()
            if node is not None:
                if node.left is not None and node.left.data != '':
                    level.append(node.left.data)
                if node.right is not None and node.right.data != '':
                    level.append(node.right.data)

        for i in level:
            if len(rlt)<2:
                rlt.append(i)
            else:
                if i<min(rlt):
                    rlt.remove(min(rlt))
                    rlt.append(i)
                else:
                    if i<max(rlt):
                        rlt.remove(max(rlt))
                        rlt.append(i)
        
    if min(rlt) == max(rlt):
        return -1

    return min(rlt)        

if __name__ == '__main__':
    arr = [2,2,5,None,None,5,7]
    root = None
    root = insert_level_order(arr,root,0,len(arr))
    print(sec_min(root))
    arr = [2,2,2]
    root = None
    root = insert_level_order(arr,root,0,len(arr))
    print(sec_min(root))
