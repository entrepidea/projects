"""
move all zeros to the end of an array and keep the order of the non-zero elements intacted
https://leetcode.com/problems/move-zeroes/

Date: 10/28/21

"""
def mov_zeros(arr):
	cur = 0
	while cur < len(arr):

		#print(f'cur={cur}, arr[cur]={arr[cur]}')
		while arr[cur] != 0:
			if cur == len(arr) -1: #reach the last element in the list
				return
			cur+=1

		while arr[cur] == 0:
			if cur == len(arr) -1: #reach the last element in the list
				return
			cur+=1

		temp = arr[cur]
		arr[cur] = 0
		j = cur-1
		while arr[j] == 0:
			j -= 1
		arr[j+1] = temp
		#print(f'j={j}, arr[j+1] = {arr[j+1]}')

if __name__ == '__main__':
	#arr = [0,1,0,3,12] # this test passed
	#arr = [6,0,1,0,3,12] # this test passed
	arr = [6,0,8,0,1,0,3,0,0,12,0] # this test passed
	print(arr)
	mov_zeros(arr)
	print(arr)
