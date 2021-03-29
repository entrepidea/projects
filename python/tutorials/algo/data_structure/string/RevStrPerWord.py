'''

A BAML interview question. I wasn't the interviewee.

Question C1: given a sentence without punctuation, print the words in reverse. 
For eg, "I like Teresa Teng songs most" would become "most songs Teng Teresa like I"

Please don't use any split() function from some library.

it says no split, i used it anyway
'''
def reverseStrPerWord(str):
    l = str.split()
    l.reverse()
    return l

print(reverseStrPerWord("I like Teresa Teng songs most"))
