import sys
import numpy

"""
Insertion sorting. Here is a very good explanation for it:
https://www.tutorialspoint.com/data_structures_algorithms/insertion_sort_algorithm.htm

"""
def insert_sorting(data):
    for i in range(1, len(data),1):
        val_to_insert = data[i]
        hold_pos = i
        while hold_pos > 0 and data[hold_pos] < data[hold_pos-1]:
            data[hold_pos] = data[hold_pos-1] #swap
            data[hold_pos-1] = val_to_insert  #swap
            hold_pos = hold_pos-1
    return data

#another approach, 01/30/19
def insert_sorting2(data):
    for i in range(1, len(data),1):
        for j in range(i, 0, -1):
            if data[j] < data[j-1]:
                temp = data[j-1]
                data[j-1] = data[j]
                data[j] = temp
            else:
                break
    return data

#redo - practice. 07/08/21
def insert_sorting3(arr):
	for i in range(1, len(arr)):
		j = i-1
		while arr[i] < arr[j] and j > 0: 
			j = j-1
	
		for k in range(i-1,j,-1):
			arr[k+1] = arr[k]

		arr[j] = arr[i]

		print(arr)
	
	return arr 			
				

def main(argv):
    data = numpy.random.randint(0,1000,10) #create an array of 10 random number
    print(data)
    #data = insert_sorting2(data)
    data = insert_sorting3(data)
    print(data)

if __name__ == '__main__':
    main(sys.argv[1:])
