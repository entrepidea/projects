"""
Given an array arr of integers, check if there exist two indices i and j such that :

https://leetcode.com/problems/check-if-n-and-its-double-exist/

11/14/22

"""
def is_exist(arr) -> bool:
	s = set()
	for i in arr:
		if 2*i in s or i/2 in s:
			return True
		else:
			s.add(i)
	return False

if __name__ == '__main__':
	arr = [5,2,10,3]
	print(is_exist(arr))
	arr = [10,2,5,3]
	print(is_exist(arr))
	arr = [3,1,7,11]
	print(is_exist(arr))
	arr = [3,1,7,11,14]
	print(is_exist(arr))
	
