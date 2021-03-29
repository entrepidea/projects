from commons import *
import sys

#TODO

def sort(head):
    if head == None or head.next == None:
        return head

    i_ptr = head
    stop_ptr = None

    while i_ptr.next.next != None:
        i_ptr = i_ptr.next
        j_ptr = head.next
        j_1_ptr = head
        pre_j_1_ptr = None
        is_sorted = True
        while j_ptr.next != stop_ptr:
            if j_ptr.data < j_1_ptr.data:
                j_1_ptr.next = j_ptr.next
                j_ptr.next = j_1_ptr

                if j_1_ptr == head:
                    head = j_ptr
                    pre_j_1_ptr = head
                else:
                    pre_j_1_ptr.next = j_ptr

                j_ptr = j_1_ptr.next

                is_sorted = False

            else:
                pre_j_1_ptr = j_1_ptr
                j_1_ptr = j_ptr
                j_ptr = j_ptr.next

        if is_sorted:
            break

        stop_ptr = j_ptr

    return head



def main(argv):
    #head = construct_list_from_array([27, 20, 37, 64, 33, 66])
    head = construct_list_from_array([23, 29, 19, 64, 2])
    #head = construct_random_list(0,100,5)
    print_list(head)
    new_head = sort(head)
    print_list(new_head)


if __name__ == "__main__":
    main(sys.argv[1:])