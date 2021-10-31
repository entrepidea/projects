"""
You are given a string s, return the number of segments in the string. 
A segment is defined to be a contiguous sequence of non-space characters.
https://leetcode.com/problems/number-of-segments-in-a-string/

Date: 10/30/21

"""
def seg(s):
    l = list(s)
    rlt = []
    temp = []
    for e in l:
        if e !=' ':
            temp.append(e)
        else:
            if len(temp)>0:
                rlt.append(''.join(x for x in temp))
                temp = []

    if len(temp)>0:
        rlt.append(''.join(x for x in temp))

    return rlt

if __name__ == '__main__':
    s = "Hello, my name is John"
    print(f'segments={seg(s)}')
    s = "Hello"
    print(f'segments={seg(s)}')
    s = "love live! mu'sic forever"
    print(f'segments={seg(s)}')
