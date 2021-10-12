"""

return the indice of two elements in a non-decresing array whose sum meets the set target
https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/

date: 10/12/21

"""

def sum_ii(arr, target):
    
    if len(arr) == 2:
        return [0,1] if arr[0] + arr[1] == target else []

    stop_idx = -1
    for i in range(0,len(arr)):
        if arr[i] >target:
            stop_idx = i

    if stop_idx != -1:
        arr = arr[:stop_idx]
    
    mid = len(arr)//2
    first_half = arr[:mid]
    sec_half = arr[mid:]
    #print(f'middle index={mid}, whole array={arr}, first half={first_half}, second half={sec_half}')
    ret = [-1]*2
    for i in range(0,len(first_half)):
        #print(first_half[i],end=' ')
        remain = target - first_half[i]
        for j in range(0,len(sec_half)):
            if remain == sec_half[j]:
                ret[0] = i
                ret[1] = mid+j
                return ret




if __name__ == '__main__':
    
    arr = [2,7,11,15]
    target = 9
    ret = sum_ii(arr,target)
    print(f'array: {arr}, target={target}, index pair: {ret}')
   

    arr = [2,3,4]
    target = 6
    ret = sum_ii(arr,target)
    print(f'array: {arr}, target={target}, index pair: {ret}')

    arr = [-1,0]
    target = -1 
    ret = sum_ii(arr,target)
    print(f'array: {arr}, target={target}, index pair: {ret}')
