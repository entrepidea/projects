"""
Given an array of integers arr, replace each element with its rank.

The rank represents how large the element is. The rank has the following rules:

Rank is an integer starting from 1.
The larger the element, the larger the rank. If two elements are equal, their rank must be the same.
Rank should be as small as possible.

https://leetcode.com/problems/rank-transform-of-an-array/

09/17/22

"""

def rank(arr: []) -> []:
	new_arr = []
	for i in range(len(arr)):
		t = (arr[i],i) 
		new_arr.append(t)
		
	for i in range(len(new_arr)-1):
		for j in range(len(new_arr)-i-1):
				if new_arr[j][0]>new_arr[j+1][0]:
					new_arr[j],new_arr[j+1] = new_arr[j+1],new_arr[j]

	ret = [0]*len(new_arr)

	x = 1
	cur = new_arr[0][0]

	for i in range(len(new_arr)):
		inx = new_arr[i][1]
		if new_arr[i][0] == cur:
			ret[inx] = x
		else:
			cur = new_arr[i][0]
			x += 1
			ret[inx] = x
	
	return ret


if __name__ == '__main__':
	arr = [40,10,20,30]
	print(rank(arr))
	arr = [100,100,100]
	print(rank(arr))
	arr = [37,12,28,9,100,56,80,5,12]
	print(rank(arr))

