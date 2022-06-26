from collections import Counter
"""
Given a non-empty array of non-negative integers nums, the degree of this array is defined as the maximum frequency of any one of its elements.

Your task is to find the smallest possible length of a (contiguous) subarray of nums, that has the same degree as nums.

Date: 12/11/21

"""
def max_freq(nums):
    return max(Counter(nums).values())

#my solution, brutal force. Should have a better approach.
def degree(nums: [int]) -> int:
    freq = max_freq(nums)
    print(f'freq={freq}')
    i = 0
    l = len(nums)
    while i < len(nums)-freq:
        j = i+freq
        while j < len(nums)+1:
            new_arr = nums[i:j]
            j += 1
            print(f'new array={new_arr}, freq of new array={max_freq(new_arr)},length={len(new_arr)}')            
            if len(new_arr) == freq and max_freq(new_arr) == freq:
                return len(new_arr)
            else:
                if max_freq(new_arr) == freq and len(new_arr) < l:
                    l = len(new_arr)

        i += 1        

    return l        

#a better solution using two pointers
#borrowed and translated from this c++ code:
#https://leetcode.com/problems/degree-of-an-array/discuss/1617808/Easy-C%2B%2B-Solution-oror-Runtime-faster-than-97.64-oror-Memory-Usage-less-than-91.21
#date: 12/11/21
def degree2(nums : [int]) -> int:
    dic = Counter(nums)
    max_freq = max(dic.values()) 
    ret = len(nums)
    for k in dic.keys():
        if dic[k] == max_freq:
            l = 0
            r = len(nums)-1
            while l<=r and nums[l] != k:
                l += 1             
            while l<=r and nums[r] != k:
                r -= 1             
            ret = min(ret,r-l+1)

    return ret

if __name__ == '__main__':
    """
    nums = [1,2,2,3,1]
    print(degree(nums))
    nums = [1,2,2,3,1,3,3]
    print(degree(nums))
    nums = [1,2,2,3,1,4,2]
    print(degree(nums))
    """

    #another test.
    nums = [1,2,2,3,1]
    print(degree2(nums))
    nums = [1,2,2,3,1,4,2]
    print(degree2(nums))
