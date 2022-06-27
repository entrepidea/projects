"""

find out the length of the last word in a string
https://leetcode.com/problems/length-of-last-word/
date: 10/08/21

"""

def length(s):
    l = list(s)
    for i in range(len(l)-1,0,-1):
        if l[i] == ' ':
            continue
        start = i
        p = start
        while p>=0 and l[p] != ' ':
            p -= 1
        return start - p            
        
if __name__ == '__main__':
    s = 'Hello world'
    print(length(s))
    s = '   fly me   to   the moon  '
    print(length(s))
    s = 'luffy is still joyboy'
    print(length(s))
