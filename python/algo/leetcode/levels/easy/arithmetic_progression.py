"""
A sequence of numbers is called an arithmetic progression if the difference between any two consecutive elements is the same.

Given an array of numbers arr, return true if the array can be rearranged to form an arithmetic progression. Otherwise, return false.

https://leetcode.com/problems/can-make-arithmetic-progression-from-sequence/

12/30/22

NOTE: it seems that distance is not in absolute teams or duplicate elements are not considered.
like: [7,0,7], is it correct?

"""
def ap(arr)->bool:
	arr.sort()
	return len(set(arr[i-1]-arr[i] for i in range(1,len(arr)))) == 1

if __name__ == '__main__':
	arr = [3,5,1]
	print(ap(arr))
