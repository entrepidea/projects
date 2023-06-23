"""
You are assigned to put some amount of boxes onto one truck. You are given a 2D array boxTypes, where boxTypes[i] = [numberOfBoxesi, numberOfUnitsPerBoxi]:

numberOfBoxesi is the number of boxes of type i.
numberOfUnitsPerBoxi is the number of units in each box of the type i.
You are also given an integer truckSize, which is the maximum number of boxes that can be put on the truck. You can choose any boxes to put on the truck as long as the number of boxes does not exceed truckSize.

Return the maximum total number of units that can be put on the truck.

https://leetcode.com/problems/maximum-units-on-a-truck/

06/15/23

NOTE: learn how numpy is used.
"""

import numpy as np
def foo(matrix:[[int]],ts:int):
	mat = np.array(matrix)
	t_arr = [tuple(row) for row in mat]
	sorted_t_arr = sorted(t_arr, key=lambda x: x[1], reverse=True)
	print(sorted_t_arr)
	n = ts
	s = 0
	for t in sorted_t_arr:
		if t[0]>=n:
			return s+n*t[1]
		else:
			s += t[0]*t[1]
			n -= t[0]
	return s

if __name__ == '__main__':
	boxTypes = [[1,3],[2,2],[3,1]]
	truckSize = 4
	print(foo(boxTypes,truckSize))
	boxTypes = [[5,10],[2,5],[4,7],[3,9]]
	truckSize = 10
	print(foo(boxTypes,truckSize))
