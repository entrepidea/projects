"""
Given an m x n binary matrix mat, return the number of special positions in mat.

A position (i, j) is called special if mat[i][j] == 1 and all other elements in row i and column j are 0 (rows and columns are 0-indexed).

https://leetcode.com/problems/special-positions-in-a-binary-matrix/

01/28/23

"""
def spec_pos(mat:[[int]])->int:
	m = len(mat)
	n = len(mat[0])
	#print(f'm={m},n={n},min={min(m,n)}')
	cnt = 0
	for i in range(m):
		for j in range(n):
			if mat[i][j]==1:
				print(f'i={i},j={j}')
				mat[i][j] = 0
				k=0
				while k<n and mat[i][k]==0:
					k+=1
				if k==n:
					k=0
					while k<m and mat[k][j]==0:
						k+=1
					if k==m:
						cnt+=1 			
				mat[i][j] = 1 #reset

	return cnt

if __name__ == '__main__':

	mat = [[1,0,0],[0,0,1],[1,0,0]]
	print(spec_pos(mat))
	mat = [[1,0,0],[0,1,0],[0,0,1]]
	print(spec_pos(mat))

