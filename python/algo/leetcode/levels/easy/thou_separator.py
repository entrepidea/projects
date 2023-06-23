"""
Given an integer n, add a dot (".") as the thousands separator and return it in string format.
https://leetcode.com/problems/thousand-separator/
01/22/23

"""
def thou(n : int)->str:
	if n//1000 > 0:
		return str(n//1000)+'.'+str(n%1000)
	else:
		return str(n)

if __name__ == '__main__':
	n = 987
	print(thou(n))
	n = 1234
	print(thou(n))
