"""
A fancy string is a string where no three consecutive characters are equal.

Given a string s, delete the minimum possible number of characters from s to make it fancy.

Return the final string after the deletion. It can be shown that the answer will always be unique.

https://leetcode.com/problems/delete-characters-to-make-fancy-string/

10/15/22

"""
from typing import List
def fancy(text : List[str]) -> str:
	if len(text)<3:
		return ""
	ret = []
	ret.append(text[0])
	for i in range(1,len(text)-1):
		if text[i] == text[i-1] and text[i] == text[i+1]:
			continue
		ret.append(text[i])
	ret.append(text[len(text)-1])
	return ''.join(ret)
			
if __name__ == '__main__':
	text = 'leeetcode'
	print(f'original: {text}, fancy string: {fancy(text)}')
	text = "aaabaaaa"
	print(f'original: {text}, fancy string: {fancy(text)}')
