"""

find the index of the first unique char in a string.
https://leetcode.com/problems/first-unique-character-in-a-string/

Date: 10/29/21

"""
def uniq(s):
    l = list(s)
    at = [0]*26

    for e in l:
        at[ord(e)-ord('a')] += 1

    uniqs = [chr(ord('a')+idx) for idx in range(len(at)) if at[idx] == 1]

    for idx in range(len(l)):
        if l[idx] in uniqs:
            return idx

    return -1

if __name__ == '__main__':
    print(uniq('leetcode'))
    print(uniq('loveleetcode'))
    print(uniq('aabb'))
