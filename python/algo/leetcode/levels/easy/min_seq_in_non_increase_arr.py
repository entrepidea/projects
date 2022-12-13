"""
Given the array nums, obtain a subsequence of the array whose sum of elements is strictly greater than the sum of the non included elements in such subsequence. 

If there are multiple solutions, return the subsequence with minimum size and if there still exist multiple solutions, return the subsequence with the maximum total sum of all its elements. A subsequence of an array can be obtained by erasing some (possibly zero) elements from the array. 

Note that the solution with the given constraints is guaranteed to be unique. Also return the answer sorted in non-increasing order.

https://leetcode.com/problems/minimum-subsequence-in-non-increasing-order/

11/28/22

"""

def foo(arr : [int])->[int]:
	arr.sort(reverse=True)
	cnt = 1
	while sum(arr[:cnt])<=sum(arr[cnt:]):
		cnt += 1
	return arr[:cnt]

if __name__ == '__main__':
	arr = [4,3,10,9,8]
	print(foo(arr))
	arr = [4,4,7,6,7]
	print(foo(arr))
