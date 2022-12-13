"""
Given an binary array nums and an integer k, return true if all 1's are at least k places away from each other, otherwise return false.

https://leetcode.com/problems/check-if-all-1s-are-at-least-length-k-places-away/

12/04/22

"""
def k_places(num : [int], k : int)->bool:
	i = 0
	m = 0
	print(f'num={num}')
	while i < len(num):
		while num[i] == 0:
			i += 1
		j = i+1
		while j<len(num) and num[j] == 0:
			j += 1
		print(f'space={j}-{i}-1={j-i-1}, length={len(num)}',end=' ')
		if j-i-1<k and j<len(num):
			return False
		i = j
		print(f'i={i}')
	return True		

#following is a neat solution. One thing learned about Python is that we can put code after :, no new line is required!
#https://leetcode.com/problems/check-if-all-1s-are-at-least-length-k-places-away/discuss/1034337/Python-Just-iterate-data-explained
#12/06/22
def k_places2(nums,k):
	dist = k
	for num in nums:
		if num == 0: dist += 1
		elif num == 1 and dist >= k: dist = 0
		else: return False
	return True
	
if __name__ == '__main__':
	num = [1,0,0,0,1,0,0,1]
	k = 2
	print(k_places2(num, k))
	num = [1,0,0,1,0,1]
	print(k_places2(num, k))


