"""
determine if a number is the power of four or not.
https://leetcode.com/problems/power-of-four/

Date: 10/29/21

"""
def power(num) -> bool:
	if num == 0:
		return False
	if num == 4:
		return True
	if num%4!=0:
		return False
	return power(num//4)


if __name__ == '__main__':
	print(power(16))
	print(power(4))
	print(power(0))
	print(power(5))
	print(power(1))
