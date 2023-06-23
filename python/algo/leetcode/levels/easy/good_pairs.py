"""
Given an array of integers nums, return the number of good pairs.

A pair (i, j) is called good if nums[i] == nums[j] and i < j.

https://leetcode.com/problems/number-of-good-pairs/

12/31/22

"""
from typing import List
def good_pairs(nums : List[int])->int:
	count = 0
	for i in range(len(nums)):
		arr = nums[i+1:]
		for j in range(len(arr)):
			if nums[i] == arr[j]:
				count += 1
	return count
#https://leetcode.com/problems/number-of-good-pairs/solutions/2968656/using-basic-combination-with-bucket-array/
#the method seems making sense
def good_pairs2(nums:List[int])->int:
	buc_arr = [0]*101
	for x in nums:
		buc_arr[x] += 1
	sum = 0
	for x in buc_arr:
		sum += x*(x-1)/2
	return int(sum)

if __name__ == '__main__':
	nums = [1,2,3,1,1,3]
	print(good_pairs(nums))
	nums = [1,1,1,1]
	print(good_pairs(nums))
	nums = [1,2,3,1,1,3]
	print(good_pairs2(nums))
	nums = [1,1,1,1]
	print(good_pairs2(nums))
