"""

to check if duplicate elements (two or more elements with same values) can be found in an array
https://leetcode.com/problems/contains-duplicate/
date: 10/13/21

"""
def bubble(arr):
    for i in range(0, len(arr)):
        is_sorted = True
        for j in range(1,len(arr)-i):
            if arr[j] < arr[j-1]:
                temp = arr[j]
                arr[j] = arr[j-1]
                arr[j-1] = temp
                is_sorted = False
        if is_sorted:
            break

def check_dups(arr) -> bool:
    if len(arr) <=1:
        return False
    bubble(arr)
    print(f'array = {arr}')

    count = i = 0
    #print(f'length = {len(arr)}')
    while i < len(arr)-1:
        #print(f'i={i},arr[{i}] = {arr[i]}.')
        while i<len(arr)-1 and arr[i] == arr[i+1]:
            i +=1
            count +=1
        if count>0:
            return True
        i +=1
    if arr[i] == arr[i-1]:
        return True
    return False


if __name__ == '__main__':
    #arr = [1,2,3,1]
    #arr = [1,1,1,3,3,4,3,2,4,2]
    arr = [1,2,7,3,8,4,5,6,7,9]
    #arr = [3,3]
    print(check_dups(arr))
    
    arr = [1,2,4,3]
    print(check_dups(arr))
