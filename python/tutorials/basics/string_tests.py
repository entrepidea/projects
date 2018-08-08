#some tests on basic type: string
#note: the statement
#print XXX
#failed on python 3+, it seems that a paratheness is needed.



str = 'hello world!'
print str
print str[0]
print str[2:5]
print str[2:]
print str*2
print str+" TEST"
print 'hello'.capitalize()
print '45'.isdigit()

#One of Python's coolest features is the string format operator %.
print "My name is %s and weight is %d kg!" % ('Zara', 21)