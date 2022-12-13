"""
The Tribonacci sequence Tn is defined as follows: 

T0 = 0, T1 = 1, T2 = 1, and Tn+3 = Tn + Tn+1 + Tn+2 for n >= 0.

Given n, return the value of Tn.

https://leetcode.com/problems/n-th-tribonacci-number/

09/10/22

"""

def tribonacci(n : int) -> int:
	if n == 0:
		return n
	if n == 1 or n == 2:
		return 1

	return tribonacci(n-1) + tribonacci(n-2) + tribonacci(n-3)

if __name__ == '__main__':

	n = 4
	print(f'tribonacci for {n} is: {tribonacci(n)}')
	n = 25
	print(f'tribonacci for {n} is: {tribonacci(n)}')
	n = 37
	print(f'tribonacci for {n} is: {tribonacci(n)}')
