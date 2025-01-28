"""
Show how to read a file line by line and

write a function to break the line into three parts separated by ','

and call the reusable function

Author: Aaron Yee

Date: 01/19/25
"""
def break_line(s:str):
    name,pop,size = s.split(',')
    return name,pop,size

with open('..\\..\\data\\cities.txt', 'r') as f:
    line = f.readline()
    n,p,s = break_line(line)
    print(f'city[name={n},population={p},size={s}]')
    while line:
        line = f.readline()
        print(f'city[name={n},population={p},size={s}]')