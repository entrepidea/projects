"""
Given an array of integers arr, a lucky integer is an integer that has a frequency in the array equal to its value.

Return the largest lucky integer in the array. If there is no lucky integer return -1.

https://leetcode.com/problems/find-lucky-integer-in-an-array/

11/26/22

"""
def lucky(arr : [int])->int:
	rlt = -1
	for i in arr:
		cnt = arr.count(i)
		if i == cnt:	
			if i > rlt:
				rlt = i

	return rlt

if __name__ == '__main__':
	arr = [2,2,3,4]
	print(lucky(arr))
	arr = [1,2,2,3,3,3]
	print(lucky(arr))
	arr = [2,2,2,3,3]
	print(lucky(arr))
