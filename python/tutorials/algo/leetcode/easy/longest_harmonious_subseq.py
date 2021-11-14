"""
find out the harmonious subsequence from a given array. 
https://leetcode.com/problems/longest-harmonious-subsequence/

NOTE: got the idea from: https://leetcode.com/problems/longest-harmonious-subsequence/discuss/1576221/python-very-simple-to-understand

Date: 11/14/21


"""
from collections import Counter

def harmonious(arr) -> int:
    dic = Counter(arr)
    rlt = 0
    for i in dic:
        if i+1 in dic:
            rlt = max(rlt, dic[i]+dic[i+1])

    return rlt

if __name__ == '__main__':
    arr = [1,3,2,2,5,2,3,7]
    print(harmonious(arr))
    arr = [1,2,3,4]
    print(harmonious(arr))
    arr = [1,1,1,1]
    print(harmonious(arr))
