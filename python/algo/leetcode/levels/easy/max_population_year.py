"""
You are given a 2D integer array logs where each logs[i] = [birthi, deathi] indicates the birth and death years of the ith person.

The population of some year x is the number of people alive during that year. The ith person is counted in year x's population if x is in the inclusive range [birthi, deathi - 1]. Note that the person is not counted in the year that they die.

Return the earliest year with the maximum population.

https://leetcode.com/problems/maximum-population-year/

06/25/23

"""
import numpy as np
import sys
#https://leetcode.com/problems/maximum-population-year/solutions/3571976/o-n-optimized-and-easy-to-understand-code/
def count_pop(logs:[tuple], year:int)->int:
	cnt = 0
	for person in logs:
		b = person[0]
		d = person[1]
		if b<=year and year < d:
			cnt += 1
	return cnt

def foo(logs:[[int]]):
	logs = [tuple(row) for row in np.array(logs)]
	print(logs)
	pop = [0]*101
	for y in range(1950,2051):
		pop[y-1950] = count_pop(logs,y)

	max_pop,min_yr = 0,sys.maxsize

	for i in range(100,-1,-1):
		p = pop[i]
		if p>=max_pop:
			min_yr = i+1950
			max_pop = p

	return min_yr

if __name__ == '__main__':
	logs = [[1993,1999],[2000,2010]]
	print(foo(logs))
	logs = [[1950,1961],[1960,1971],[1970,1981]]
	print(foo(logs))
