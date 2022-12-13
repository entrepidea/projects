"""
Given an integer n, return any array containing n unique integers such that they add up to 0.
https://leetcode.com/problems/find-n-unique-integers-sum-up-to-zero/
10/30/22

"""

#https://leetcode.com/problems/find-n-unique-integers-sum-up-to-zero/discuss/465585/JavaC%2B%2BPython-Find-the-Rule
def arr_cons(n : int) -> []:
	r = []
	for i in range(1-n,n,2):
		r.append(i)
	return r
if __name__ == '__main__':
	n = 5
	print(arr_cons(n))
