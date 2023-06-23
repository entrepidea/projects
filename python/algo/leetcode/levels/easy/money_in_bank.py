"""
Hercy wants to save money for his first car. He puts money in the Leetcode bank every day.

He starts by putting in $1 on Monday, the first day. Every day from Tuesday to Sunday, he will put in $1 more than the day before. On every subsequent Monday, he will put in $1 more than the previous Monday.
Given n, return the total amount of money he will have in the Leetcode bank at the end of the nth day.

https://leetcode.com/problems/calculate-money-in-leetcode-bank/

06/15/23

NOTE: answer of example3 on the above link seems not correct.

"""
def foo(n : int) ->int:
	x,y = n//7,n%7
	print(f'x={x},y={y}')

	s = 0
	for i in range(1,x+1):
		s += 28*i
	print(s)
	for j in range(1,y+1):
		s += x+j
	print(s)

	return s
	
if __name__ == '__main__':
	n = 20
	print(foo(n))
	
