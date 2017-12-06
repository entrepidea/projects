#read citi data from a downloaded csv file.
#citi_expense_2017.csv

#read the file line by line, create a list of tuples

import csv
from decimal import Decimal
from sys import argv
import re
prgm, input=argv

output = input.split('.csv')[0]+"_updated.csv"

supplies = {'PAPYRUS','LUSH BRIDGEWTR','GYMBOREE','MACYS','99 RANCH','LOFT','WAL-MART','SHOPRITE','ANN TAYLOR','TJ MAXX','H MART','UNIQLO','GAP OUTLET','AFM OF PISCATAWAY','THE HOME DEPOT'}
health = {'HUNTERDON'}
dinners = {'SARKU JAPAN SUSHI','STARBUCKS','PANERA BREAD','HUDSON NEWS'}


with open(input, "r") as input, open(output, "w") as output:
    w = csv.writer(output)
    for record in csv.reader(input):
        Status,Date,Description,Debit,Credit = tuple(s.strip() for s in record)
        if Description and Debit:
            d= Decimal(0.0)-Decimal(Debit)
            newTuple = ("Sale",Date,Description,str(d),"Unknown category")
            for s in supplies:
                if re.match(s,Description) is not None:
                    newTuple = ("Sale",Date,Description,str(d),"Supplies")    
                    break       
            for s in health:
                if re.match(s,Description) is not None:
                    newTuple = ("Sale",Date,Description,str(d),"Health Care")    
                    break       
            for s in dinners:
                if re.match(s,Description) is not None:
                    newTuple = ("Sale",Date,Description,str(d),"Dining Out")    
                    break       

            w.writerow(newTuple)