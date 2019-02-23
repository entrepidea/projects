import sys
import random

"""
merge sorting, here is a great explaination
https://www.tutorialspoint.com/data_structures_algorithms/merge_sort_algorithm.htm

to recap: there are two parts in the solution. 1. break the array in halves recursively; 2. merge the two in one 

"""
def merge(arr1, arr2):

    ret = []
    while len(arr1) > 0 and len(arr2)>0:
        if arr1[0] > arr2[0]:
            ret.append(arr2[0])
            arr2.remove(arr2[0])
        else:
            ret.append(arr1[0])
            arr1.remove(arr1[0])

    while len(arr1)>0:
        ret.append(arr1[0])
        arr1.remove(arr1[0])

    while len(arr2)>0:
        ret.append(arr2[0])
        arr2.remove(arr2[0])

    return ret

def merge_sorting(dataArr):
    if len(dataArr)==1:
        return dataArr

    arr1, arr2 = dataArr[:(int)(len(dataArr)/2)], dataArr[int(len(dataArr)/2):]
    arr1 = merge_sorting(arr1)
    arr2 = merge_sorting(arr2)

    return merge(arr1,arr2)




#redo, 02/21/19
def merge2(l1,l2):
    n1 = len(l1)
    n2 = len(l2)
    l = []
    k1 = 0
    k2 = 0
    while k1<n1 and k2<n2:
        if l1[k1] <= l2[k2]:
            l.append(l1[k1])
            k1+=1
        else:
            l.append(l2[k2])
            k2+=1

    if k1==n1: #l1 is used up, only l2 has remains
        while k2<n2:
            l.append(l2[k2])
            k2+=1

    else:
        while k1<n1:
            l.append(l1[k1])
            k1+=1

    return l

def merge_sort2(arr):
    if len(arr) == 1:
        return arr
    l1,l2 = merge_sort2(arr[:(int)(len(arr)/2)]), merge_sort2(arr[(int)(len(arr)/2):])
    return merge2(l1,l2)

def main(argv):
    data = random.sample(range(0,100),11) #another simple way of generating random number to form a array.
    print("unsorted array:")
    print(data)
    print("sorted array using merged sorting:")
    print(merge_sort2(data))

if __name__ == '__main__':
    main(sys.argv[1:])