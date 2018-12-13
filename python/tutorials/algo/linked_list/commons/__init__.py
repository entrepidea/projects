class Node(object):
    def __init__(self, data):
        self.data = data
        self.next = None

    def get_data(self):
        return self.data


def construct_consecutive_list(n):
    head = Node(1)
    cur = head
    for i in range(2,n,1):
        node = Node(i)
        cur.next = node
        cur = cur.next

    return head

def print_list(head):
    cur = head
    while cur:
        print(cur.data, end=' ')
        cur = cur.next

    print()