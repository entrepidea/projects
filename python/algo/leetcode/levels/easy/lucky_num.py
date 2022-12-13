"""
Given an m x n matrix of distinct numbers, return all lucky numbers in the matrix in any order.

A lucky number is an element of the matrix such that it is the minimum element in its row and maximum in its column.

https://leetcode.com/problems/lucky-numbers-in-a-matrix/

11/19/22

"""
import sys
def lucky(mat : [[int]])->[int]:
	#l = list(enumerate([row for row in mat]))
	#print(l)
	min_of_each_row = []
	for row in mat:
		#find out the min of each row and its index
		row = list(enumerate([n for n in row]))
		row.sort(key=lambda x : x[1])
		min_of_each_row.append(row[0])
		
	#[print(m) for m in min_of_each_row]
		
	rlt = []
	#now, for each min of rows, use its index(m[0]) to find out if it's the max of the col indicated by the index
	for m in min_of_each_row:
		col = m[0]
		cadidate = m[1]
		is_largest = True
		for row in mat:
			if row[col] > cadidate: # the cadidate is not the largest one, the deal is off.
				is_largest = False
				break
		if is_largest:
			rlt.append(cadidate)
	
	#print(rlt)
	return rlt
		

if __name__ == '__main__':
	mat = [[3,7,8],[9,11,13],[15,16,17]]
	print(lucky(mat))
	
	mat = [[1,10,4,2],[9,3,8,7],[15,16,17,12]]
	print(lucky(mat))
	
	mat = [[7,8],[1,2]]
	print(lucky(mat))

