"""
traversal of the tree and check if the values of the nodes, when summed up, is the same as the set target.
https://leetcode.com/problems/path-sum/

date: 10/11/21

"""
import sys
sys.path.append('utils/')

from TreeUtils import TreeNode
from TreeUtils import insert_level_order
from typing import Optional

#pre-order traversal of the tree
def path_sum(node : Optional[TreeNode], target : int) -> bool:
    if node is None or node.data is None:
        return False
    if node.left is None and node.right is None and node.data == target:
        return True
    left = path_sum(node.left, target - node.data)
    right = path_sum(node.right, target - node.data)
    return left or right

if __name__ == '__main__': 
    arr = [5,4,8,11,None,13,4,7,2,None,None,None,1]
    node = None
    node = insert_level_order(arr,node,0,len(arr))
    print(path_sum(node, 22))
