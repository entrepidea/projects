import re
from decimal import Decimal
from sys import argv
#this script take text file as the income data and find the money values from each line and add them up.

def moneyVale(moneyStr):
    value = Decimal(re.sub(r'[^\d.]', '', moneyStr))
    return value

prgm, incomeFile = argv
with open(incomeFile) as f:
    sum=Decimal(0.00)
    for line in f:
        #print(repr(line.strip()))
        list = re.findall('(\$\d+(\,*\.*\d+)+)',line)
        if list: 
            money=list[0][0]
            val = moneyVale(money)
            print(val)        
            sum = sum + val

    print ("sum is: ",str(sum))        
