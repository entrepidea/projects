"""

"""
def mat_diag(mat)->int:
	corr_set = set()
	total = 0
	for i in range(len(mat)):
		if (i,i) not in corr_set:
			corr_set.add((i,i))
			total += mat[i][i]
	for i in range(len(mat)):
		if (i,len(mat)-i-1) not in corr_set:
			corr_set.add((i,len(mat)-i-1))
			total += mat[i][len(mat)-i-1]
	return total
	
if __name__ == '__main__':
	mat = [[1,2,3],
        [4,5,6],
        [7,8,9]]
	print(mat_diag(mat))

	mat = [[1,1,1,1],
              [1,1,1,1],
              [1,1,1,1],
              [1,1,1,1]]

	print(mat_diag(mat))
