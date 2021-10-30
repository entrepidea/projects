import sys
sys.path.append('utils/')

from TreeUtils import TreeNode
from TreeUtils import insert_level_order

from Typing import Optional

def add(node : Optional[TreeNode], sum):
    if node is None:
        return 
    
    if node.left is not None:
        sum += node.left.data
        add(node.left, sum)
    
    if node.right is not None:
        add(node.right,sum)

if __name__ == '__main__':
    arr = [3,9,20,null,null,15,7]
    root = insert_level_order(arr,root,0,len(arr))
    sum = 0
    add(root,sum)
    print(sum)
    


