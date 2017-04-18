#https://www.tutorialspoint.com/python/python_modules.htm

#see the comments in support.py
import support
support.func()

#Glboal and local variables - use global keyword to differential variables in different scope but have same names
Money = 2000
def addMoney():
    global Money
    Money = Money +1

    print "print locals:"
    print locals()
    print "print globals:"
    print globals()
    return

print "variable Money:"
print Money
addMoney()
print "variable Money after the function:"
print Money



#The dir() built-in function returns a sorted list of strings containing the names defined by a module.
import math
content = dir(math)
print content

#this show how to use package
print "\nthis shows how to use packages in python:"
import phone
phone.isdn()
phone.g3()
phone.pots()
