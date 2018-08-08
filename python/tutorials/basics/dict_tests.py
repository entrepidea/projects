#dictionary uses {} for enclose
#refer to: https://www.tutorialspoint.com/python/python_dictionary.htm


dict = {}
dict['one'] = 'this is one'
dict[2] = 'this is two'

tinyDict = {'name':'john', 'code':4536, 'dept':'sale'}

print (dict['one'])

print (dict[2])

print tinyDict

#delete dictionary elements
del tinyDict['name']
print tinyDict

#when dup keys found, the last one prevails
#key is NOT mutable
dict = {'Name': 'Zara', 'Age': 7, 'Name': 'Manni'}
print "dict['Name']: ", dict['Name']

#built-in functions
dict1 = {'Name': 'Zara', 'Age': 7};
dict2 = {'Name': 'Mahnaz', 'Age': 27};
dict3 = {'Name': 'Abid', 'Age': 27};
dict4 = {'Name': 'Zara', 'Age': 7};
print "Return Value : %d" %  cmp (dict1, dict2)
print "Return Value : %d" %  cmp (dict2, dict3)
print "Return Value : %d" %  cmp (dict1, dict4)

print "String presentation of dic: %s" % str(dict4)

print "type of a dic: %s" % type(dict4)

#dict's functions
dict5  = dict4.copy()
print "shallow copy of a dict: ", dict5

print "true or false - dict has key 'Name' : ", dict4.has_key('Name')

print "dict items (return a list of tuples): ", dict4.items()

print "dict keys: ", dict4.keys()

print "dict values: ", dict4.values()
