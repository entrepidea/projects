"""
add two strings which representing two numbers. No built-in convert functions are to be used.
https://leetcode.com/problems/add-strings/

Date: 10/30/21

"""

def s_2_i(s)->int:
    l = list(s)
    #print(f'l={l}')
    n= 0
    for i in range(len(l)):
        factor = 1
        x = ord(l[i]) - ord('0')
        for j in range(len(l)-i-1):
            factor *= 10
        n += x*factor
        #print(f'n={n}')
    return n

def i_2_s(i) -> str:
    rlt = []
    while i != 0:
        rlt.insert(0,i%10)
        i //=10
    #rlt.insert(0,i%10)
    #print(f'rlt = {rlt}')
    return ''.join(str(e) for e in rlt)

def add(s1,s2) ->str:
    n1 = s_2_i(s1)
    n2 = s_2_i(s2)
    print(f'n1={n1},n2={n2}')
    n = n1 + n2
    return i_2_s(n)

if __name__ == '__main__':
    print(add('11','123'))
    print(add('101','123'))
    print(add('300','700'))
    print(add('400','756'))

