"""
Given an array of integers nums, sort the array in increasing order based on the frequency of the values. If multiple values have the same frequency, sort them in decreasing order.

Return the sorted array.

https://leetcode.com/problems/sort-array-by-increasing-frequency/

TODO the second part of requirement is not met.

03/02/23

"""
import collections
#below solution is borrowed from 
#https://leetcode.com/problems/sort-array-by-increasing-frequency/solutions/1065249/python-3-solution-with-process-of-thinking-and-improvement/
def sort(nums)->[]:
	ret = []
	r = collections.Counter(nums).most_common()
	r.sort(key=lambda x : x[0], reverse=True)
	r.sort(key=lambda x : x[1])
	#print(r)
	for i in r:
		a,b = i
		ret.extend([a]*b)

	return ret
	
def sort2(nums :[])->[]:
	r = collections.Counter(nums)
	nums.sort(key=lambda x:(r[x],-x))
	return nums

if __name__ == '__main__':

	nums = [1,1,2,2,2,3]
	print(nums)
	print(sort(nums))
	nums = [2,3,1,3,2]
	print(nums)
	print(sort(nums))
	nums = [-1,1,-6,4,5,-6,1,4,1]
	print(sort(nums))


	nums = [1, 1, 2, 2, 2, 6, 6, 4, 4, 5, 5, 3]
	print(sort2(nums))

