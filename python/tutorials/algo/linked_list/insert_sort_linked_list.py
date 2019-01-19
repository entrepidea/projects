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

def main():
    #construct the list
    head = construct_list_from_array([27,20,37,64,33,66])
    print_list(head)
    new_head = sort(head)
    print_list(new_head)


if __name__ == '__main__':
    main()