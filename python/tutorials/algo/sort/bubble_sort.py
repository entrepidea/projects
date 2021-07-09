"""


"""
import sys
import numpy

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

def main(argv):
		"""
    data = numpy.random.randint(0,100,10) #create 10 random number with low bound = 0 and high bound = 100
    print(data)
    bubble_sorting5(data)
    print(data)
		"""
		arr = [55,23,87,62,99,16,79,11]
		bubble_sorting5(arr)
		print(arr)

if __name__ == "__main__":
    main(sys.argv[1:])
