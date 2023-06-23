"""
Given an array of positive integers nums, return the maximum possible sum of an ascending subarray in nums.

A subarray is defined as a contiguous sequence of numbers in an array.

A subarray [numsl, numsl+1, ..., numsr-1, numsr] is ascending if for all i where l <= i < r, numsi  < numsi+1. Note that a subarray of size 1 is ascending.

https://leetcode.com/problems/maximum-ascending-subarray-sum/

06/18/23

"""

def foo(nums:[int])->int:
	p = 0
	ret = 0
	total = 0
	while p<len(nums)-1:
		
		while p<len(nums)-1 and nums[p]<=nums[p+1]:
			total += nums[p]
			#print(f'nums[{p}]={nums[p]}, total={total}')
			p += 1
		total += nums[p]
		#print(f'total={total}')	
		p += 1

		if total > ret:
			ret = total
			total = 0
	
	return ret			
	
if __name__ == '__main__':
	nums = [10,20,30,5,10,50]
	print(foo(nums))
	nums = [10,20,30,40,50]
	print(foo(nums))
