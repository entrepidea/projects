#https://www.ynonperek.com/2017/09/21/python-exercises/
#Write a python script for mass renaming music files according to labels. 
'''
Bob Dylan - 01 You're No Good (1962).mp3 -> Bob Dylan/1962 Bob Dylan/01 You're No Good.mp3
Bob Dylan - 02 Talkin' New York (1962).mp3 -> Bob Dylan/1962 Bob Dylan/02 Talkin' New York.mp3
Bob Dylan - 03 In My Time of Dyin' (1962).mp3 -> Bob Dylan/1962 Bob Dylan/03 In My Time of Dyin'.mp3
Bob Dylan - 04 Man of Constant Sorrow (1962).mp3 -> Bob Dylan/1962 Bob Dylan/04 Man of Constant Sorrow.mp3
Bob Dylan - 05 Fixin' to Die (1962).mp3 -> Bob Dylan/1962 Bob Dylan/05 Fixin' to Die.mp3
Bob Dylan - 06 Pretty Peggy-O (1962).mp3 -> Bob Dylan/1962 Bob Dylan/06 Pretty Peggy-O.mp3

'''
#usage: python3 mass_file_rename.py album.txt 

from sys import argv
import re

script, file_name = argv

list_tuples = []
lines = []
with open(file_name) as f:
    #load the file into a list first
    for l in f:
        lines.append(l.strip())

for l in lines:
    temp = re.match(r'(.*)( - )(.*)(\()(\d{4})', l)
    album = temp.group(1)
    track = temp.group(3).strip()
    year = temp.group(5)
    t = (album, track, year)
    str = l
    str+=' -> '
    str+=album
    str+='/'
    str+=year
    str+=' '
    str+=album
    str+='/'
    str+=track
    str+='.mp3'
    print(str)
