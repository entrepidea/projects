"""
determin if a linked list has a cycle
https://leetcode.com/problems/linked-list-cycle/

date: 10/11/21

note: use two points and the 2nd pointer runs twice faster, if two points meet, it means a cycle was formed.

"""

class Node:
    def __init__(self, data):
        self.data = data
        self.next = None

def construct_linked_list(arr):
    head = Node(arr[0])
    node = head

    for i in range(1, len(arr)):
        node.next = Node(arr[i])        
        node = node.next
    return head

def create_cycle(head,pos):
    p = head
    i =0
    node = None
    while p.next is not None:
        if i == pos:
            node = p
        p = p.next 
        i+=1
    p.next = node
    return head

def is_cycle(head) -> bool:
    p1 = head.next
    p2 = head.next.next
    while p1 is not None and p2 is not None and p2.next is not None: 
        #print(p.data)
        if p1 == p2:
            return True
        p1 = p1.next
        p2 = p2.next.next
    return False

if __name__ == '__main__':
    arr = [3,2,0,-4,5]
    head = construct_linked_list(arr)
    head = create_cycle(head, 1)
    #testing
    #p = head
    #for i in range(0,10):
    #    print(p.data)
    #    p = p.next
    print(is_cycle(head))
        
