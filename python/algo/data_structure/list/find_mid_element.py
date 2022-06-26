from commons import *



def main():
    head = construct_consecutive_list(10)
    print_list(head)

    if head.next is None:
        print(head.data)
        return

    if head.next.next is None:
        print(head.next.data)
        return

    slow = head.next
    fast = head.next.next
    while(fast is not None and fast.next is not None):
        fast = fast.next.next
        slow = slow.next

    print(slow.data)

if __name__ == "__main__":
    main()
