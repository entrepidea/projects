"""
Given a string s, reverse the order of characters in each word within a sentence while still preserving whitespace and initial word order.
https://leetcode.com/problems/reverse-words-in-a-string-iii/

Date: 11/05/21

"""

def revr_word(word:str)->str:
    l = list(word)
    #print(l)
    #print(len(l)//2)
    for i in range(len(l)//2):
        l[i], l[len(l)-i-1] = l[len(l)-i-1], l[i]
    
    #print(l)

    return ''.join(str(e) for e in l)

def reverse(s:str)->str:
    l = s.split()
    for i in range(len(l)):
        l[i] = revr_word(l[i])
    return ' '.join(str(e) for e in l)        

if __name__ == '__main__':
    s = "Let's take LeetCode contest"
    print(reverse(s))
    s = "God Ding"
    print(reverse(s))


        



