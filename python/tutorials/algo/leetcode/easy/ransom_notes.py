"""
Given two stings ransomNote and magazine, return true if ransomNote can be constructed from magazine and false otherwise.
Each letter in magazine can only be used once in ransomNote.
https://leetcode.com/problems/ransom-note/

Date: 10/29/21

"""

def ransom(note, mag):
    dic = {}
    l = list(note)
    for e in l:
        if e not in dic:
            dic[e] = 1
        else:
            dic[e] += 1

    l = list(mag)
    for e in l:
        if e in dic:
            dic[e] -= 1

    for key in dic.keys():
        if dic[key] != 0:
            return False

    return True        


if __name__ == '__main__':
    ransom_note = 'a'
    magzine = 'b'
    print(ransom(ransom_note,magzine))
    ransom_note = 'aa'
    magzine = 'ab'
    print(ransom(ransom_note,magzine))
    ransom_note = 'aa'
    magzine = 'aab'
    print(ransom(ransom_note,magzine))

    
