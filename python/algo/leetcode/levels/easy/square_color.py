"""
You are given coordinates, a string that represents the coordinates of a square of the chessboard. Below is a chessboard for your reference.

https://leetcode.com/problems/determine-color-of-a-chessboard-square/

06/18/23

"""

def foo(s:str)->bool:
	odd_black = ['a','c','e','g']
	even_black = ['b','d','f','h']

	letter,digit = s[:1], int(s[1:])
	if letter in odd_black:
		return False if digit%2!=0 else True
	if letter in even_black:
		return True if digit%2!=0 else False


if __name__ == '__main__':
	print(foo('a1'))
	print(foo('h3'))
	print(foo('c7'))
	print(foo('f2'))
	print(foo('f3'))


