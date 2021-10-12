from typing import Optional

class ListNode:
    def __init__(self, data):
        self.data = data
        self.next = None
    """
    def __repr__(self):
        return f'C({self.data})'
    def __hash__(self):
        return hash(self.data)
    def __eq__(self,other):
        return self.__class__ == other.__class__ and self.data == other.data
    """

def construct_linked_list(arr) -> ListNode:
    head = ListNode(arr[0])
    node = head

    for i in range(1, len(arr)):
        node.next = ListNode(arr[i])
        node = node.next
    return head

def print_list(head : Optional[ListNode]):
    p = head
    while p is not None:
        print(p.data, end=',')
        p = p.next
    
    print()

def create_cycle(head : Optional[ListNode],pos) -> ListNode:
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

