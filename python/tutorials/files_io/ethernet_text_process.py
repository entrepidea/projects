#https://www.ynonperek.com/2017/09/21/python-exercises/
#scroll to "Text Processing"#1
#usage: python3 ethernet_text_process.py ethernet.txt
#ethernet_output.csv will be created.

from sys import argv
import re

def printOutput(dict, list):
    headers=['interface','inet','status']
    str=''
    for h in headers:
        if h in dict.keys():
            str=str+dict[h]
        str=str+',' 
    temp = str[:-1]     
    print(temp)
    list.append(temp)  
    
        

   

script, file_name = argv

keywords = ["\tinet","\tstatus"]

with open(file_name) as f:
    currKey = ''
    dict = {}
    list=[]
    for line in f:
        l = re.findall(r'^\w+:\s+', line)
        if len(l)>0:
            if bool(dict):
                printOutput(dict,list)
            dict={}    
            dict['interface']=l[0][:-2]
        else:
            if line.startswith('\tinet6'):
                continue
            for keyword in keywords:
                if line.startswith(keyword):
                    dict[keyword.strip()] = line.strip().split()[1]

    printOutput(dict,list)

    with open('ethernet_output.csv','w') as file:
        file.write('interface,inet,status\n')
        for e in list:
            file.write(e)
            file.write('\n')
