"""
reverse a linked list
https://leetcode.com/problems/reverse-linked-list/

date: 10/13/21

"""
import sys
sys.path.append('utils/')

from LinkedListUtils import ListNode
from LinkedListUtils import construct_linked_list
from LinkedListUtils import print_list

from typing import Optional

def reverse(head : Optional[ListNode]) -> ListNode:
    if head is None:
        return None
    if head.next is None:
        return head
    
    new_head = reverse(head.next)
    p = new_head
    while p.next is not None:
        p = p.next
    p.next = head
    head.next = None

    return new_head

if __name__ == '__main__':
    #arr = [1,2,3,4,5]
    arr = [1,2]
    head = construct_linked_list(arr)
    print_list(head)
    new_head = reverse(head)
    print_list(new_head)
