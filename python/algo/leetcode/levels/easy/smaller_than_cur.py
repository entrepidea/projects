"""
Given the array nums, for each nums[i] find out how many numbers in the array are smaller than it. That is, for each nums[i] you have to count the number of valid j's such that j != i and nums[j] < nums[i].

Return the answer in an array.

https://leetcode.com/problems/how-many-numbers-are-smaller-than-the-current-number/

11/14/22

"""
def smaller(arr : [int])->[int]:
	rlt = []
	for i in arr:
		rlt.append(len(list(filter(lambda x : x< i, arr))))

	return rlt

if __name__ == '__main__':
	arr = [8,1,2,2,3]
	print(smaller(arr))
	arr = [6,5,4,8]
	print(smaller(arr))
	arr = [7,7,7,7]
	print(smaller(arr))


