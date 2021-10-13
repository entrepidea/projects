"""
remove the occurencies in an array
https://leetcode.com/problems/remove-linked-list-elements/

date: 10/13/21

"""
import sys
sys.path.append('utils/')

from LinkedListUtils import ListNode
from LinkedListUtils import construct_linked_list
from LinkedListUtils import print_list

def remove(head : ListNode, val : int) -> ListNode:
	
	p = head
	while p is not None and p.data == val:
		p = p.next

	if p is None:
		return None

	new_head = p # first node that does NOT match val

	prev_p = p
	p = p.next
	while p is not None:
		if p.data != val:
			prev_p = p
			p = p.next
		else:
			prev_p.next = p.next
			p = p.next

	return new_head


if __name__ == '__main__':
	#arr = [1,2,6,3,4,5,6]
	#val = 6
	arr = [7,7,7,7,7]
	val = 7
	head = construct_linked_list(arr)
	new_head = remove(head,val)
	if new_head is not None: 
		print_list(new_head) 
