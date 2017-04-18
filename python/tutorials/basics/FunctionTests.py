#https://www.tutorialspoint.com/python/python_functions.htm

#all argument lists or parameters passed to a function is passed by reference.
def changeList(list):
    list.append([1,2,3,4])
    return;

myList = [5,6,7,8]
print "original list is: ", myList
changeList(myList)
print "new list is: ", myList


def changeList2(list):

    list = [1,2,3,4] #here is a new local variable having nothing to do with the "list" passed in.
    print "list inside the function: ", list
    return

myList = [5,6,7,8]
changeList2(myList)
print "list outside the function: ", myList

#keyword argument, the compiler depends on the argument literals to determined which argument it represents.
#as shown below, the argument order is not relevant here.
def printInfo(name, age):
    print "the name is %s"% name
    print "the age is %d"% age
    return

printInfo(age = 50, name="john")

#variable-length arguments, specified by asterisk
def printInfo2(arg1, *args):
    print "\noutput is:"
    print arg1
    for var in args:
        print var
    return

printInfo2(10)
printInfo2(10,3,4,5,6)

#Anonymous function, lambda keyword is used to identify them
#take multiple arguments but with one value returned in the form of an expression
sum = lambda arg1, arg2: arg1+arg2

print "value of total: ", sum(10,20)

