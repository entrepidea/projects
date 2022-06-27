"""
Two strings s and t are isomorphic if the characters in s can be replaced to get t.
https://leetcode.com/problems/isomorphic-strings/

date: 10/14/21

note: the understanding and implementation below is wrong. Need to redo it. TODO

"""
def isomorphic(s1, s2) -> bool:
	if len(s1) != len(s2):
		return False

	dic = {}
	arr = list(s1)
	for i in range(len(arr)):
		if dic[arr[i]] is None:
			dic[arr[i]] = 1
		else:
			dic[arr[i]] += 1

	arr = list(s2)
	for i in range(len(arr)):
		if arr[i] not in dic.keys():
			return False
		else:
			dic[arr[i]] -= 1
	
	for k in dic.keys():
		if dic[k] != 0:
			return False
	
	return True		

if __name__ == '__main__':
	print(isomorphic('abcc','cabc'))
