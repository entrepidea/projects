"""
Given a string s, find the length of the longest substring without repeating characters.
https://leetcode.com/problems/longest-substring-without-repeating-characters/description/
05/15/24
"""

def foo(s:str)->str:
    l = 0
    d = [0]*26
    a = list(s)
    ret=[]
    for i in range(len(a)):
        j = i
        while j<len(a) and d[ord(a[j])-97]!=1:
            d[ord(a[j])-97]=1
            j+=1

        d=[0]*26 #reset
        if (j-i)>l:
            l=j-i
            ret = str(a[i:j])

    return ret

if __name__ == '__main__':
    s = "abcabcbb"
    print(foo(s))
    s = "bbbbb"
    print(foo(s))
    s = "pwwkew"
    print(foo(s))
