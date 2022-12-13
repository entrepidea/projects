"""
Given an array of string words, return all strings in words that is a substring of another word. You can return the answer in any order.

A substring is a contiguous sequence of characters within a string

https://leetcode.com/problems/string-matching-in-an-array/

12/01/22

"""

#NOTE: python can be done as simple as "as" in "mass" to determine a substring. 
#thus the method renders unnecessary. but the code below can be a good practice thou.
def is_sub(s, s2)->bool:
	if len(s)>len(s2) or s==s2:
		return False
	l = list(s)
	l2 = list(s2)
	for i in range(len(l2)):
		if l2[i] == l[0]:
			if s2[i:i+len(s)] == s:
				return True
	return False
			
def sub_str(words : [str])->[str]:
	ret = []
	for s in words:
		print(f's={s}')
		for s2 in words:
			if is_sub(s,s2):
				ret.append(s)

	return ret

if __name__ == '__main__':
	words = ["mass","as","hero","superhero"]
	print(sub_str(words))
	words = ["leetcode","et","code"]
	print(sub_str(words))

