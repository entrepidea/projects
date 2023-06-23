"""
You are given an m x n integer grid accounts where accounts[i][j] is the amount of money the i​​​​​​​​​​​th​​​​ customer has in the j​​​​​​​​​​​th​​​​ bank. Return the wealth that the richest customer has.

A customer's wealth is the amount of money they have in all their bank accounts. The richest customer is the customer that has the maximum wealth.

https://leetcode.com/problems/richest-customer-wealth/

05/17/23

"""
def foo(m : [[]])->int:
	col = len(m[0])
	row = len(m)
	ret = 0
	for i in range(0,row):
		s = 0
		for j in range(0,col):
			s += m[i][j]
		#print(f'{s},{ret}')
		if s > ret:
			ret = s

	return ret

if __name__ == '__main__':
	m = [[1,2,3],[3,2,1]]
	print(foo(m))
	m = [[1,5],[7,3],[3,5]]
	print(foo(m))
	m = [[2,8,7],[7,1,3],[1,9,5]]
	print(foo(m))
