"""
Given a 0-indexed integer array nums of length n and an integer k, return the number of pairs (i, j) where 0 <= i < j < n, such that nums[i] == nums[j] and (i * j) is divisible by k.

https://leetcode.com/problems/count-equal-and-divisible-pairs-in-an-array/description/

02/29/24

"""
def foo(nums:[int], k:int) -> int:
    cnt=0
    for i in range(0,len(nums)-1):
        for j in range(i+1,len(nums)):
            if nums[i]==nums[j] and (i*j)%k==0:
                cnt += 1
    return cnt

if __name__ == '__main__':
    nums = [3,1,2,2,2,1,3]
    k = 2
    print(foo(nums,k))
    nums = [1,2,3,4]
    k = 1
    print(foo(nums,k))
