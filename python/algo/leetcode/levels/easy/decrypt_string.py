"""
You are given a string s formed by digits and '#'. We want to map s to English lowercase characters as follows:

Characters ('a' to 'i') are represented by ('1' to '9') respectively.
Characters ('j' to 'z') are represented by ('10#' to '26#') respectively.
Return the string formed after mapping.

The test cases are generated so that a unique mapping will always exist.

https://leetcode.com/problems/decrypt-string-from-alphabet-to-integer-mapping/

11/05/22

"""

from collections import defaultdict
from typing import List
def decryt(text : List[str]) ->str:
	d = defaultdict()
	s = 'abcdefghi'
	letters = [x for x in s]
	for c in letters:
		d[str(ord(c)-ord('a')+1)] = c

	s = 'jklmnopqrstuvwxyz'
	letters = [x for x in s]
	for c in letters:
		tmp = str(ord(c)-ord('j')+10)+'#'
		d[tmp] = c

	

	#for k, v in d.items():
	#	print(f'{k},{v}')
	rlt = []
	print(f'text = {text}')	
	i = len(text)-1
	while i>= 0: 
		if text[i] == '#':
			k = text[i-2:i+1]
			#print(f'k={k}')
			rlt.append(d[k])
			i -= 3
		else:
			k = text[i]
			#print(f'k={k}')
			rlt.append(d[k])
			i -= 1
	
	return ''.join(rlt)[::-1]
						
	
	
if __name__ == '__main__':
	s = '1326#'
	print(f'descrpted:={decryt(s)}')
	s = '10#11#12'
	print(f'descrpted:={decryt(s)}')
	
