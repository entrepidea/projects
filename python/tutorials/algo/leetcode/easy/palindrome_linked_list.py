"""

determine if a linked list is a palindrome.
https://leetcode.com/problems/palindrome-linked-list/

date: 10/18/21

"""

import sys
sys.path.append('utils/')
from LinkedListUtils import ListNode
from LinkedListUtils import construct_linked_list
from LinkedListUtils import list_size
from LinkedListUtils import print_list

def palindrome(head : ListNode) -> bool:

	whole_len = list_size(head)
	half_len = whole_len//2

	p1 = head
	cur = 0
	while cur <= half_len:
		v1 = p1.data
		p2 = head
		for i in range(whole_len - cur -1):
			p2 = p2.next
      
		v2 = p2.data

		#print(f'v1={v1},v2={v2}')
		if v1 != v2:
			return False
		
		cur += 1
		p1 = p1.next

	return True

if __name__ == '__main__':
	arr = [1,2,3,3,2,1]
	head = construct_linked_list(arr)
	print(palindrome(head))
	arr = [1,8,3,3,2,1]
	head = construct_linked_list(arr)
	print(palindrome(head))

