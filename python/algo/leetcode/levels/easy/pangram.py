"""
A pangram is a sentence where every letter of the English alphabet appears at least once.

Given a string sentence containing only lowercase English letters, return true if sentence is a pangram, or false otherwise.

https://leetcode.com/problems/check-if-the-sentence-is-pangram/

06/24/23

"""
def foo(s:str)->bool:
	arr = [0]*26
	for c in s:
		i = ord(c)-ord('a')
		arr[i] += 1
	
	for x in arr:
		if x == 0:
			return False
	return True	

if __name__ == '__main__':
	s = 'swzaqxedcrfvtgbyhnujmikolp'
	print(foo(s))
	s = 'leetcode'
	print(foo(s))
