"""
Given a binary array nums, return the maximum number of consecutive 1's in the array.
https://leetcode.com/problems/max-consecutive-ones/

Date: 11/02/21
NOTE: the idea was borrowed from this javascript code: https://leetcode.com/problems/max-consecutive-ones/discuss/1555732/JavaScript-solution
"""
def max_ones(nums) -> int:
    max_ones = 0
    tmp = 0
    for e in nums:
        if e == 1:
            tmp += 1
            max_ones = max(tmp,max_ones)
        else:
            tmp = 0
    
    return max_ones


if __name__ == '__main__':
    nums = [1,1,0,1,1,1]
    print(max_ones(nums))
    nums = [1,0,1,1,0,1]
    print(max_ones(nums))
    nums = [1,1,1,1,0,1]
    print(max_ones(nums))

            

