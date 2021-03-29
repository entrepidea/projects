import numpy

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


def construct_random_list(min, max, size):
    data = numpy.random.randint(min,max,size) #create 10 random number with low bound = 0 and high bound = 100
    return construct_list_from_array(data)

def construct_list_from_array(arr):
    head = Node(arr[0])
    cur = head

    for i in range(1,len(arr),1):
        cur.next = Node(arr[i])
        cur = cur.next
    return head

def print_list(head):
    cur = head
    while cur:
        print(cur.data, end=' ')
        cur = cur.next

    print()