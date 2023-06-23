"""
Given an array of positive integers arr, return the sum of all possible odd-length subarrays of arr.

A subarray is a contiguous subsequence of the array.

https://leetcode.com/problems/sum-of-all-odd-length-subarrays/

01/26/23

"""
def sum_odd_len(arr : [int])->int:
	i = 1
	total = 0
	while i <= len(arr):
		step = i
		p = 0
		while p+step <= len(arr):
			a = arr[p:p+step]
			print(f'{a}={sum(a)}')
			total += sum(a)
			p += 1

		i += 2
	return total

if __name__ == '__main__':
	arr = [1,4,2,5,3]
	print(sum_odd_len(arr))
	arr = [1,2]
	print(sum_odd_len(arr))
	arr = [10,11,12]
	print(sum_odd_len(arr))
