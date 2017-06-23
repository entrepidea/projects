import re

#https://www.tutorialspoint.com/python/python_reg_expressions.htm

line = "Cats are smarter than dogs"

#match function
matchObj = re.match( r'(.*) are (.*?) .*', line, re.M|re.I)

if matchObj:
   print "matchObj.group() : ", matchObj.group()
   print "matchObj.group(1) : ", matchObj.group(1)
   print "matchObj.group(2) : ", matchObj.group(2)
else:
   print "No match!!"

print "\n\ntest regexp search function:\n"
#search function
searchObj = re.search(r'(.*) are (.*?) .*', line, re.M|re.I)
if searchObj:
    print "searchObj.group() : ", searchObj.group()
    print "searchObj.group(1) : ", searchObj.group(1)
    print "searchObj.group(2) : ", searchObj.group(2)
else:
    print "Nothing found!!"


#test sub function
print "\n\nTest sub function:\n"
phone = "2004-959-559 # This is Phone Number"
num = re.sub(r'#.*$',"", phone)
print num
num = re.sub(r'\D',"", phone)
print num
