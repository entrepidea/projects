"""
A string is a valid parentheses string (denoted VPS) if it meets one of the following:

It is an empty string "", or a single character not equal to "(" or ")",
It can be written as AB (A concatenated with B), where A and B are VPS's, or
It can be written as (A), where A is a VPS.
We can similarly define the nesting depth depth(S) of any VPS S as follows:

depth("") = 0
depth(C) = 0, where C is a string with a single character not equal to "(" or ")".
depth(A + B) = max(depth(A), depth(B)), where A and B are VPS's.
depth("(" + A + ")") = 1 + depth(A), where A is a VPS.
For example, "", "()()", and "()(()())" are VPS's (with nesting depths 0, 1, and 2), and ")(" and "(()" are not VPS's.

Given a VPS represented as string s, return the nesting depth of s.

https://leetcode.com/problems/maximum-nesting-depth-of-the-parentheses/description/

02/20/23

"""

def depth(s : str) -> int:
	stack = []
	cnt = 0
	ret = 0
	for c in s:
		#print(c)
		if c == '(':
			stack.append(c)
			cnt += 1
		if c == ')' and len(stack)>0:
			stack.pop()
			if cnt > ret: 
				ret = cnt
			cnt -= 1
	
	return ret

if __name__ == '__main__':
	s = '(1+(2*3)+((8)/4))+1'
	print(depth(s))
	s = '(1)+((2))+(((3)))'
	print(depth(s))
	s = '(2)'
	print(depth(s))
	s = '(1+(1+(1+(1*2))))'
	print(depth(s))
	
