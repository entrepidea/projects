"""
Given an integer array nums, return the number of elements that have both a strictly smaller and a strictly greater element appear in nums.
https://leetcode.com/problems/count-elements-with-strictly-smaller-and-greater-elements/description/
02/25/24
"""

def foo(nums:[int])->int:
    nums = sorted(nums)
    cnt = 0
    for i in range(1,len(nums)-1):
        if nums[i]>nums[0] and nums[i]<nums[len(nums)-1]:
            cnt += 1
    return cnt

#another one: 
#https://leetcode.com/problems/count-elements-with-strictly-smaller-and-greater-elements/solutions/4348594/simple-py/
def foo2(nums:[int])->int:
    k = len(nums) - nums.count(min(nums)) - nums.count(max(nums))
    if k < 0:
        return 0
    else:
        return k

if __name__ == '__main__':
    nums = [11,7,2,15]
    print(foo2(nums))
    nums = [-3,3,3,90]
    print(foo2(nums))
