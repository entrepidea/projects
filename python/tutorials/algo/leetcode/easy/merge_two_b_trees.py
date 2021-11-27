"""
merge two binary trees. Details: https://leetcode.com/problems/merge-two-binary-trees/

NOTE: borrow idea: https://leetcode.com/problems/merge-two-binary-trees/discuss/1578297/JS-Two-Lines-JS-Answer
NOTE: TODO - result not correct.

Date: 11/14/21

"""
import sys
sys.path.append('utils/')
from TreeUtils import TreeNode
from TreeUtils import insert_level_order
from typing import Optional
def merge(root1: Optional[TreeNode], root2: Optional[TreeNode]) -> TreeNode:
    if root1 is None and root2 is None:
        return None
    v1 = 0 if root1 is None or root1.data == '' else int(root1.data)
    v2 = 0 if root2 is None or root2.data == '' else int(root2.data)
    v = v1+v2
    return TreeNode(v, merge(root1.left,root2.left), merge(root1.right, root2.right))

if __name__ == '__main__':
    arr1 = [1,3,2,5]
    root1 = None
    root1 = insert_level_order(arr1, root1, 0, len(arr1))
    arr2 = [2,1,3,None,4,None,7]
    root2 = None
    root2 = insert_level_order(arr2, root2, 0, len(arr2))
    
    root = merge(root1,root2)
    print(bfs(root))




