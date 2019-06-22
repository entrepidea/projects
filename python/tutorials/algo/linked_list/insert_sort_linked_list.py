from commons import *

#This is a reweite in python from my java code, see the algo.tests package of my java_tests project.
#you will find in there of the logic

def sort(head):
    if(head==None or head.next==None):
        return head

    toBeIns = head.next
    head.next = None

    while (toBeIns != None):
        cur = head
        prev = None
        while cur!=None and cur.data > toBeIns.data:
            prev = cur
            cur = cur.next

        nextToBeIns = toBeIns.next
        toBeIns.next = cur
        if prev == None:
            head = toBeIns
        else:
            toBeIns.next = cur
            prev.next = toBeIns

        toBeIns = nextToBeIns

    return head


#rewrite again 01/31/19
def sort2(head):
    to_be_ins = head.next
    pre_ins = head
    pre_ins.next = None
    while to_be_ins != None:
        if pre_ins == head and pre_ins.next == None:
            if to_be_ins.data < pre_ins.data:
                temp = to_be_ins
                to_be_ins = to_be_ins.next
                temp.next = pre_ins
                head = temp
            else:
                pre_ins.next = to_be_ins
                pre_ins = to_be_ins
                to_be_ins = to_be_ins.next
                pre_ins.next = None

        mov = head
        pre_mov = None
        while mov != pre_ins and mov.data < to_be_ins.data:
            pre_mov = mov
            mov = mov.next

        if mov == head:
            temp = to_be_ins
            to_be_ins = to_be_ins.next
            temp.next = mov
            head = temp
        else:
            if mov == pre_ins and mov.data < to_be_ins.data:
                temp = to_be_ins
                to_be_ins = to_be_ins.next
                mov.next = temp
                pre_ins = temp
                pre_ins.next = None
            else:
                temp = to_be_ins
                to_be_ins = to_be_ins.next
                temp.next = mov
                pre_mov.next = temp

        #print_list(head)

    return head





def main():
    #construct the list
    #head = construct_list_from_array([27,20,37,64,33,66])

    head = construct_random_list(10,100,10)
    #head = construct_list_from_array([46,36,86,25,12])
    #head = construct_list_from_array([32, 55, 98, 75, 21, 94, 54, 24, 29, 28])
    print_list(head)
    new_head = sort2(head)
    print_list(new_head)


if __name__ == '__main__':
    main()