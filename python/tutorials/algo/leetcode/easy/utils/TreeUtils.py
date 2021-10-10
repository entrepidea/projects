"""
a bunch of Tree related utilities

note: the code below is copied from: https://www.geeksforgeeks.org/construct-complete-binary-tree-given-array/

date: 10/09/21
"""
class TreeNode:
    def __init__(self, data):
        self.data = data
        self.left = self.right = None

# Function to convert an array to a binanry tree
def insert_level_order(arr, root, i, n):
     
    # Base case for recursion
    if i < n:
        temp = TreeNode(arr[i])
        root = temp
 
        # insert left child
        root.left = insert_level_order(arr, root.left,
                                     2 * i + 1, n)
 
        # insert right child
        root.right = insert_level_order(arr, root.right,
                                      2 * i + 2, n)
    return root        
