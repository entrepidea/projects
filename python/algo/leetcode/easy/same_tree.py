"""
compare if two trees are the same
https://leetcode.com/problems/same-tree/

date: 10/09/21

note: pay attention to the comment below.

"""
#Optional gives hint that the argument could be None or non-None
#see discussion at: https://stackoverflow.com/questions/51710037/how-should-i-use-the-optional-type-hint
from typing import Optional

#the below two lines are needed if the common code is from a different folder than the one this code resides in.
#see article at: https://www.digitalocean.com/community/tutorials/how-to-write-modules-in-python-3
import sys
sys.path.append('utils')

from TreeUtils import TreeNode
from TreeUtils import insert_level_order

#this solution is from the disccusion tab:
#https://leetcode.com/problems/same-tree/discuss/1508218/C%2B%2B-Python-simple-solution
def is_same(p : Optional[TreeNode], q : Optional[TreeNode]) -> bool:
    if not p or not q:
        return p == q
    return p.data == q.data and is_same(p.left,q.left) and is_same(p.right,q.right)

if __name__ == '__main__':
    #first test
    arr = [1,2,3]
    p = None
    p = insert_level_order(arr,p,0,len(arr))

    q = None
    q = insert_level_order(arr,q,0,len(arr))

    print(is_same(p,q))


    #2nd test
    arr = [1,2]
    p = None
    p = insert_level_order(arr,p,0,len(arr))

    arr = [1,None,2]
    q = None
    q = insert_level_order(arr,q,0,len(arr))

    print(is_same(p,q))


