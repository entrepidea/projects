"""

put two arrays together in ascent order and find out its median
https://leetcode.cn/problems/median-of-two-sorted-arrays/

date: 07/02/22

"""

def median(arr1, arr2) -> int:
    arr = []
    p1 = 0
    p2 = 0
    while p1 < len(arr1) and p2 < len(arr2):
        if arr1[p1] <= arr2[p2]:
            arr.append(arr1[p1])
            p1+=1
        else:
            arr.append(arr2[p2])
            p2+=1
    print(f'p1={p1}, p2={p2}') 
    if p1 == len(arr1): # arr1 is exhausted first
        for k in range(p2, len(arr2)):
            arr.append(arr2[k])

    if p2 == len(arr2): # arr2 is exhausted first
        for k in range(p1, len(arr1)):
            arr.append(arr1[k])
    
    print(f'arr={arr}')

    if len(arr)%2!=0:   #length is an odd number
        return arr[int(len(arr)/2)]
    else:
        print(arr[int(len(arr)/2)])
        return (arr[int(len(arr)/2)-1] + arr[int(len(arr)/2)])/2

if __name__ == '__main__':
    arr1 = [1,3]
    arr2 = [2]
    print(median(arr1,arr2))
    arr1 = [1,2]
    arr2 = [3,4]
    print(median(arr1,arr2))

