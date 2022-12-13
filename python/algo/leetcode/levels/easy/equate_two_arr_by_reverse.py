"""
You are given two integer arrays of equal length target and arr. In one step, you can select any non-empty subarray of arr and reverse it. You are allowed to make any number of steps.

Return true if you can make arr equal to target or false otherwise.

https://leetcode.com/problems/make-two-arrays-equal-by-reversing-subarrays/description/

12/06/22

"""
def rever(target, arr):
	return len(set(target)-set(arr)) == 0

if __name__ == '__main__':
	target = [1,2,3,4] 
	arr = [2,4,1,3]	
	print(rever(target,arr))
