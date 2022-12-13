"""
Given a string of English letters s, return the greatest English letter which occurs as both a lowercase and uppercase letter in s. The returned letter should be in uppercase. If no such letter exists, return an empty string.

An English letter b is greater than another letter a if b appears after a in the English alphabet.

https://leetcode.com/problems/greatest-english-letter-in-upper-and-lower-case/

10/23/22

"""
from typing import List
def greatest(text : List[str]) -> str:
	lower = [0]*26
	upper = [0]*26
	for i in range(len(text)):
		if ord(text[i]) <= ord('z') and ord(text[i]) >= ord('a'):
			lower[ord(text[i])-ord('a')] += 1
		if ord(text[i]) <= ord('Z') and ord(text[i]) >= ord('A'):
			upper[ord(text[i])-ord('A')] += 1
	
	for i in range(25,-1,-1):
		if lower[i] > 0 and upper[i] > 0:
			return chr(ord('A')+i)

	return ''

if __name__ == '__main__':
	s = 'lEeTcOdE'
	print(greatest(s))
	s = "arRAzFif"
	print(greatest(s))
	s = "AbCdEfGhIjK"
	print(greatest(s))
