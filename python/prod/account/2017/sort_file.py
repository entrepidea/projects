"""
sort the file on category, date
"""
from sys import argv
import csv

prgm, input_file, output_file = argv
with open(input_file,newline='') as csvfile:
    #MULTIPLE COLUMN sorting
    #https://stackoverflow.com/questions/2100353/sort-csv-by-column
    spamreader = csv.DictReader(csvfile, delimiter=",")
    sortedlist = sorted(spamreader, key=lambda row:(row['Category'],row['Post Date']), reverse=False)

with open(output_file,'w') as csvfile:
    for idx, val in enumerate(sortedlist):
        print(idx, val)
        line = val['Type']+','+val['Post Date']+','+val['Description']+','+val['Amount']+','+val['Category']+'\n'
        csvfile.writelines(line)