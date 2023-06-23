"""
Given an array nums. We define a running sum of an array as runningSum[i] = sum(nums[0]…nums[i]).

Return the running sum of nums.

https://leetcode.com/problems/running-sum-of-1d-array/

12/18/22

"""

#use a genexp in python
def running(arr : [int])->[int]:
	def gen(arr):
		for i in range(len(arr)):
			yield sum(arr[:i+1])
	return list(gen(arr))

if __name__ == '__main__':
	arr = [1,2,3,4]
	print(running(arr))
	arr = [1,1,1,1,1]
	print(running(arr))
	arr = [3,1,2,10,1]
	print(running(arr))
