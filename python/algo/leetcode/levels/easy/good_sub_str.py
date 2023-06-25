"""
A string is good if there are no repeated characters.

Given a string s, return the number of good substrings of length three in s.

Note that if there are multiple occurrences of the same substring, every occurrence should be counted.

A substring is a contiguous sequence of characters in a string.

https://leetcode.com/problems/substrings-of-size-three-with-distinct-characters/

06/25/23

"""

def foo(s:str)->int:
	i = 0
	cnt = 0
	while i+3<=len(s):
		sub = s[i:i+3]
		#print(f'i={i},sub={sub}')
		l = list(sub)
		se = set(l)
		if len(l) == len(se):
			cnt += 1
		i +=1 
	return cnt

if __name__ == '__main__':
	s = 'xyzzaz'
	print(foo(s))
	s = 'aababcabc'
	print(foo(s))
