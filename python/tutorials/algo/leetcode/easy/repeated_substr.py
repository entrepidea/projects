"""
Given a string s, check if it can be constructed by taking a substring of it and appending multiple copies of the substring together.
https://leetcode.com/problems/repeated-substring-pattern/

Date: 10/31/21

NOTE: the below code is from: https://leetcode.com/problems/repeated-substring-pattern/discuss/1546958/python-straightforward-solution-oror-faster-than-81 , it appears clever. Need to review in depth. TODO
"""
def repeated(s) -> bool:
    s_len = len(s)
    if s_len<=1:
        return False
    for i in range(1,s_len//2+1):
        if s_len%i > 0:
            continue
        substr = s[:i]
        
        if substr*(s_len//i) == s:
            return True

    return False

if __name__ == '__main__':
    print(repeated('abcabcd'))
    print(repeated('aa'))
    print(repeated('abcabcabcabc'))
    print(repeated('abcd'))
    print(repeated('abcdeabcde'))
