"""
Given a m x n matrix grid which is sorted in non-increasing order both row-wise and column-wise, return the number of negative numbers in grid.
https://leetcode.com/problems/count-negative-numbers-in-a-sorted-matrix/

11/14/22

"""
def neg(mat : [[int]])->int:
	rlt = 0
	for row in mat:
		rlt += len(list(filter(lambda x:x<0, row)))
	return rlt
if __name__ == '__main__':
	mat = [[4,3,2,-1],[3,2,1,-1],[1,1,-1,-2],[-1,-1,-2,-3]]
	print(neg(mat))
	mat = [[3,2],[1,0]]
	print(neg(mat))

