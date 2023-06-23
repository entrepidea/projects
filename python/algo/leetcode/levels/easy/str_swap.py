"""
You are given two strings s1 and s2 of equal length. A string swap is an operation where you choose two indices in a string (not necessarily different) and swap the characters at these indices.

Return true if it is possible to make both strings equal by performing at most one string swap on exactly one of the strings. Otherwise, return false.

https://leetcode.com/problems/check-if-one-string-swap-can-make-strings-equal/

06/18/23

"""

def foo(s1:str,s2:str)->bool:
	if sorted(s1) != sorted(s2):
		return False
	cnt = 0
	l1,l2 = list(s1),list(s2)
	for i in range(len(l1)):
		if l1[i]!=l2[i]:
			cnt += 1	
	return cnt==0 or cnt==2

if __name__ == '__main__':
	s1,s2='bank','kanb'
	print(foo(s1,s2))
	s1 = "kelb"
	s2 = "kelb"
	print(foo(s1,s2))
	s1 = "abb"
	s2 = "aab"
	print(foo(s1,s2))
