import tutorials.algo.tree.tree_tests as tree_module
"""

desc: comapre if two trees are the same
source: https://leetcode.com/problems/same-tree/
date: 05/11/19

"""
def comp_arrs(arr1,arr2):
    if len(arr1) != len(arr2):
        return False
    for i in range(0, len(arr1),1):

        x,y = arr1[i],arr2[i]
        if x is None and y is None:
            continue
        else:
            if x is None and y is not None:
                return False
            else:
                if x is not None and y is None:
                    return False

        if x.val != y.val:
            return False

    return True


def test_same_tree():
    root1 = tree_module.from_array_to_tree([1, 2, 3, 4, None, 5, None])
    arr1 = tree_module.from_tree_to_array(root1)

    root2 = tree_module.from_array_to_tree([1, 2, 3, 4, None, 5, None])
    arr2 = tree_module.from_tree_to_array(root2)

    if comp_arrs(arr1,arr2):
        print('\nIs same tree')
    else:
        print ('\nnot same tree')
