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


def main():
    head = construct_consecutive_list(10)
    print("original list:")
    print_list(head)
    new_head = reverse2(head)
    print("reversed list:")
    print_list(new_head)

if __name__ == "__main__":
    main()

