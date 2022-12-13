"""
Given an integer array sorted in non-decreasing order, there is exactly one integer in the array that occurs more than 25% of the time, return that integer.
https://leetcode.com/problems/element-appearing-more-than-25-in-sorted-array/
09/06/22
"""

def pct25_element(arr : []) -> int:
	threshold = (int)(0.25*len(arr))
	print(f'threashold = {threshold}, arr = {arr}')
	pivot = arr[0]
	i = 1
	while i < len(arr):
		count = 0
		while arr[i] == pivot:
			count += 1
			i += 1
		if count>threshold:
			return pivot
		else:
			pivot = arr[i]
	return 0					

if __name__ == '__main__':
	arr = [1,2,2,6,6,6,6,7,10]
	print(f'the element more than 25% threshold of {arr} is: {pct25_element(arr)}')
