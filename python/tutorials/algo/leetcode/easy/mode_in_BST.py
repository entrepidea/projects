"""
Given the root of a binary search tree (BST) with duplicates, return all the mode(s) (i.e., the most frequently occurred element) in it.
https://leetcode.com/problems/find-mode-in-binary-search-tree/

NOTE: borrow the ideas from this link: https://leetcode.com/problems/find-mode-in-binary-search-tree/discuss/1516781/python-99-faster
learned: function inside another function; APIs such as Counter

Date: 11/04/21

"""
import sys
sys.path.append('utils/')
from TreeUtils import TreeNode
from TreeUtils import insert_level_order
from TreeUtils import inorder_traverse
from TreeUtils import bfs

from typing import Optional
from collections import Counter
#from collections import List
def mode(node : Optional[TreeNode]) -> [int]:
    arr = []

    def inorder(node):
        if node is None:
            return
        inorder(node.left)
        arr.append(node.data)
        print(f'arr={arr}')
        inorder(node.right)
    
    inorder(node)
    freq = Counter(arr)
    max_len = max(freq.values())

    print(f'freq={freq}')

    ans = []
    for key in freq:
        if freq[key] == max_len:
            ans.append(key)
        
    return ans

if __name__ == '__main__':
    arr = [1,None,2,2]
    root = None
    root = insert_level_order(arr,root,0,len(arr))
    print(mode(root))


