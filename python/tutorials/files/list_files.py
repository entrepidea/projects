#https://www.ynonperek.com/2017/09/21/python-exercises/
#OS integration#1
#usage: python3 list_files.py . pl c
import re
import os, glob
from sys import argv 

def ext_dict():
    #read config file
    #eg
    #pl:pl,pm:files with all supported perl formats.

    dict = {}
    with open('extension.txt') as f:
        for line in f:
            temp = re.match(r'(.*)(:)(.*)(:)(.*)',line)
            t = (temp.group(3), temp.group(5))
            dict[temp.group(1)] = t 
    return dict        

dir = argv[1]
exts = argv[2:]

dict = ext_dict()

os.chdir(dir)

for ext in exts:
    #if ext is found in dict, find all supported file formats and list them out.
    if ext in dict.keys():
        spt_fmts = dict[ext][0].split(',')
        cmt = dict[ext][1]
        print(cmt)
        for fmt in spt_fmts:
            for file in glob.glob("*."+fmt):
                print('\t'+file)

'''




'''


