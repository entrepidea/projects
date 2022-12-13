"""
Given an array arr, replace every element in that array with the greatest element among the elements to its right, and replace the last element with -1.

After doing so, return the array.


https://leetcode.com/problems/replace-elements-with-greatest-element-on-right-side/

09/06/22

"""

def max_right(arr : []) -> int:
	ret = arr[0]
	for i in range(len(arr)):
		if arr[i] > ret:
			ret = arr[i]
	return ret
	
def replace(arr :[]) -> []:
	for i in range(len(arr)-1):
		arr[i] = max_right(arr[i+1:])
	arr[len(arr)-1] = -1
	return arr

if __name__ == '__main__':
	arr = [17,18,5,4,6,1]
	print(replace(arr))
	arr = [400]
	print(replace(arr))
