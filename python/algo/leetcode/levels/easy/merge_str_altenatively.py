"""
You are given two strings word1 and word2. Merge the strings by adding letters in alternating order, starting with word1. If a string is longer than the other, append the additional letters onto the end of the merged string.

Return the merged string.

https://leetcode.com/problems/merge-strings-alternately/

06/17/23

"""

def foo(s1:str,s2:str)->str:
	l = min(len(s1),len(s2))
	ret = []
	for i in range(l):
		ret.append(s1[i])
		ret.append(s2[i])
	if len(s1)>l:
		ret.append(s1[l:])			
	if len(s2)>l:
		ret.append(s2[l:])			

	return ''.join(ret)

if __name__ == '__main__':
	word1,word2 = 'abc','pqr'
	print(foo(word1,word2))
	word1,word2 = 'ab','pqrs'
	print(foo(word1,word2))
	word1,word2 = 'abcd','pq'
	print(foo(word1,word2))
