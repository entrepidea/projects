"""
Given an unsorted array of integers nums, return the length of the longest continuous increasing subsequence (i.e. subarray). The subsequence must be strictly increasing.
https://leetcode.com/problems/longest-continuous-increasing-subsequence/

Date: 12/02/21

"""
def longest(arr)->int:
    i = 0
    l = -1
    while i < len(arr):
        j = i
        while j+1 < len(arr) and arr[j+1]>arr[j]:
            j += 1
        tmp = j+1-i
        #print(f'i={i}, j={j}, temp length: {tmp}')
        if j+1 == len(arr) and tmp > l:
            return tmp
        else:
            if tmp>l:
                l = tmp
                #print(f'l={l}')
            i = j+1

    return l

if __name__ == '__main__':
    nums = [1,3,5,4,7]
    print(longest(nums))
    nums = [2,2,2]
    print(longest(nums))
