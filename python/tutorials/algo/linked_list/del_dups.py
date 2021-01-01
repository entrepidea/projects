from commons import *

def main():
    head = construct_list_from_array([1,2,3,4,5,5,5,5,6,7,8,8,9,10,11,11,11,11,11,12])
    print("original list:")
    print_list(head)
    p = head
    while p is not None and p.next is not None:
        n = p.next
        start  = p
        while n is not None and p.data == n.data:
            n = n.next
        start.next = n
        p = n

    print("list w/o dups: ")
    print_list(head)

if __name__ == "__main__":
    main()

