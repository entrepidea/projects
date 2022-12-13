"""
Given an array nums of integers, return how many of them contain an even number of digits.
https://leetcode.com/problems/find-numbers-with-even-number-of-digits/

10/30/22

"""

#intuitive way
def even(nums : [])->int:
	r = 0
	for n in nums:
		cnt = 0
		while n//10:
			cnt += 1
			n = n//10
		cnt += 1
		if cnt%2 == 0:
			r += 1
	return r
		
#tricky solution
def even2(nums : []) -> int:
	r = 0
	for n in nums:
		s = str(n)
		if len(s)%2==0:
			r += 1
	return r


if __name__ == '__main__':
	nums = [12,345,2,6,7896]
	print(even(nums))
	nums = [555,901,482,1771]
	print(even(nums))
	nums = [12,345,2,6,7896]
	print(even2(nums))
	nums = [555,901,482,1771]
	print(even2(nums))
