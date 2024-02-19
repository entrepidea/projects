"""
You are given a 0-indexed integer array nums. Rearrange the values of nums according to the following rules:

Sort the values at odd indices of nums in non-increasing order.
For example, if nums = [4,1,2,3] before this step, it becomes [4,3,2,1] after. The values at odd indices 1 and 3 are sorted in non-increasing order.
Sort the values at even indices of nums in non-decreasing order.
For example, if nums = [4,1,2,3] before this step, it becomes [2,1,4,3] after. The values at even indices 0 and 2 are sorted in non-decreasing order.
Return the array formed after rearranging the values of nums.

https://leetcode.com/problems/sort-even-and-odd-indices-independently/description/

03/03/24


"""

#https://leetcode.com/problems/sort-even-and-odd-indices-independently/solutions/1748551/python-two-lines/
def foo(nums:[int]):
    nums[::2],nums[1::2] = sorted(nums[::2]),sorted(nums[1::2])[::-1]    

if __name__ == '__main__':
    nums = [4,1,2,3]
    foo(nums)
    print(nums)
    
