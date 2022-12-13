"""
You are given an integer n.

Each number from 1 to n is grouped according to the sum of its digits.

Return the number of groups that have the largest size.

https://leetcode.com/problems/count-largest-group/

11/25/22

"""

from collections import defaultdict
def largest(n : int) -> int:
	d = defaultdict()
	for i in range(1,n+1):
		tmp = i
		s = 0
		while i//10:
			s += i%10
			i = i//10
		s += i%10
		if d.get(s) is None:
			d[s] = []
		d[s].append(tmp)	

	[print(v) for v in d.values()]
	l = [len(v) for v in d.values()]
	m = max(l)
	#print(m)
	return len(list(filter(lambda x : x == m,l))) 
		
	
if __name__ == '__main__':
	n = 13
	print(largest(n))
	n = 2
	print(largest(n))
