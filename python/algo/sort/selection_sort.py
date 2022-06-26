import sys
import random
"""
selection sorting. Not for large array, or performance will suffer. 
The idea is that: start the first element, find out the smallest of the array, swap it with the first element. 
Then move on to the 2nd element, repeat the above process, swap it with 2nd element. And so on and so forth.

https://www.tutorialspoint.com/data_structures_algorithms/selection_sort_algorithm.htm
 
"""
def find_min(arr):
    min = arr[0]
    pos=0
    for i in range(1, len(arr),1):
        if arr[i]<min:
            min = arr[i]
            pos=i

    return min,pos

def selection_sort(arr):
    orig_arr = arr
    k=len(arr)
    curr_pos = 0
    while k>0:
        min,pos = find_min(arr)
        orig_arr[curr_pos], orig_arr[curr_pos+pos] = orig_arr[curr_pos+pos], orig_arr[curr_pos]
        curr_pos = curr_pos+1
        arr = orig_arr[curr_pos:]
        k = k-1

    return orig_arr

def selection_sort2(arr):
	for i in range(len(arr)-1):
		for j in range(i+1, len(arr)):
			if arr[i] > arr[j]:
				arr[i], arr[j] = arr[j], arr[i]
	return arr	


def print_data(arr):
	for i in range(len(arr)):
		print ("%3d"%arr[i], end=' ')


#redo on 09/30/21, testing. Refer to the link below:
#https://mp.weixin.qq.com/s?__biz=MzUyNjQxNjYyMg==&mid=2247485556&idx=1&sn=344738dd74b211e091f8f3477bdf91ee&chksm=fa0e67f5cd79eee3139d4667f3b94fa9618067efc45a797b69b41105a7f313654d0e86949607&scene=21#wechat_redirect
def select3(data):
    if len(data) == 1:
        return
    k = 0
    while k < len(data):
        smallest = data[k]
        idx = k
        for i in range(k,len(data)):
           if data[i] < smallest:
               smallest = data[i]
               idx = i
        temp = data[k]
        data[k] = data[idx]
        data[idx] = temp
        k += 1
        
if __name__ == '__main__':
    arr = [55,23,87,62,99,16,79,11]
    print("unsorted array is: ")
    print(arr)
    select3(arr)
    print("sorted array is: ")
    print_data(arr)


