"""
convert a sorted array to a binary search tree
https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/
date: 10/10/21
note: the code below is from the discussion tab: 
https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/discuss/1505133/easy-to-understand-c%2B%2B-recursive-solution
it's validility is to be determined yet - TODO

"""
import sys
sys.path.append('utils/')

from TreeUtils import TreeNode

def bst(arr, start,end) ->TreeNode:
    if start > end:
        return None
    mid = (end + start)//2    
    root = TreeNode(arr[mid])
    root.left = bst(arr, start, mid-1)
    root.right = bst(arr,mid+1, end)
    return root

def pre_order(node, rlt):
    if node is None:
        rlt.append(None)
        return
    rlt.append(node.data)
    pre_order(node.left,rlt)
    pre_order(node.right,rlt)


def convert(arr):
    start = 0
    end = len(arr)-1
    root = bst(arr,start,end)
    rlt = []
    pre_order(root, rlt)
    return rlt

if __name__ == '__main__':
    arr = [-10,-3,0,5,9]
    rlt = convert(arr)
    print(rlt)
