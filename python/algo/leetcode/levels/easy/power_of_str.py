"""

The power of the string is the maximum length of a non-empty substring that contains only one unique character.

Given a string s, return the power of s.

https://leetcode.com/problems/consecutive-characters/

12/03/22

"""
def uniq_sub(s : str) -> int:
	i = 0
	m = 0
	while i < len(s):
		j = i+1
		while j<len(s) and  s[i] == s[j]:
			j += 1
		m = max(j-i,m)
		i = j
	return m		

if __name__ == '__main__':
	s = "leetcode"
	print(uniq_sub(s))
	s = "abbcccddddeeeeedcba"
	print(uniq_sub(s))
