import queue

"""
Description: This is a collection of tests relating to the Tree data structure. It covers: BFS, DFS(pre/post/in order searches); construct a tree from a array and vice versa.

date: 05/10/19

references: 
http://www.cppblog.com/guogangj/archive/2009/10/16/98772.html  

"""
#create a tree node class for testing
class TreeNode:

    def __init__(self, v):
        self.val = v
        self.lchild = None
        self.rchild = None






# construct a tree from an array. The array is known as a binary tree array
def from_array_to_tree(arr):
    root = TreeNode(arr[0])
    temp = root
    populate_tree(temp, 0, arr, len(arr) - 1)

    return root

def populate_tree(node, idx, arr, max_len):
    if node is None:
        return

    l_idx, r_idx = 2 * idx + 1, 2 * idx + 2

    if l_idx > max_len or r_idx > max_len:
        return

    if arr[l_idx] is not None:
        node.lchild = TreeNode(arr[l_idx])
    else:
        node.lchild = None

    if arr[r_idx] is not None:
        node.rchild = TreeNode(arr[r_idx])
    else:
        node.rchild = None

    populate_tree(node.lchild, l_idx, arr, max_len)
    populate_tree(node.rchild, r_idx, arr, max_len)


"""
The array is so arranged that the final tree looks like below:
     1 
    / \
   2   3
  /   / 
 4   5

"""


def test_from_array_to_tree():
    arr = [1, 2, 3, 4, None, 5, None]

    root = from_array_to_tree(arr)

    assert root.val == 1
    assert root.lchild.val == 2
    assert root.rchild.val == 3

    assert root.lchild.lchild.val == 4
    assert root.lchild.rchild is None

    assert root.rchild.lchild.val == 5
    assert root.rchild.rchild is None





# from a known tree to a binary tree array
def from_tree_to_array(root):

    level = 0
    dic = {}
    foo(root, level, dic)
    arr = []
    for _, node in sorted(dic.items()):
        arr.append(node)
    return arr

# recursively BFS a tree and map it into a dictionary
def foo(node, level, dic):
    if node is None:
        dic[level] = node
        return

    if node.lchild is None and node.rchild is None:
        dic[level] = node
        return

    dic[level] = node

    l_node = node.lchild
    l_level = 2 * level + 1
    foo(l_node, l_level, dic)

    r_node = node.rchild
    r_level = 2 * level + 2
    foo(r_node, r_level, dic)


def test_from_tree_to_array():
    root = from_array_to_tree([1, 2, 3, 4, None, 5, None])
    arr = from_tree_to_array(root)
    for i in arr:
        print(i.val) if i is not None else print(i)


# BFS, or Breadth First Search, can be implemented using a queue
def test_bfs():
    root = from_array_to_tree([1, 2, 3, 4, None, 5, None])

    q = queue.Queue()

    q.put(root)

    node = q.get()

    ret = []

    while node is not None:
        ret.append(node.val)
        n = node.lchild
        if n is not None:
            q.put(n)


        n = node.rchild
        if n is not None:
            q.put(n)


        if q.qsize() != 0:
            node = q.get()
        else:
            break

    assert len(ret) == 5


    print('\n')
    print(','.join(str(e) for e in ret))


#test BFS again
def test_bfs2():
    arr = [1,2,2,3,4,4,3]
    root = from_array_to_tree(arr)
    q = queue.Queue()
    q.put(root)
    node = q.get()
    while node is not None and not q.empty():
        if node is not None:
            print(node.val)
        if node.lchild is not None:
            q.put(node.lchild)
        if node.rchild is not None:
            q.put(node.rchild)

        node = q.get()



#DFS, or Depth First Search ...
#TODO
def test_dfs():
    pass


