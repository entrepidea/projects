"""
You are given an array nums of non-negative integers. nums is considered special if there exists a number x such that there are exactly x numbers in nums that are greater than or equal to x.

Notice that x does not have to be an element in nums.

Return x if the array is special, otherwise, return -1. It can be proven that if nums is special, the value for x is unique.

https://leetcode.com/problems/special-array-with-x-elements-greater-than-or-equal-x/

01/28/23

"""
def speci(arr)->int:
	p = max(arr)
	x = p
	while x > 0:
		filtered = list(filter(lambda i:i>=x, arr))
		if len(filtered)==x:
			return x
		x -= 1
	return -1

if __name__ == '__main__':
	nums = [3,5]
	print(speci(nums))	
	nums = [0,0]
	print(speci(nums))	
	nums = [0,4,3,0,4]
	print(speci(nums))	

