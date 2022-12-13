"""
Given a string, use the characters within to form word "balloon" as many as possbile. Each character can be used at most once. Return the number of occurances.

10/15/22

"""
from typing import List

#https://leetcode.com/problems/maximum-number-of-balloons/discuss/2495650/Very-Simple-C%2B%2B-faster-then-99.99-or-O(1)-space-or-with-explanation
def balloon_num(text : List[str])->int:
	a = 0
	b = 0 
	l2 = 0 
	o2 = 0 
	n = 0

	for c in text:
		if c == 'b':
			b += 1
		elif c == 'a':
			a += 1
		elif c == 'l':
			l2 += 1
		elif c == 'o':
			o2 += 1
		elif c == 'n':
			n += 1

	l2 /= 2
	o2 /=2
	
	return min(a,b,l2,o2,n)

if __name__ == '__main__':
	text = "nlaebolko"
	print(balloon_num(text))
	text = "loonbalxballpoon"
	print(balloon_num(text))
