"""
Desc: find out if two strings are isomorphic
Source: https://leetcode.com/problems/isomorphic-strings/
Date: 11/06/19
"""

def is_isomorphic(s,t):
    assert len(s) == len(t)
    dic = {}
    for i in range(0, len(s)):
        if not s[i] in dic:
            dic[s[i]]=t[i]
        else:
            if dic[s[i]] != t[i]:
                return False

    return True

def test_isomorphic():
    assert is_isomorphic("egg","add")
    assert is_isomorphic("paper", "title")
    assert not is_isomorphic("foo", "bar")