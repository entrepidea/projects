"""
Given the roots of two binary trees root and subRoot, return true if there is a subtree of root with the same structure and node values of subRoot and false otherwise.
https://leetcode.com/problems/subtree-of-another-tree/

NOTE: simple python solution with explanation:
https://leetcode.com/problems/subtree-of-another-tree/discuss/1562570/Easiest-and-Intuitive-Python-with-explanation-(WITH-EXPLANATION)
TODO: not pass the 2nd test.

Date: 11/10/21

"""
import sys
sys.path.append('utils/')
from TreeUtils import TreeNode
from TreeUtils import insert_level_order
from TreeUtils import bfs

def is_subtree(a ,b) -> bool:
    def same_tree(a,b) ->bool:
        if (not a) and (not b): return True
        if (a and not b) or (b and not a): return False
        return a.data == b.data and same_tree(a.left,b.left) and same_tree(a.right, b.right)
        
    def check_all(a,b) -> bool:
        return same_tree(a,b) or (a.left and check_all(a.left, b)) or (a.right and check_all(a.right,b)) 

    return check_all(a,b)
        

if __name__ == '__main__':
    """
    #test1
    arr = [3,4,5,1,2]
    root = None
    root = insert_level_order(arr,root,0, len(arr))
    arr = [4,1,2]    
    sub_root = None
    sub_root = insert_level_order(arr,sub_root,0,len(arr))
    print(is_subtree(root,sub_root))

    """

    #test2
    arr = [3,4,5,1,2,None,None,None,None,0]
    root = None
    root = insert_level_order(arr,root,0, len(arr))
    print(bfs(root))

    arr = [4,1,2]    
    sub_root = None
    sub_root = insert_level_order(arr,sub_root,0,len(arr))
    print(bfs(sub_root))
    print(is_subtree(root,sub_root))

