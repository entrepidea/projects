"""
Given two non-negative integers low and high. Return the count of odd numbers between low and high (inclusive).
https://leetcode.com/problems/count-odd-numbers-in-an-interval-range/

12/31/22

"""
def odd(low, high)->int:
	rlt = []
	for i in range(low, high+1):
		if i%2==1:
			rlt.append(i)
	return len(rlt)

#https://leetcode.com/problems/count-odd-numbers-in-an-interval-range/solutions/2911369/blazingly-fast-solution/
#TODO why?
def odd2(low,high)->int:
	return int((high + high%2-low+low%2)/2)

if __name__ == '__main__':
	low = 3
	high = 7
	print(odd(low,high))
	print(odd2(low,high))
