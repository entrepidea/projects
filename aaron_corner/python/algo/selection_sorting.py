"""
use selection sorting to sort a random array.
originally made by Jonathan Yee, updated by Aaron Yee on 8/21/2025.
"""
import random


#creates a random list
def create_random_list(size, lower_bound, upper_bound):
    return [random.randint(lower_bound, upper_bound) for _ in range(size)]


#returns the minimum element in an array and its position
def find_min(arr):
    min = arr[0]
    pos = 0
    for i in range(1, len(arr)):
        if arr[i] < min:
            min = arr[i]
            pos = i

    return min, pos


#sorts the array
def selection_sort(arr):
    orig_arr = arr
    k = len(arr)
    curr_pos = 0
    while k > 0:
        min, pos = find_min(arr)
        orig_arr[curr_pos], orig_arr[curr_pos + pos] = orig_arr[curr_pos + pos], orig_arr[curr_pos]
        curr_pos += 1
        arr = orig_arr[curr_pos:]
        k -= 1

    return orig_arr


#prints the data of an array
def print_data(arr):
    for i in range(len(arr)):
        if i<len(arr)-1:
            print("%3d" % arr[i], end=', ')
        else:
            print("%3d" % arr[i], end='\n')


#prints the original array and the sorted version
if __name__ == '__main__':
    #arr = [55,23,87,62,99,16,79,11]
    arr = create_random_list(10, 1, 99)
    print("unsorted array is: ")
    print_data(arr)
    selection_sort(arr)
    print("sorted array is: ")
    print_data(arr)
