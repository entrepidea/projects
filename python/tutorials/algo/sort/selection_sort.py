import sys
import random
"""
selection sorting. Not for large array, or performance will suffer. 

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


def main(argv):

	"""
    arr = random.sample(range(1,100),30)
    orig_arr = selection_sort(arr)
    print(orig_arr)
	"""

	arr = [55,23,87,62,99,16,79,11]
	print("unsorted array is: ")
	print(arr)
	arr = selection_sort2(arr)
	print("sorted array is: ")
	print_data(arr)

if __name__ == '__main__':
    main(sys.argv[1:])
