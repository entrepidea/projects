"""
Given an array of integers nums, calculate the pivot index of this array.
The pivot index is the index where the sum of all the numbers strictly to the left of the index is equal to the sum of all the numbers strictly to the index's right.

If the index is on the left edge of the array, then the left sum is 0 because there are no elements to the left. This also applies to the right edge of the array.

Return the leftmost pivot index. If no such index exists, return -1.

https://leetcode.com/problems/find-pivot-index/

Date: 12/15/21

"""
def pivot(nums : [int]) -> int:
	for i in range(1,len(nums)):
		print(f'i={i}, left = {nums[:i]}, right = {nums[(i+1):]}')
		if sum(nums[:i]) == sum(nums[(i+1):]):
			return i

	return -1

if __name__ == '__main__':
	nums = [1,7,3,6,5,6]
	#pivot(nums)
	print(pivot(nums))
