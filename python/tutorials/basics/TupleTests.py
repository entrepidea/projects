#Tuple is enclosed using parentnesses, while list uses bracket
#Tuple's elements can't be changed so it's regarded as read-only list

tuple = ( 'abcd', 786 , 2.23, 'john', 70.2  )
tinytuple = (123, 'john')

print tuple           # Prints complete list
print tuple[0]        # Prints first element of the list
print tuple[1:3]      # Prints elements starting from 2nd till 3rd
print tuple[2:]       # Prints elements starting from 3rd element
print tinytuple * 2   # Prints list two times
print tuple + tinytuple # Prints concatenated lists

#delete tuple elements
tup = ('physics', 'chemistry', 1997, 2000)

#tuples is sequence and will be treated as such
print 'tup[2] = '+ tup[1]
print 'tup from 2nd element: '
print  tup[1:]
del tup;
#print tup #will report error as tup no longer exists


print 3 in (1,2,3)
for x in (1,2,3):
    print x

#test built-in tuple functions
tuple1, tuple2 = (123, 'xyz', 'zara'), (456, 'abc')

print "First tuple length : ", len(tuple1)
print "Second tuple length : ", len(tuple2)