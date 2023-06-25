"""
You are given a 0-indexed string s that has lowercase English letters in its even indices and digits in its odd indices.

There is a function shift(c, x), where c is a character and x is a digit, that returns the xth character after c.

For example, shift('a', 5) = 'f' and shift('x', 0) = 'x'.
For every odd index i, you want to replace the digit s[i] with shift(s[i-1], s[i]).

Return s after replacing all digits. It is guaranteed that shift(s[i-1], s[i]) will never exceed 'z'.

https://leetcode.com/problems/replace-all-digits-with-characters/

06/24/23

"""

def foo(s:str)->str:
	l = list(s)
	i = 1
	
	while i<len(l):
		c = l[i-1]
		l[i] = chr(ord(c)+int(l[i]))
		i += 2
	
	return ''.join(l)		

if __name__ == '__main__':
	s = 'a1c1e1'
	print(foo(s))
	s = 'a1b2c3d4e'
	print(foo(s))
