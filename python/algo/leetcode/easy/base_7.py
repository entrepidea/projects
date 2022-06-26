"""
Given an integer num, return a string of its base 7 representation.
https://leetcode.com/problems/base-7/

Date: 11/04/21

"""
def base7(num) -> str:
    arr = []
    while num != 0:
        arr.insert(0,num%7)
        num //= 7

    return ''.join(str(e) for e in arr)        

if __name__ == '__main__':
    print(base7(100))

