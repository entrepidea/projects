"""

https://leetcode.com/problems/shuffle-the-array/solutions/
Given the array nums consisting of 2n elements in the form [x1,x2,...,xn,y1,y2,...,yn].
Return the array in the form [x1,y1,x2,y2,...,xn,yn].

12/12/22

"""

from typing import List
#this solution employs generator
#https://leetcode.com/problems/shuffle-the-array/solutions/674365/clean-python-3-generator-and-one-liner/
def shuffle(arr : List[int], n : int) ->List[int]:
	def gen(A):
		for i in range(n):
			yield from (A[i],A[i+n])
	return list(gen(nums))	

if __name__ == '__main__':
	nums = [2,5,1,3,4,7] 
	n = 3
	print(shuffle(nums, n))
