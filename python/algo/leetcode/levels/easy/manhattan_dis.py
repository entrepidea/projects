"""
You are given two integers, x and y, which represent your current location on a Cartesian grid: (x, y). You are also given an array points where each points[i] = [ai, bi] represents that a point exists at (ai, bi). A point is valid if it shares the same x-coordinate or the same y-coordinate as your location.

Return the index (0-indexed) of the valid point with the smallest Manhattan distance from your current location. If there are multiple, return the valid point with the smallest index. If there are no valid points, return -1.

The Manhattan distance between two points (x1, y1) and (x2, y2) is abs(x1 - x2) + abs(y1 - y2).

https://leetcode.com/problems/find-nearest-point-that-has-the-same-x-or-y-coordinate/

06/17/23

"""
import numpy as np
import sys
def foo(x:int,y:int,points: [[int]]):
	t_arr = [tuple(row) for row in np.array(points)]
	print(t_arr)
	r = sys.maxsize
	inx = sys.maxsize
	i = 0
	for t in t_arr:
		if x == t[0] or y == t[1]:
			d = abs(x-t[0])+abs(y-t[1])
			if d <= r:	
				print(f'x={x},y={y},t={t},man_dis={d},i={i}')
				if d < r:
					inx = i
				r = d
		i += 1
	
	return -1 if inx == sys.maxsize else inx

if __name__ == '__main__':
	x,y,points = 3,4,[[1,2],[3,1],[2,4],[2,3],[4,4]]
	print(foo(x,y,points))
	x,y,points = 3,4,[[3,4]]
	print(foo(x,y,points))
	x,y,points = 3,4,[[2,3]]
	print(foo(x,y,points))
