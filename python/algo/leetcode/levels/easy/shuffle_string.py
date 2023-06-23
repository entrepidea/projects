"""
You are given a string s and an integer array indices of the same length. The string s will be shuffled such that the character at the ith position moves to indices[i] in the shuffled string.

Return the shuffled string.

https://leetcode.com/problems/shuffle-string/

01/07/23

"""
from typing import List

#use cyclic sort
#https://leetcode.com/problems/shuffle-string/solutions/755923/used-cyclic-sort-with-o-1-space-and-o-n-time-complexity/
def shuffle(arr : List[int], indices : List[int]):
	for i in range(len(indices)):
		while i != indices[i]:
			arr[i],arr[indices[i]] = arr[indices[i]], arr[i]
			indices[i],indices[indices[i]] = indices[indices[i]],indices[i]
			
if __name__ == '__main__':
	s = ['c','o','d','e','l','e','e','t']
	indices =  [4,5,6,7,0,2,1,3]
	shuffle(s,indices)
	print(s)	
