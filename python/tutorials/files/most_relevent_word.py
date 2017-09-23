#https://www.ynonperek.com/2017/09/21/python-exercises/
#Write a program that takes a file name as command line argument,
#count how many times each word appears in the file and prints the word that appears the most
#(and its relevant count)
from sys import argv

if len(argv) != 2 :
    print('usage: program file_name')
else:
    all_words = []
    script, file_name = argv
    with open(file_name) as f:
        for line in f:
            words = line.split()
            all_words.extend(words)

    dict = {}
    for w in all_words:
        if w in dict.keys() :
            dict[w] = int(dict[w]) + 1
        else:
            dict[w] = 1    

    max = 0
    most_rel = ''
    for k,v in dict.items():
        if v > max :
            max = v
            most_rel = k

    print (most_rel+" is the most relevant word, and is used "+str(max)+" times")                    
