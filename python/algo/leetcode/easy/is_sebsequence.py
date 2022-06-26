"""
Given two strings s and t, return true if s is a subsequence of t, or false otherwise.
https://leetcode.com/problems/is-subsequence/

Date: 10/29/21

"""
def is_seq(s,t):
    ls = list(s)
    lt = list(t)
    idx = []
    for e in ls:
        if e in lt:
            idx.append(lt.index(e))
            if len(idx)>1 and idx[len(idx)-1]<idx[len(idx)-2]:
                return False
        else:
            return False
    return True        


if __name__ == '__main__':
    s = 'abc'
    t = 'ahbgdc'
    print(is_seq(s,t))
    s = 'axc'
    t = 'ahbgdc'
    print(is_seq(s,t))
    s = 'acb'
    t = 'ahbgdc'
    print(is_seq(s,t))
