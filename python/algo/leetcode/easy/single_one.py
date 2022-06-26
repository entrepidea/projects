"""

find out the data with single occurence from an array.
https://leetcode.com/problems/single-number/

date: 10/11/21

note: I sorted the array first and search for singler. Should have a better solution.


"""
def bubble(arr):
    for i in range(0,len(arr)):
        is_sorted = True
        for j in range(1, len(arr)-i):
            if arr[j] < arr[j-1]:
                temp = arr[j-1]
                arr[j-1] = arr[j]
                arr[j] = temp
                is_sorted = False

        if is_sorted:
            break

def single_one(arr):
    bubble(arr)
    i=0
    while i<len(arr):
        j = i+1
        while j<len(arr) and arr[j] == arr[i]:
            j+=1
        if j<len(arr) and (j-i)==1:
            return arr[i]
        else:
            if j==len(arr):
                return -1 #no singler was found
            else:
                i = j
                if i == len(arr)-1:
                    return arr[i]

    return -1

if __name__ == '__main__':
    arr = [2,2,1]
    print(arr)
    print(single_one(arr))

    arr = [4,1,2,1,2]
    print(arr)
    print(single_one(arr))


