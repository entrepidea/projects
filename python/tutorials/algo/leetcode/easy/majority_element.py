"""
find majority element (that appear more than n/2 times in the array)
https://leetcode.com/problems/majority-element/

date: 10/12/21

"""
def bubble(arr):
    for i in range(0,len(arr)):
        is_sorted = True
        for j in range(1, len(arr)-i):
            if arr[j]>arr[j-1]:
                temp = arr[j-1]
                arr[j-1] = arr[j]
                arr[j] = temp
                is_sorted = False
        if is_sorted:
            break
            

def majority(arr):
    bubble(arr)
    mid = len(arr)//2
    #print(f'mid={mid}')
    return arr[0] if arr[mid] == arr[0] else arr[len(arr)-1]

if __name__ == '__main__':
    arr = [3,2,3]
    print(f'the majority element in array {arr} is {majority(arr)}')
    arr = [2,2,1,1,1,2,2]
    print(f'the majority element in array {arr} is {majority(arr)}')

