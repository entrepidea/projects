from string import ascii_uppercase
"""
Given an integer columnNumber, return its corresponding column title as it appears in an Excel sheet.
https://leetcode.com/problems/excel-sheet-column-title/

date: 10/12/21

"""

def convert(num):
    pos = 1
    dic = {0:'Z'}
    for c in ascii_uppercase:
        if c != 'Z':
            dic[pos] = c
            pos += 1
    #print(dic)

    rlt = []
    while num >= 27: #this part is tricky in that the matrix actually has 27 rows: "A, AA,BA...ZA"
        remainder = num%26
        #print(f'number={num}, remainder={remainder}')
        rlt.insert(0,dic[remainder])
        num //=26

    #print(f'num={num}, num%26={num%26}')
    rlt.insert(0,dic[num%26])        

    return ''.join(s for s in rlt)

if __name__ == '__main__':
    print(convert(1))
    print(convert(28))
    print(convert(701))
    print(convert(2147483647))
