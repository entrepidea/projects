"""
Given the root of a binary tree, construct a string consisting of parenthesis and integers from a binary tree with the preorder traversal way, and return it.
https://leetcode.com/problems/construct-string-from-binary-tree/

Date: 11/14/21

"""
import sys
sys.path.append('utils/')
from TreeUtils import TreeNode
from TreeUtils import insert_level_order
from TreeUtils import preorder_traverse

def construct(root: TreeNode, rlt :[]):
    if root is None:
        return
    #if root.left is None and root.right is None:
    #    rlt.append(')')
    rlt.append('(')
    rlt.append(root.data)
    construct(root.left,rlt)
    construct(root.right,rlt)
    rlt.append(')')


if __name__ == '__main__':
    arr = [1,2,3,4]
    root = None
    root = insert_level_order(arr, root, 0, len(arr))
    rlt = []
    construct(root,rlt)
    s = ''.join(str(e) for e in rlt)
    print(s)

    arr = [1,2,3,None,4]
    root = None
    root = insert_level_order(arr, root, 0, len(arr))
    rlt = []
    construct(root,rlt)
    s = ''.join(str(e) for e in rlt)
    print(s)
