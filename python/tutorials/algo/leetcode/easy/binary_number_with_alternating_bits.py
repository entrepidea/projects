"""
Given a positive integer, check whether it has alternating bits: namely, if two adjacent bits will always have different values.
https://leetcode.com/problems/binary-number-with-alternating-bits/

Date: 12/09/21

"""
def alter(num : int)->bool:
    bench = -1
    while num//2 > 0:
        rem = num%2
        #print(rem, end='')
        if bench == -1 or bench != rem:
            bench = rem
        else:
            return False

        num //=2
    
    #print(num, end='')
    #print()

    if num == bench:
        return False
    return True

if __name__ == '__main__':
    print(alter(5))
    print(alter(7))
    print(alter(10))
    print(alter(11))
