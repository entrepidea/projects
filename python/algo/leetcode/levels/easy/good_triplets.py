"""
Given an array of integers arr, and three integers a, b and c. You need to find the number of good triplets.

A triplet (arr[i], arr[j], arr[k]) is good if the following conditions are true:

0 <= i < j < k < arr.length
|arr[i] - arr[j]| <= a
|arr[j] - arr[k]| <= b
|arr[i] - arr[k]| <= c
Where |x| denotes the absolute value of x.

Return the number of good triplets.

https://leetcode.com/problems/count-good-triplets/

01/22/23

"""
def triplet(arr:[int],a:int,b:int,c:int)->[]:
	rlt = []
	for i in range(len(arr)-2):
		j = i+1
		k = j+1
		while abs(arr[i]-arr[j])<=a and abs(arr[j]-arr[k])<=b:
			while abs(arr[i]-arr[k])<=c:
				rlt.append((arr[i],arr[j],arr[k]))
				k += 1
			j += 1
			k = j+1	
	return rlt

if __name__ == '__main__':
	arr = [3,0,1,1,9,7]
	a = 7
	b = 2
	c = 3
	print(triplet(arr,a,b,c))

	arr = [1,1,2,2,3]
	a = 0
	b = 0
	c = 1
	print(triplet(arr,a,b,c))
