"""
You are given an integer n and an integer start.

Define an array nums where nums[i] = start + 2 * i (0-indexed) and n == nums.length.

Return the bitwise XOR of all elements of nums.

https://leetcode.com/problems/xor-operation-in-an-array/

12/08/22

"""
from functools import reduce
from operator import xor
def XOR(start : int, n : int) -> int:
	def gen(start,n):
		for i in range(n):
			yield (start+2*i)

	return reduce(xor, map(int, list(gen(start, n))))

if __name__ == '__main__':
	start = 3
	n = 4
	print(XOR(start,n))
	start = 0
	n = 5
	print(XOR(start,n))
