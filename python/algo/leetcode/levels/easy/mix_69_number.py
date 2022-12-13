"""
You are given a positive integer num consisting only of digits 6 and 9.

Return the maximum number you can get by changing at most one digit (6 becomes 9, and 9 becomes 6).

https://leetcode.com/problems/maximum-69-number/

11/06/22

"""

def max_69(num : int) -> int:
	l = list(str(num))
	for i in range(len(l)):
		if l[i] == '6':
			l[i] = '9'
			return int(''.join(l))

	return int(''.join(l)) 

if __name__ == '__main__':
	num = 9669
	print(max_69(num))
	num = 9996
	print(max_69(num))
	num = 9999
	print(max_69(num))
