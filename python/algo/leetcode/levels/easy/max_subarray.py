"""

find out the subarray with a maximum sum.
https://leetcode.com/problems/maximum-subarray/
date: 10/08/21

"""

#this is the most inefficient way, very brutal force. Gonna have a better way. 10/08/21
def max_sub(arr):
    start_pt = 0
    max_val = 0
    start = 0
    end = len(arr)-1
    while start_pt < len(arr):
        end_pt = len(arr)
        while start_pt < end_pt:
            temp = 0
            for i in range(start_pt,end_pt):
                temp += arr[i]
            if temp > max_val:
                max_val = temp
                start = start_pt
                end = end_pt
            end_pt -= 1                
        start_pt += 1                    
    
    max_val = 0
    for i in range(start,end):
        max_val += arr[i]

    return start, end, max_val

if __name__ == '__main__':
    nums = [-2,1,-3,4,-1,2,1,-5,4]
    x,y,max_val = max_sub(nums)
    print('x=%d,y=%d,max value=%d'%(x,y,max_val))
    nums = [1]
    x,y,max_val = max_sub(nums)
    print('x=%d,y=%d,max value=%d'%(x,y,max_val))
    nums = [5,4,-1,7,8]
    x,y,max_val = max_sub(nums)
    print('x=%d,y=%d,max value=%d'%(x,y,max_val))
