"""
Given an integer num, return the number of steps to reduce it to zero.

In one step, if the current number is even, you have to divide it by 2, otherwise, you have to subtract 1 from it.
https://leetcode.com/problems/number-of-steps-to-reduce-a-number-to-zero/

11/05/22

"""

def step_2_zero(num : int) -> int:
	rlt = 0
	while num != 0:
		if num%2==0:
			num = num//2
			rlt += 1
		else:
			num -= 1
			rlt += 1

	return rlt

if __name__ == '__main__':

		num = 14
		print(step_2_zero(num))
		num = 8
		print(step_2_zero(num))
		num = 123
		print(step_2_zero(num))
