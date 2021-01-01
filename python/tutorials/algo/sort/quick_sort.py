"""
quick sort, worst case: O^2, but it's rare. mostly it's nlog(n)

https://en.wikipedia.org/wiki/Quicksort

"""
import sys
import random
def patition(arr, lo, hi):
    pivot = arr[hi]
    i = lo
    for j in range(lo, hi):
        if arr[j] < pivot:
            arr[i],arr[j] = arr[j], arr[i]
            i = i+1
    arr[i], arr[hi] = arr[hi],arr[i]
    return i

#Lomuto partition scheme to select a pivot
def quick_sorting(arr, lo, hi):
    if lo < hi:
        p = patition(arr,lo,hi)
        quick_sorting(arr,lo, p-1)
        quick_sorting(arr, p+1, hi)



#02/06/19, use the first element as pivot
def partition2(arr, low, high):
    pivot = arr[low]
    i = high+1
    for j in range(high,low,-1):
        if arr[j] > pivot:
            i = i - 1
            arr[j],arr[i] = arr[i], arr[j]

    arr[low], arr[i-1] = arr[i-1],arr[low]

    return i-1

def quick_sorting2(arr,lo,hi):
    if lo < hi:
        p = partition2(arr,lo, hi)
        quick_sorting2(arr, lo, p-1)
        quick_sorting2(arr, p+1, hi)



def main(argv):
    #arr = random.sample(range(0,100),5)
    arr = [77, 46, 90, 79, 73]
    print(arr)
    quick_sorting2(arr, 0, len(arr)-1)
    print(arr)

if __name__ == '__main__':
    main(sys.argv[1:])


