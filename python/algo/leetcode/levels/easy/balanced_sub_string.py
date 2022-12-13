"""
Balanced strings are those that have an equal quantity of 'L' and 'R' characters.

Given a balanced string s, split it into some number of substrings such that:

Each substring is balanced.
Return the maximum number of balanced strings you can obtain.
https://leetcode.com/problems/split-a-string-in-balanced-strings/

10/22/22

"""
from typing import List
def balanced(text: List[str])->int:
	l = r = 0
	su = 0
	for c in text:
		if c == 'L':
			l += 1
		else:
			r +=1

		if l == r:
			su += 1
			l = r = 0

	return su

if __name__ == '__main__':
	s = 'RLRRLLRLRL'
	print(balanced(s))
	s = 'RLRRRLLRLL'
	print(balanced(s))
	s = 'LLLLRRRR'
	print(balanced(s))
