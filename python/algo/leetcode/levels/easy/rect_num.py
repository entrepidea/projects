"""
You are given an array rectangles where rectangles[i] = [li, wi] represents the ith rectangle of length li and width wi.

You can cut the ith rectangle to form a square with a side length of k if both k <= li and k <= wi. For example, if you have a rectangle [4,6], you can cut it to get a square with a side length of at most 4.

Let maxLen be the side length of the largest square you can obtain from any of the given rectangles.

Return the number of rectangles that can make a square with a side length of maxLen.

https://leetcode.com/problems/number-of-rectangles-that-can-form-the-largest-square/

06/16/23


"""

import numpy as np
def foo(rect : [[int]])->int:
	t_arr = [tuple(row) for row in np.array(rect)]
	print(t_arr)
	largest = 0
	n = 1
	for t in t_arr:
		s = min(t[0],t[1])
		if s > largest:
			largest = s
			n = 1
		elif s == largest:
			n += 1
	
	return n

if __name__ == '__main__':
	rectangles = [[5,8],[3,9],[5,12],[16,5]]
	print(foo(rectangles))
	rectangles = [[2,3],[3,7],[4,3],[3,7]]
	print(foo(rectangles))
