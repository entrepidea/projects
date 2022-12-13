"""
Given two 0-indexed integer arrays nums1 and nums2, return a list answer of size 2 where:

answer[0] is a list of all distinct integers in nums1 which are not present in nums2.
answer[1] is a list of all distinct integers in nums2 which are not present in nums1.
Note that the integers in the lists may be returned in any order.

https://leetcode.com/problems/find-the-difference-of-two-arrays/

10/24/22

"""
def diff(arr1, arr2) -> []:
	s1 = set(arr1)
	s2 = set(arr2)
	
	#print(f's1={s1},s2={s2}')

	r1 = []
	r2 = []
	ret = [None]*2

	for a in arr1:
		if a not in s2:
			r1.append(a)
	#print(f'r1={r1}')		
	ret[0] = list(set(r1))

	for a in arr2:
		if a not in s1:
			r2.append(a)
	#print(f'r2={r2}')		
	ret[1] = list(set(r2))

	return ret

#one line solution
def diff2(arr1,arr2) -> []:
	return [list((set(arr1)-set(arr2))),list((set(arr2)-set(arr1)))]


if __name__ == '__main__':
	nums1 = [1,2,3]
	nums2 = [2,4,6]
	print(diff(nums1,nums2))
	nums1 = [1,2,3,3]
	nums2 = [1,1,2,2]
	print(diff(nums1,nums2))
	nums1 = [1,2,3]
	nums2 = [2,4,6]
	print(diff2(nums1,nums2))
	nums1 = [1,2,3,3]
	nums2 = [1,1,2,2]
	print(diff2(nums1,nums2))
