"""
find the elements intersecting two arrays
https://leetcode.com/problems/intersection-of-two-arrays/

Date: 10/29/21

"""
def inter(n1,n2):
	rlt = []
	dic = {}
	for e in n1:
		if e not in dic:
			dic[e] = 1

	for e in n2:
		if e in dic and dic[e] == 1:
			dic[e] = 0	

	for e in dic.keys():
		if dic[e] == 0:
			rlt.append(e)

	return rlt

if __name__ == '__main__':
	n1 = [1,2,2,1]
	n2 = [2,2]
	print(inter(n1,n2))
	n1 = [4,9,5]
	n2 = [9,4,9,8,4]
	print(inter(n1,n2))
