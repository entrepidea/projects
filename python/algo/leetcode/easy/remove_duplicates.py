"""

the array is in ascending order with possible duplicates. This program is to remove the dups in place.
https://leetcode.com/problems/remove-duplicates-from-sorted-array/

date: 10/08/21

"""
def remove(arr):
    start_pt = 0
    while start_pt < len(arr)-1:
        stop_pt = start_pt + 1
        ele = arr[start_pt]
        while stop_pt < len(arr) and arr[stop_pt] == ele:
            stop_pt += 1
        if stop_pt > start_pt + 1: #dups found
            pt = start_pt + 1
            for j in range(stop_pt, len(arr)):
                arr[pt] = arr[j]
                arr[j] = None
                pt += 1

        start_pt += 1

    #print(start_pt)
    #if arr[start_pt] == arr[start_pt-1]:
        #arr[start_pt] = '_'
        
if __name__ == '__main__':
    #num = [1,1,1,2,2,3,3,6,9,9,9,10]
    #num = [1,2,3,3,6,9,10]
    num = [1,1,2]
    print(num)
    remove(num)
    print(num)
