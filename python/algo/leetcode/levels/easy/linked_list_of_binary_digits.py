"""
Given head which is a reference node to a singly-linked list. The value of each node in the linked list is either 0 or 1. The linked list holds the binary representation of a number.

Return the decimal value of the number in the linked list.

The most significant bit is at the head of the linked list.

https://leetcode.com/problems/convert-binary-number-in-a-linked-list-to-integer/

10/29/22

"""

import sys
sys.path.append('utils/')
from LinkedListUtils import ListNode
from LinkedListUtils import construct_linked_list
from LinkedListUtils import print_list
def convert(head) -> int:
	arr = []	
	p = head
	while p:
		arr.append(p.data)
		p = p.next
	
	for i in range(len(arr)):
		arr[i] *= pow(2,(len(arr)-i-1))

	#print(arr)
	return sum(arr)	

#this one make more sense
#https://leetcode.com/problems/convert-binary-number-in-a-linked-list-to-integer/discuss/2758998/Java-Runtime%3A-0ms-faster-than-100.00-oror-Memory-usage-less-than-90.7
def convert2(head) -> int:
	r = head.data
	while head.next:
		r = 2*r + head.next.data
		head = head.next

	return r

if __name__ == '__main__':
	arr = [1,0,1]
	head = construct_linked_list(arr)
	print(convert(head))
	arr = [1,1,1]
	head = construct_linked_list(arr)
	print(convert(head))
	arr = [1,0,0,0]
	head = construct_linked_list(arr)
	print(convert(head))
	arr = [1,0,1,0]
	head = construct_linked_list(arr)
	print(convert(head))


	arr = [1,0,1]
	head = construct_linked_list(arr)
	print(convert2(head))
	arr = [1,1,1]
	head = construct_linked_list(arr)
	print(convert2(head))
	arr = [1,0,0,0]
	head = construct_linked_list(arr)
	print(convert2(head))
	arr = [1,0,1,0]
	head = construct_linked_list(arr)
	print(convert2(head))

