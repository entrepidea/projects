import sys
import random

"""
merge sorting, here is a great explaination
https://www.tutorialspoint.com/data_structures_algorithms/merge_sort_algorithm.htm

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

def main(argv):
    data = random.sample(range(0,100),11) #another simple way of generating random number to form a array.
    print(merge_sorting(data))

if __name__ == '__main__':
    main(sys.argv[1:])