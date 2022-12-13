"""
You are given an m x n binary matrix mat of 1's (representing soldiers) and 0's (representing civilians). The soldiers are positioned in front of the civilians. That is, all the 1's will appear to the left of all the 0's in each row.

A row i is weaker than a row j if one of the following is true:

The number of soldiers in row i is less than the number of soldiers in row j.
Both rows have the same number of soldiers and i < j.
Return the indices of the k weakest rows in the matrix ordered from weakest to strongest.

https://leetcode.com/problems/the-k-weakest-rows-in-a-matrix/

11/13/22

"""
def weak(mat : [[int]], k : int)->[int]:
	#the function enumerate will include index in the array
	#https://stackoverflow.com/questions/4367087/indexing-of-same-values-in-a-list
	soldiers = list(enumerate([row.count(1) for row in mat]))
	#print(soldiers)
	soldiers.sort(key=lambda x : x[1])
	rlt = [x[0] for x in soldiers]
	return rlt[:k]
	
if __name__ == '__main__':
	matrix = [[1,1,0,0,0],
		 [1,1,1,1,0],
		 [1,0,0,0,0],
		 [1,1,0,0,0],
		 [1,1,1,1,1]]
	k = 3
	print(weak(matrix,k))
	
	matrix = [[1,0,0,0],
	 [1,1,1,1],
	 [1,0,0,0],
	 [1,0,0,0]] 
	k = 2
	print(weak(matrix,k))
