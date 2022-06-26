"""
check two strings about anagram 
https://leetcode.com/problems/valid-anagram/
date: 10/23/21
"""

def anagram(s,t) -> bool:
    dic = {}
    l = list(s)
    #print(l)
    #print(dic)
    #dic['a'] = 1
    for i in range(len(l)):
        if dic.get(l[i]) is None:
            dic[l[i]] = 1
        else:
            dic[l[i]] += 1

    #print(dic)
    
    l = list(t)
    for i in range(len(l)):
        if dic.get(l[i]) is None:
            return False
        else:
            dic[l[i]] -= 1
    
    for k in dic.keys():
        if dic[k] != 0:
            return False
    
    #print(dic)
    return True

if __name__ == '__main__':
    s = 'anagram'
    t = 'nagaram'
    print(anagram(s,t))

    s = 'rat'
    t = 'car'
    print(anagram(s,t))

