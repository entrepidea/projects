"""
Given an array of distinct integers arr, find all pairs of elements with the minimum absolute difference of any two elements.

Return a list of pairs in ascending order(with respect to pairs), each pair [a, b] follows

https://leetcode.com/problems/minimum-absolute-difference/

10/19/22

"""
import math
import collections
from typing import List
def diff(arr : [int]) -> []:
	arr.sort()
	ret = []
	for i in range(len(arr)-1):
		d = abs(arr[i]-arr[i+1])
		if len(ret) == 0:
			ret.append((arr[i],arr[i+1]))
		else:
			first_one = ret[0]
			d2 = abs(first_one[0] - first_one[1])
			if d<d2:
				ret = []
				ret.append((arr[i],arr[i+1]))
			elif d2==d:
				ret.append((arr[i],arr[i+1]))

	return ret
	

#use dictionary
#https://leetcode.com/problems/minimum-absolute-difference/discuss/901118/Python-3-greater-O(n-log-n)-time-and-O(n)-space-using-sort()-and-dictionary
def diff2(arr : List[int]) -> List[List[int]]:
	min_diff = math.inf
	dic = collections.defaultdict(list)
	
	arr.sort()
	for i in range(len(arr)-1):
		d = abs(arr[i]-arr[i+1])
		dic[d].append((arr[i],arr[i+1]))
		min_diff = min(min_diff, d)

	return dic[min_diff]

if __name__ == '__main__':
	
	"""
	arr = [4,2,1,3]
	print(diff(arr))
	arr = [1,3,6,10,15]
	print(diff(arr))
	arr = [3,8,-10,23,19,-4,-14,27]
	print(diff(arr))
	"""

	arr = [4,2,1,3]
	print(diff2(arr))
	arr = [1,3,6,10,15]
	print(diff2(arr))
	arr = [3,8,-10,23,19,-4,-14,27]
	print(diff2(arr))

