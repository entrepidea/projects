"""
Given a string s, return the string after replacing every uppercase letter with the same lowercase letter.
https://leetcode.com/problems/to-lower-case/

Date: 12/15/21

"""


def lower(c: chr) -> chr:
    if ord('A') <= ord(c) <= ord('Z'):
        return chr(ord('a') + (ord(c) - ord('A')))
    return c


def to_lower(s: str) -> str:
    l = list(s)
    r = list(map(lambda x: lower(x), l))
    return ''.join(e for e in r)


if __name__ == '__main__':
    s = 'HAPPY'
    print(to_lower(s))
