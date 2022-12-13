"""
No-Zero integer is a positive integer that does not contain any 0 in its decimal representation.

Given an integer n, return a list of two integers [A, B] where:

A and B are No-Zero integers.
A + B = n
The test cases are generated so that there is at least one valid solution. If there are many valid solutions you can return any of them.

https://leetcode.com/problems/convert-integer-to-the-sum-of-two-no-zero-integers/

11/06/22

"""

def contain_zero(n : int) -> bool:
	l = list(str(n))
	return len(list(filter(lambda c:c=='0', l)))>0

def conv(num : int)->[]:
	rlt = []
	for i in range(1,num+1):
		if not contain_zero(i) and not contain_zero(num-i):		
			return [i,num-i]
	return []
if __name__ == '__main__':
	num = 2
	print(conv(num))
	num = 11
	print(conv(num))
	num = 13
	print(conv(num))
