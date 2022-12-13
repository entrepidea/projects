"""
Given an array of integers arr, return true if the number of occurrences of each value in the array is unique, or false otherwise.
https://leetcode.com/problems/unique-number-of-occurrences/

10/19/22

"""
import collections
from typing import List

def uniq_num_occu(arr : List[int]) -> bool:
	dic = collections.defaultdict(int)
	for x in arr:
		dic[x] += 1

	s = set()
	for k,v in dic.items():
		if v in s:
			return False
		else:
			s.add(v)

	return True
		
	

if __name__ == '__main__':
	arr = [1,2,2,1,1,3]
	print(uniq_num_occu(arr))	
	arr = [1,2]
	print(uniq_num_occu(arr))	
	arr = [-3,0,1,-3,1,1,1,-3,10,0]
	print(uniq_num_occu(arr))	
	
