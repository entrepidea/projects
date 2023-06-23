"""
You are given an integer array nums. The unique elements of an array are the elements that appear exactly once in the array.

Return the sum of all the unique elements of nums.

https://leetcode.com/problems/sum-of-unique-elements/

06/16/23

"""
from collections import Counter
def foo(arr : [int])->int:
	dic = Counter(arr)
	l = [(k,v) for k,v in dic.items()]
	print(l)
	l = list(filter(lambda x:x[1]==1,l))
	print(l)
	return sum(i for i, j in l)

if __name__ == '__main__':
	nums = [1,2,3,2]	
	print(foo(nums))
	nums = [1,1,1,1,1]
	print(foo(nums))
	nums = [1,2,3,4,5]
	print(foo(nums))
