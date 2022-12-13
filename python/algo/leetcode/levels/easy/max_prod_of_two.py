"""

Given the array of integers nums, you will choose two different indices i and j of that array. Return the maximum value of (nums[i]-1)*(nums[j]-1).
https://leetcode.com/problems/maximum-product-of-two-elements-in-an-array/

12/10/22

"""
def prod(arr : [int])->int:
	arr.sort(reverse=True)
	return max((arr[0]-1)*(arr[1]-1), (arr[-1]-1)*(arr[-2]-1))
	
if __name__ == '__main__':
	arr = [3,4,5,2]
	print(prod(arr))
	arr = [1,5,4,5]
	print(prod(arr))
	arr = [3,7]
	print(prod(arr))
