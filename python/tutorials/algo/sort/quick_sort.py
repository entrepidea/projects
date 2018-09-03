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

def main(argv):
    arr = random.sample(range(0,1000),5)
    quick_sorting(arr, 0, len(arr)-1)
    print(arr)

if __name__ == '__main__':
    main(sys.argv[1:])


