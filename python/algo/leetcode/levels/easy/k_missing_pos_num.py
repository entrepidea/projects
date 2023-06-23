"""
Given an array arr of positive integers sorted in a strictly increasing order, and an integer k.

Return the kth positive integer that is missing from this array.

https://leetcode.com/problems/kth-missing-positive-number/

01/07/23

"""
from typing import List
def k_missing(arr: List[int], k : int) -> int:
	cnt = 0
	i = 0
	while cnt<k:
		if i not in arr:
			cnt += 1
		i+=1
	return i

if __name__ == '__main__':
	arr = [2,3,4,7,11]
	k = 5
	print(k_missing(arr,k))
	arr = [1,2,3,4]
	k = 2
	print(k_missing(arr,k))
