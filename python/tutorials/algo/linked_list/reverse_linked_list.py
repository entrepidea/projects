#create a node class
#https://www.tutorialspoint.com/python/python_linked_lists.htm
#TODO Not finished yet
class Node(object):
    def __init__(self, data):
        self.data = data
        self.next = None

    def get_data(self):
        return self.data


def construct_list():
    head = Node(1)
    cur = head
    for i in range(2,6,1):
        node = Node(i)
        cur.next = node
        cur = cur.next

    return head

def print_list(head):
    cur = head
    while cur.next:
        print(cur.data)
        cur = cur.next

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



def main():
    head = construct_list()
    print_list(head)
    new_head = reverse(head)
    print_list(new_head)


if __name__ == "__main__":
    main()

