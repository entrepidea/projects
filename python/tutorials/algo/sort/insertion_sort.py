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

def main(argv):
    data = numpy.random.randint(0,1000,10) #create an array of 20 random number
    data = insert_sorting(data)
    print(data)

if __name__ == '__main__':
    main(sys.argv[1:])