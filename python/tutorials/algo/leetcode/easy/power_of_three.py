"""
determin if a number is the power of three
https://leetcode.com/problems/power-of-three/

Date: 10/29/21

"""

def power(num) -> bool:
	#print(int(num))
	if int(num) == 0:
		return False
	if int(num) == 3:
		return True	
	if num%3 != 0:
		return False
	else:
		return power(num/3)

if __name__ == '__main__':
	print(power(3))
	print(power(81))
	print(power(9))
	print(power(27))
	print(power(0))
	print(power(45))
	print(power(4))
	print(power(5))
	print(power(1))
