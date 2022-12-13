"""
Given an array of integers nums, you start with an initial positive value startValue.

In each iteration, you calculate the step by step sum of startValue plus elements in nums (from left to right).

Return the minimum positive value of startValue such that the step by step sum is never less than 1.

https://leetcode.com/problems/minimum-value-to-get-positive-step-by-step-sum/

11/27/22

"""
def steps(arr : [int])->int:
	last_neg_idx = -1
	for i in range(len(arr)-1,-1,-1):
		#print(f'index={i},arr[{i}]={arr[i]}, arr={arr}')
		if arr[i]<0:
			last_neg_idx = i
			break
	s = sum(arr[:last_neg_idx+1])
	#print(f'last negative index ={last_neg_idx}, sum={s}')
	return 1 if s>=0 else abs(s)+1

if __name__ == '__main__':
	arr = [-3,2,-3,4,2]
	print(steps(arr))
	arr = [1,2]
	print(steps(arr))
	arr = [1,-2,-3]
	print(steps(arr))
