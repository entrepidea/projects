"""
A string s can be partitioned into groups of size k using the following procedure:

The first group consists of the first k characters of the string, the second group consists of the next k characters of the string, and so on. Each character can be a part of exactly one group.
For the last group, if the string does not have k characters remaining, a character fill is used to complete the group.
Note that the partition is done so that after removing the fill character from the last group (if it exists) and concatenating all the groups in order, the resultant string should be s.

Given the string s, the size of each group k and the character fill, return a string array denoting the composition of every group s has been divided into, using the above procedure.

https://leetcode.com/problems/divide-a-string-into-groups-of-size-k/description/

02/24/24

"""

def divide(s:str,k:int,fill:chr) -> list[str]:
    frag = len(s)//k
    reminder = len(s)%k
    re = []
    for i in range(0,frag):
        re.append(s[i*k:(i+1)*k])
    last_part = s[frag*k:]

    if reminder != 0:
        for i in range(reminder,k):
            last_part += fill
        re.append(last_part)
    return re


if __name__ == '__main__':
    s = 'abcdefghi'
    k = 3
    fill = 'x'
    print(divide(s,k,fill))
    k = 4
    print(divide(s,k,fill))

