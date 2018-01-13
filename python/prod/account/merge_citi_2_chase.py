"""

this script convert the file format of citi credit card expense to the format of chase credit card expense.
this is the head of citi file:
 Status	Date	Description	Debit	Credit
this is the head of chase file:
Type	Post Date	Description	Amount	Category

"""

from sys import argv
import csv
import re

"""
Convert citi file into the format like that for chase file
"""
prgm, citi_input, chase_input, merged_file = argv

supplies = {'COACH','PAPYRUS','LUSH BRIDGEWTR','GYMBOREE','MACYS','99 RANCH','LOFT','WAL-MART','SHOPRITE','ANN TAYLOR','TJ MAXX','H MART','UNIQLO','GAP OUTLET','AFM OF PISCATAWAY','THE HOME DEPOT'}
health = {'HUNTERDON'}
dinners = {'CAJUN MARKET', 'SARKU JAPAN SUSHI','STARBUCKS','PANERA BREAD','HUDSON NEWS'}
travel = {'NYCDOT'}

with open(citi_input, "r") as input, open("temp.csv", "w") as output:
    w = csv.writer(output)
    list = []
    for record in csv.reader(input):
        Status,Date,Description,Debit,Credit = tuple(s.strip() for s in record)
        if "AUTOPAY" in Description:
            continue
        if Debit:
            Debit = "-"+Debit
            Status = "Sale"
        else:
            Debit = "-"+Credit
            Status = "Return"
            Credit=""
        newTuple = (Status, Date, Description, Debit, "Unknown category")
        for s in supplies:
            if re.match(s, Description) is not None:
                newTuple = ("Sale", Date, Description, Debit, "Supplies")
                break
        for s in health:
            if re.match(s, Description) is not None:
                newTuple = ("Sale", Date, Description, Debit, "Health Care")
                break
        for s in dinners:
            if re.match(s, Description) is not None:
                newTuple = ("Sale", Date, Description,Debit, "Dining Out")
                break

        for s in travel:
            if re.match(s, Description) is not None:
                newTuple = ("Sale", Date, Description,Debit, "Travel")
                break

        w.writerow(newTuple)

"""
merge the converted file with chase file and create a output file with the name specified as in the last of the script parameters.
"""
l1 = []
with open(chase_input, "r") as input: #read chase file into a list
    for line in input:
        l1.append(line)

with open("temp.csv", "r") as input: #append the converted citi file to the list
    for _ in range(1): #skip the first line
        next(input)
    for line in input:
        if not line.strip():#skip empty lines
            continue

        list = line.split(',')
        Status, Date, Description, Debit, Credit = list
        #print (len(list))
        if not Date or not Description: #none of these should be empty
             continue

        line =Status+","+Date+","+Description+","+Debit+","+Credit
        l1.append(line)

with open(merged_file, "w") as f:
    for ele in l1:
        f.write(ele)




