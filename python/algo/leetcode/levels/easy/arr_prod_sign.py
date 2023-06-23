"""
There is a function signFunc(x) that returns:

1 if x is positive.
-1 if x is negative.
0 if x is equal to 0.
You are given an integer array nums. Let product be the product of all values in the array nums.

Return signFunc(product).

https://leetcode.com/problems/sign-of-the-product-of-an-array/

06/19/23

"""

def foo(nums:[])->int:
	p = 1
	for i in nums:
		p*=i
	return 0 if p==0 else 1 if p>0 else -1

if __name__ == '__main__':
	nums = [-1,-2,-3,-4,3,2,1]
	print(foo(nums))
	nums = [1,5,0,2,-3]
	print(foo(nums))
	nums = [-1,1,-1,1,-1]
	print(foo(nums))
