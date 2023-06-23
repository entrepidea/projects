"""
Given an array of positive integers arr, find a pattern of length m that is repeated k or more times.

A pattern is a subarray (consecutive sub-sequence) that consists of one or more values, repeated multiple times consecutively without overlapping. A pattern is defined by its length and the number of repetitions.

Return true if there exists a pattern of length m that is repeated k or more times, otherwise return false.

https://leetcode.com/problems/detect-pattern-of-length-m-repeated-k-or-more-times/

01/26/23
 
"""
def pattern(arr: [int], m:int, k:int)->bool:
	for i in range(len(arr)-m):
		p = i
		new_arr = arr[p:p+m]
		cnt = 1
		p += m
		while (p+m) <= len(arr) and new_arr == arr[p:p+m]:	
			print(f'length={len(arr)}, new_arr={new_arr}, p={p}, p+m={p+m}, p+2m={p+2*m},cnt={cnt},k={k}')
			cnt += 1
			if cnt >= k:
				return True
			p = p+m

	return False

if __name__ == '__main__':
	arr = [1,2,4,4,4,4]
	m = 1
	k = 3
	print(pattern(arr,m,k))
	arr = [1,2,1,2,1,1,1,3]
	m = 2
	k = 2
	print(pattern(arr,m,k))
	arr = [1,2,1,2,1,3]
	m = 2
	k = 3
	print(pattern(arr,m,k))
