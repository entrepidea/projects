"""

Three ways of traversing a tree: pre-order, in-order and post-order
date: 10/10/21

"""
import sys
sys.path.append('utils/')

from TreeUtils import inorder_traverse
from TreeUtils import preorder_traverse
from TreeUtils import postorder_traverse
from TreeUtils import insert_level_order

if __name__ == '__main__':
    
    #consutrct a tree out of an array
    arr = [1,2,2,3,3,None,None,4,4]
    node = None
    node = insert_level_order(arr, node, 0, len(arr))

    rlt = inorder_traverse(node)
    print('in order:')
    print(rlt)

    rlt = preorder_traverse(node)
    print('pre order:')
    print(rlt)

    rlt = postorder_traverse(node)
    print('post order:')
    print(rlt)

