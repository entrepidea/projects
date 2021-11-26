"""
You are given an integer array nums consisting of n elements, and an integer k.
Find a contiguous subarray whose length is equal to k that has the maximum average value and return this value. 
https://leetcode.com/problems/maximum-average-subarray-i/

Date: 11/24/21

"""

def max_avg(arr : [int], k :int)->float:
	max_val = sum(arr[:k])
	i = 1
	while i + (k-1) < len(arr):
		#print(f'max val = {max_val}')
		tmp = max_val - arr[i-1]
		tmp += arr[i+(k-1)]
		#print(f'tmp = {tmp}')

		if tmp > max_val:
			max_val = tmp
		i += 1

	return max_val/k

if __name__ == '__main__':
	arr = [1,12,-5,-6,50,3]
	k = 4
	print(max_avg(arr,k))
	arr = [5]
	k = 1
	print(max_avg(arr,k))
