"""

https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
delete a specified node from a linked list. No head is given.
date: 10/22/21

"""
import sys
sys.path.append('utils/')
from LinkedListUtils import ListNode
from LinkedListUtils import construct_linked_list
from LinkedListUtils import print_list

def dele(node : ListNode):
		node.data = node.next.data
		node.next = node.next.next
	
if __name__ == '__main__':
	arr = [4,5,1,9]
	head = construct_linked_list(arr)
	print_list(head)	
	i = 1
	p = head
	while p:
		if p.data == i:
			break
		p = p.next

	dele(p)
	
	print_list(head)
