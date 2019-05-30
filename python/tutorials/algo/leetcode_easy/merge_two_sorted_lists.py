"""
https://leetcode.com/problems/merge-two-sorted-lists/

date: 03/20/19

"""

class Node(object):
    def __init__(self, data):
        self.data = data
        self.next = None

    def get_data(self):
        return self.data



def conv_arr_2_list(arr):
    head = Node(arr[0])

    p = head

    for i in range(1, len(arr),1):
        p.next = Node(arr[i])
        p = p.next

    return head

def print_list(head):
    p = head
    while p!= None:
        print(p.data)
        p = p.next

#redo. 04/21/19
def merge(h1,h2):
    h = Node(None)
    h.next = None
    temp = h
    while h1 is not None and h2 is not None:
        if h1.data < h2.data:
            temp.next = h1
            h1 = h1.next
        else:
            temp.next = h2
            h2 = h2.next

        temp = temp.next

    if h1 is not None:
        temp.next = h1

    if h2 is not None:
        temp.next = h2

    return h

def test_merge():
    h1 = conv_arr_2_list([1,2,4,6])
    h2 = conv_arr_2_list([3,7,8])
    h = merge(h1,h2)
    print_list(h.next)

def main():
    #test
    h1 = conv_arr_2_list([1,2,4,6])
    h2 = conv_arr_2_list([3,7,8])

    p1,p2 = h1,h2

    head = Node(-1)
    p = head
    while p1 != None and p2 != None:
        if p1.data < p2.data:
            p.next = p1
            p1 = p1.next
        else:
            p.next = p2
            p2 = p2.next

        p = p.next

    if p1 == None:
        p.next = p2
    else:
        p.next = p1

    print_list(head.next)


if __name__ =='__main__':
    main()