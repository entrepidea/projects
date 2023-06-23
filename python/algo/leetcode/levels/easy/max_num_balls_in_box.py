"""
You are working in a ball factory where you have n balls numbered from lowLimit up to highLimit inclusive (i.e., n == highLimit - lowLimit + 1), and an infinite number of boxes numbered from 1 to infinity.

Your job at this factory is to put each ball in the box with a number equal to the sum of digits of the ball's number. For example, the ball number 321 will be put in the box number 3 + 2 + 1 = 6 and the ball number 10 will be put in the box number 1 + 0 = 1.

Given two integers lowLimit and highLimit, return the number of balls in the box with the most balls.

https://leetcode.com/problems/maximum-number-of-balls-in-a-box/

06/16/23

"""
def in_box(ball_num:int)->int:
	n = ball_num
	s = 0
	while n//10:
		s += n%10
		n = n//10
	
	return s+n

def foo(low:int, high:int)->int:
	boxes = dict()
	for i in range(low,high+1):
		print(f'in_box({i})={in_box(i)}')

		if in_box(i) not in boxes.keys():
			boxes[in_box(i)] = 1
		else:
			boxes[in_box(i)] += 1

	print(boxes)
	l = [(k,v) for k, v in boxes.items()]
	print(l)	

	l = sorted(l, key=lambda x:x[1], reverse=True)
	return l[0][1]

if __name__ == '__main__':
	low_limit,high_limit = 1,10
	print(foo(low_limit,high_limit))
	low_limit,high_limit = 5,15
	print(foo(low_limit,high_limit))
	low_limit,high_limit = 19,28
	print(foo(low_limit,high_limit))
	
