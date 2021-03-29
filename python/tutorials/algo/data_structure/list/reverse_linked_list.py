#create a node class
#https://www.tutorialspoint.com/python/python_linked_lists.htm


from commons import *

#this is a recursive version of reversing a linked list
def reverse(head):
    if head == None or head.next == None:
        return head
    cur = head
    while cur.next:
        prev = cur
        cur = cur.next

    prev.next = None
    head2 = reverse(head)
    cur.next = head2
    return cur

def find_tail (head):
    tail = head
    while tail.next is not None:
        tail = tail.next
    return tail

#write a non recursive version
def reverse2(head):
    tail = find_tail(head)
    cur = head
    while head.next is not None:
        while cur.next != None:
            prev = cur
            cur = cur.next

        cur.next = prev
        prev.next = None
        cur = head

    return tail

#redo, non-recursive 01/31/19
def reverse3(head):
    if head == None or head.next == None:
        return head

    cur = head.next
    head.next = None
    while cur != None:
        temp = cur
        cur = cur.next
        temp.next = head
        head = temp

    return head


#redo, recursive method, 02/07/19
def reverse4(head):
    if head == None or head.next == None:
        return head
    new_head = reverse4(head.next)
    p = new_head
    while p.next != None:
        p = p.next
    p.next = head
    head.next = None
    return new_head


#redo recursive. 04/20/19
def reverse5(head):
    if head is None or head.next is None:
        return head

    new_head = reverse5(head.next)

    temp = new_head

    while temp.next is not None:
        temp = temp.next

    temp.next = head
    head.next = None

    return new_head



def main():
    head = construct_consecutive_list(10)
    print("original list:")
    print_list(head)
    print("reversed list (recursive version):")
    new_head = reverse5(head)
    print_list(new_head)
    """
    head = construct_consecutive_list(10)
    new_head = reverse3(head)
    print("reversed list (non-recursive version):")
    print_list(new_head)
    print("reversed list (recursive version):")
    head = construct_consecutive_list(10)
    new_head = reverse4(head)
    print_list(new_head)
    """

if __name__ == "__main__":
    main()

