'''
This is a typical two-pointer problem. There are multiple ways of dealing with it.
This page of solutions tell it all: https://leetcode.com/problems/trapping-rain-water/solution/

09/27/21

'''
#brutch force
def snowpack(arr):
    #remove the first and the last - these two won't hold water.
    total = 0
    for i in range(1,len(arr)-1):
        left_max = 0
        for j in range(i-1,0,-1):
            if arr[j] > left_max:
                left_max = arr[j]
        
        right_max = 0
        for j in range(i+1, len(arr)):
            if arr[j] > right_max:
                right_max = arr[j]

        lower = min(left_max, right_max)                
        if lower > arr[i]:
            total += (lower - arr[i])

    return total

#dynamic programming
def snowpack2(arr):
    total = 0
    
    left_max = [0]*len(arr)
    right_max = [0]*len(arr)

    for i in range(1,len(arr)-2):
        left_max[i] = max(left_max[i-1],arr[i-1]) #the highest wall to the left of ith wall gonna be the bigger one b/w the wall next to ith and that the highest wall to the left of (i-1)th wall.
    for i in range(len(arr)-2, 0, -1):
        right_max[i] = max(right_max[i+1], arr[i+1])
    for i in range(1,len(arr)-1):
        lower = min(left_max[i],right_max[i])
        if lower > arr[i]:
            total += (lower - arr[i])
    return total



if __name__ == '__main__':
    arr = [0,1,3,0,1,2,0,4,2,0,3,0]
    t = snowpack2(arr)
    print(t)
