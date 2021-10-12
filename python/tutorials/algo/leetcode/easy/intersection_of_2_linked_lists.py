"""

Intersection of Two Linked Lists
https://leetcode.com/problems/intersection-of-two-linked-lists/

date: 10/12/21
note: not the best approach - try a better way.

"""
import sys
sys.path.append('utils/')

from LinkedListUtils import ListNode
from LinkedListUtils import construct_linked_list
from LinkedListUtils import print_list

def intersect(l1,l2):
    pass

if __name__ == '__main__':
    arr1 = [4,1,8,4,5]    
    h1 = construct_linked_list(arr1)

    dic = {}
    p = h1
    pos = 0
    intersec = None
    while p is not None:
        if pos ==2:
            intersec = p
        dic[p] = pos
        pos += 1
        p = p.next
    
    arr2 = [5,6,1]
    h2 = construct_linked_list(arr2)
    p = h2
    while p.next is not None:
        print(p.data)
        p = p.next
    
    #print('type of intersect')
    #print(type(intersec))
    p.next = intersec #deliberately create a intersection between two linked list for the test below

    p = h2
    pos = -1
    intersected = True
    positions = []
    while p is not None:
        if p in dic.keys():
            if pos!=-1 and (dic[p] - pos) != 1:
                intersected = False
                break 
            pos  = dic[p]
            positions.append(pos)
        p = p.next

    if pos == -1:
        intersetcted = False

    print(f'are these two list intersected? {intersected}')
    print(positions)
