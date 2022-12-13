"""


"""
import sys
#import numpy

def bubble_sorting(data):
    for k in range(0,len(data)-1,1): # this line is equivilent to C++'s "for(int k=0;k<len-1;k++)"
        isSorted = True
        for i in range(1,len(data)-k,1):
            if data[i]<data[i-1]:
                temp = data[i]
                data[i] = data[i-1]
                data[i-1] = temp
                #data[i] = data[i-1]
                isSorted = False

        if isSorted:
            break

    return data


#redo it on 01/22/19, just practice. no change from bubble_sorting.
def bubble_sorting2(arr):
    for i in range(0, len(arr)-1,1):
        isSorted = True
        for j in range(1, len(arr)-i,1):
            if(arr[j]<arr[j-1]):
                temp = arr[j-1]
                arr[j-1] = arr[j]
                arr[j] = temp
                isSorted = False

        if(isSorted):
            break

#redo it on 01/29/19, just practice. no change from bubble_sorting.
def bubble_sorting3(arr):
    for i in range(0, len(arr)-1,1):
        is_sorted = True
        for j in range(1, len(arr)-i, 1):
            if arr[j]<arr[j-1]:
                arr[j-1],arr[j] = arr[j],arr[j-1]
                is_sorted = False

        if is_sorted:
            break


#redo on 02/27/19, practice
def bubble_sorting4(arr):
    if arr is None or len(arr) == 1:
        return arr

    for i in range(1,len(arr),1):
        is_sorted = True
        for j in range(0,len(arr)-i,1):
            if arr[j]>arr[j-1]:
                arr[j],arr[j-1] = arr[j-1],arr[j]
                is_sorted = False

            if is_sorted:
                break
    return arr

#redo on 04/20/19, practice
def bubble_sorting5(arr):
    if arr is None or len(arr) == 1:
        return arr

    for i in range(1,len(arr),1):
        is_sorted = True
        for j in range(0,len(arr)-i,1):
            if arr[j] > arr[j+1]:
                arr[j],arr[j+1] = arr[j+1],arr[j]
                is_sorted = False

        if is_sorted:
            break
    return arr

#redo on 07/07/21, practice. 
#NOTE: this is from the Chinese text book: Illustrated Algorithem - I found this version is easy to comprehend. 
def bubble_sorting6(arr):

	max_idx = len(arr)-1
	while max_idx > 0:
		cur = 0
		while cur < max_idx:
			if arr[cur]>arr[cur+1]:
				arr[cur],arr[cur+1] = arr[cur+1],arr[cur]
			cur = cur+1

		max_idx = max_idx - 1

	return arr

#redo on 09/29/21
#https://mp.weixin.qq.com/s?__biz=MzUyNjQxNjYyMg==&mid=2247485556&idx=1&sn=344738dd74b211e091f8f3477bdf91ee&chksm=fa0e67f5cd79eee3139d4667f3b94fa9618067efc45a797b69b41105a7f313654d0e86949607&scene=21#wechat_redirect
#[ 8，2，5，9，7 ]
def bubble6(data):
    for i in range(0,len(data)-1):
        for j in range(i+1, len(data)):
            print('i=%d,data[i]=%d,j=%d,data[j]=%d'%(i,data[i],j,data[j]))
            if data[i] < data[j]:
                temp = data[i]
                data[i] = data[j]
                data[j] = temp
                #data[i],data[j] = data[j],data[i]

#this one check if the array is sorted already or not. If it's, nothing needs to be done.
#found in the same link as that in bubble6
#09/29/21
def bubble7(data):
    for i in range(0,len(data)-1):
        is_sorted = True
        for j in range(0, len(data)-1-i):
            if data[j] < data[j+1]:
                temp = data[j]
                data[j] = data[j+1]
                data[j+1] = temp
                is_sorted = False
        if is_sorted:
            return

#redo a test.
#https://mp.weixin.qq.com/s?__biz=MzUyNjQxNjYyMg==&mid=2247485556&idx=1&sn=344738dd74b211e091f8f3477bdf91ee&chksm=fa0e67f5cd79eee3139d4667f3b94fa9618067efc45a797b69b41105a7f313654d0e86949607&scene=21#wechat_redirect
#09/10/22
def bubble8(arr):
	for i in range(len(arr)-1):
		for j in range(len(arr)-1-i):
			if arr[j]>arr[j+1]:
				arr[j],arr[j+1] = arr[j+1],arr[j]

def bubble8_optimized(arr):
	for i in range(len(arr)-1):
		is_sorted = True
		for j in range(len(arr)-1-i):
			if arr[j]>arr[j+1]:
				arr[j],arr[j+1] = arr[j+1],arr[j]
				is_sorted = False

		if is_sorted:
			break

if __name__ == "__main__":
    data = [8,2,5,9,11,7,1]
    bubble8(data)
    print(data)

    bubble8_optimized(data)
    print(data)
