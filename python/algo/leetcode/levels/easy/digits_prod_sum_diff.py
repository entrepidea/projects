"""
Given an integer number n, return the difference between the product of its digits and the sum of its digits.
https://leetcode.com/problems/subtract-the-product-and-sum-of-digits-of-an-integer/
10/29/22
"""

def diff(num : int) -> int:
	prod = 1
	su = 0
	while num:
		#print(f'num={num}')
		d = num%10
		prod *= d
		su += d
		num = num//10
	
	return prod - su

	

if __name__ == '__main__':
	print(diff(234))
	print(diff(4421))
