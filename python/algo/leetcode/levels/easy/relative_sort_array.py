"""
Given two arrays arr1 and arr2, the elements of arr2 are distinct, and all elements in arr2 are also in arr1.

Sort the elements of arr1 such that the relative ordering of items in arr1 are the same as in arr2. Elements that do not appear in arr2 should be placed at the end of arr1 in ascending order.

https://leetcode.com/problems/relative-sort-array/

12/05/22

"""
def relative_sort(arr1: [], arr2: []) -> []:
	new_arr = [None] * (len(arr1))
	print(f'arr1 = {arr1}')
	print(f'arr2 = {arr2}')
	print (f'arr1 leng = {len(arr1)}, arr2 len = {len(arr2)}, new arr len = {len(new_arr)}')

	k = 0
	for i in range(len(arr2)):
		j = 0
		while j < len(arr1):
			if arr1[j] == None or arr1[j] != arr2[i]:
				j += 1
			else:
				new_arr[k] = arr1[j]
				arr1[j] = None
				k += 1
				j += 1

	j = 0
	temp = []
	while j < len(arr1):
		if arr1[j] != None:
			temp.append(arr1[j])
		j += 1

	temp.sort()
	for a in temp:
		new_arr[k] = a
		k += 1
	
	return new_arr
		
if __name__ == '__main__':
	arr1 = [2,3,1,3,2,4,6,19,9,2,7]
	arr2 = [2,1,4,3,9,6]
	print(relative_sort(arr1, arr2))
