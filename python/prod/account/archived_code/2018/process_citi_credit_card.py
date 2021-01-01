import sys
import re

def process(file_name):
    #file_name = argv[0]
    str = open(file_name,'r').read().replace('\n','')
    #this link: https://stackoverflow.com/questions/2136556/in-python-how-do-i-split-a-string-and-keep-the-separators
    #explains how to keep the delimitter after using re.split
    #Split string by the occurrences of pattern. If capturing parentheses are used in pattern, then the text of all groups in the pattern are also returned as part of the resulting list.
    rx = r"(\"[\d]{1,2}/[\d]{1,2}/[\d]{4}\")"
    results = re.split(rx, str)
    list1 = results[1::2]
    list2 = results[2::2]


    #How to concatenate element-wise two lists in Python?
    #https://stackoverflow.com/questions/19560044/how-to-concatenate-element-wise-two-lists-in-python
    new_list = []
    if len(list1) != len(list2):
        print('list length mismatch')
    else:
        for each in range(0, len(list1)):
            new_list.append(list1[each] + list2[each])


    #Split by comma and how to exclude comma from quotes in split … Python
    #https://stackoverflow.com/questions/43067373/split-by-comma-and-how-to-exclude-comma-from-quotes-in-split-python/43067525
    credit_tuples = [(date, desc, debit, credit,cat) for date, desc, debit, credit, cat in (re.split(r',(?=")', str)
        for str in new_list) if credit !="\"\""]

    debit_tuples = [(date, desc, debit, credit, cat) for date, desc, debit, credit, cat in (re.split(r',(?=")', str)
        for str in new_list) if debit != "\"\""]


    #merge into new tuple list: date, desc, amt, cat
    credit_tuples = [("citi", "credit_card", "credit", t[0].replace('"',''),t[1],float(t[3].replace('"','').replace(',','')),t[4].replace('"',''))
                    for t in credit_tuples]

    debit_tuples = [("citi", "credit_card", "debit", t[0].replace('"',''),t[1],0-float(t[2].replace('"','').replace(',','')),t[4].replace('"',''))
                    for t in debit_tuples]

    tuples = credit_tuples + debit_tuples

    #test
    #[print("%d==> [card=%s, type=%s, date=%s, desc=%s, amount=%s, cat=%s]" % (tuples.index(t), t[0], t[1], t[2], t[3], t[4], t[5])) for
    # t in sorted(tuples, key=lambda tup:tup[5])]

    return tuples


