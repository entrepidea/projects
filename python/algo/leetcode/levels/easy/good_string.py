"""
Given a string s of lower and upper case English letters.

A good string is a string which doesn't have two adjacent characters s[i] and s[i + 1] where:

0 <= i <= s.length - 2
s[i] is a lower-case letter and s[i + 1] is the same letter but in upper-case or vice-versa.
To make the string good, you can choose two adjacent characters that make the string bad and remove them. You can keep doing this until the string becomes good.

Return the string after making it good. The answer is guaranteed to be unique under the given constraints.

Notice that an empty string is also good.

https://leetcode.com/problems/make-the-string-great/

01/23/23

"""
from typing import List
def good_str(s:List[str])->str:
	rlt = []
	i=0
	while i < len(s)-1:
		if (s[i].islower() and s[i].upper() == s[i+1]) or (s[i].isupper() and s[i].lower()==s[i+1]):
			i += 2
		else:
			rlt.append(s[i])
			i += 1
	if i<len(s):
		rlt.append(s[i])

	if len(rlt) == len(s):
		return ''.join(rlt)
	else:
		return good_str(rlt)		
	return ''.join(rlt)

if __name__ == '__main__':
	s = "leEeetcode"
	print(f'origin: {s}, after: {good_str(s)}')
	s = "abBAcC"
	print(f'origin: {s}, after: {good_str(s)}')
	s = "s"
	print(f'origin: {s}, after: {good_str(s)}')
