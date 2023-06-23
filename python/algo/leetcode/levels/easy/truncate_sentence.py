"""
A sentence is a list of words that are separated by a single space with no leading or trailing spaces. Each of the words consists of only uppercase and lowercase English letters (no punctuation).

For example, "Hello World", "HELLO", and "hello world hello world" are all sentences.
You are given a sentence s and an integer k. You want to truncate s such that it contains only the first k words. Return s after truncating it.

https://leetcode.com/problems/truncate-sentence/

06/19/23

"""

def foo(s:str,k:int)->str:
	i = 0
	cnt=0
	for c in s:
		i+=1
		if c==' ':
			cnt +=1
		if cnt==k:
			return s[:i]
	return s

if __name__ == '__main__':
	s,k= 'Hello how are you Contestant',4
	print(foo(s,k))
	s,k= 'What is the solution to this problem',4
	print(foo(s,k))
	s,k = 'chopper is not a tanuki',5
	print(foo(s,k))
