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
                isSorted = False

        if isSorted:
            break

    return data

def main(argv):
    data = numpy.random.randint(0,100,10) #create 10 random number with low bound = 0 and high bound = 100
    print(bubble_sorting(data))

if __name__ == "__main__":
    main(sys.argv[1:])