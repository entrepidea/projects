'''
merge sorting.

author: Jonathan Yee
date: 08/31/21
'''
def ms(arr):

	if len(arr) <= 2:
		arr1 = arr[0:len(arr)//2]
		arr2 = arr[len(arr)//2:]
		#print('arr1={},arr2={}'.format(arr1,arr2))
		return merge(arr1,arr2)
	else:
		arr1 = arr[0:len(arr)//2]
		arr2 = arr[len(arr)//2:]
		return merge(ms(arr1),ms(arr2))


def merge(arr1, arr2):

	a = [0]*(len(arr1)+len(arr2))
	k = i = j = 0

	while k < len(a):

		if i<len(arr1) and j<len(arr2):
			if arr1[i]<arr2[j]:
				a[k] = arr1[i]
				i+=1
			else:
				a[k] = arr2[j]
				j+=1

			k+=1

		if i == len(arr1):
			for x in range(j, len(arr2)):	
				a[k] = arr2[x]
				k+=1
		
		if j == len(arr2):
			for x in range(i, len(arr1)):	
				a[k] = arr1[x]
				k+=1
	
	return a				

if __name__ == '__main__':
	arr = [2,7,60,-1,1000,200,3,20,7,3,1,9,34,21,6,11,5,4,8]
	print('original array:', end=' ')
	print(arr)
	print('sorted array:', end=' ')
	print(ms(arr))
