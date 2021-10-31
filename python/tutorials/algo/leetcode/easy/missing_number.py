"""
Given an array nums containing n distinct numbers in the range [0, n], return the only number in the range that is missing from the array.
https://leetcode.com/problems/missing-number/

Date: 10/27/21

"""

def missing(arr):
    new_arr = [0]*(len(arr)+1)
    for i in range(0, len(arr)):
        index = arr[i]
        new_arr[index] = 1
    for i in range(0, len(new_arr)):
        if new_arr[i] == 0:
            return i
    return -1

if __name__ == '__main__':
    nums = [3,0,1]
    print(missing(nums))
    nums = [0,1]
    print(missing(nums))
    nums = [9,6,4,2,3,5,7,0,1]
    print(missing(nums))
