#this is to test the basic type: List
#the elements in a list can be of different types

list = [ 'abcd', 786 , 2.23, 'john', 70.2,'spam' ]
tinylist = [123, 'john']

print list          # Prints complete list
print list[0]       # Prints first element of the list
print list[1:3]     # Prints elements starting from 2nd till 3rd
print list[2:]      # Prints elements starting from 3rd element
print tinylist * 2  # Prints list two times
print list + tinylist # Prints concatenated lists


L = ['spam', 'Spam', 'SPAM!']
print L[2]
print L[-2]
print L[1:]
print L.__len__()
print len(L)
print cmp(list, L) #?

print L.sort()

