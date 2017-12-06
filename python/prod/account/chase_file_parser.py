import re
from decimal import Decimal
from sys import argv
#download the expense csv file from chase.com and do some parsing of it.
#read the file
prgm, inputFile = argv

with open(inputFile) as f:
    rtnTuples = []
    saleTuples = []
    for line in f:
        eles = ()
        if line.startswith('Return'):
            ele = line.split(',')
            rtnTuples.append(ele)
        if line.startswith('Sale'):
            ele = line.split(',')
            saleTuples.append(ele)    

#before removing, how many of the sales?
print('before removing: ', len(saleTuples))

#pick up each from retTuples to check against saleTuples to find a match
for e in rtnTuples:
    vendor = e[2]
    price = e[3]    
    price = '-'+price
    l = filter(lambda x: x[3]==price and x[2]==vendor, saleTuples)
    
    #remove filtered list from saleTuples
    for i in l:
        print(i)        
        saleTuples.remove(i)

print('after removing: ', len(saleTuples))

types = set()
for i in saleTuples:
    types.add(i[4])

outputFile = inputFile.split('.csv')[0]+"_updated.csv"
sum = Decimal(0.00)
with open(outputFile,'w') as file:
    for t in types:     
        l = filter(lambda x: x[4]==t,saleTuples)
        #print('\nthe length of '+t+' is ', len(l)+'\n')
        
        for i in l:
            #print(i[3])
            file.write(i[4]+','+i[1]+','+i[2]+','+i[3]+'\n')
            sum = sum + Decimal(i[3])

    print ("the sum is "+str(sum))            
    file.write(''+','+''+','+''+','+str(sum)+'\n')