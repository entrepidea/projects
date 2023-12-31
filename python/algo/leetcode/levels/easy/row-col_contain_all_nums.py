"""
An n x n matrix is valid if every row and every column contains all the integers from 1 to n (inclusive).

Given an n x n integer matrix matrix, return true if the matrix is valid. Otherwise, return false.

https://leetcode.com/problems/check-if-every-row-and-column-contains-all-numbers/

ref: https://leetcode.com/problems/check-if-every-row-and-column-contains-all-numbers/solutions/1679058/python3-simple-2-lines/

12/31/23

"""
def chk_valid(matrix:List[List[int]]):
    set_ = set(range(1,len(matrix)+1))
    return all(set_ == set(x) for x in matrix+list(zip(*matrix)))


if __name == '__main__':
    mat = [[1,2,3],[3,1,2],[2,3,1]]
    print(chk_valid(mat))
