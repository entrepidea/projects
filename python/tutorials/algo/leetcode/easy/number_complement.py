"""
The complement of an integer is the integer you get when you flip all the 0's to 1's and all the 1's to 0's in its binary representation.

For example, The integer 5 is "101" in binary and its complement is "010" which is the integer 2.
Given an integer num, return its complement.

https://leetcode.com/problems/number-complement/

Date: 11/01/21

"""
def comp(num):
    rlt = []
    while num != 0:
        #print(f'num={num}, num//2={num//2}, num%2={num%2}')
        rlt.append(num%2)
        num //=2
    
    count = 0
    for i in range(len(rlt)): # remove the leading 0s
        while rlt[i] == 0:
            i += 1
            count += 1

    rlt = rlt[count:]

    num2 = 0
    for i in range(len(rlt)):
        temp = 0 if rlt[i] == 1 else 1
        factor = 1
        for j in range(len(rlt)-1-i):
            factor *= 2
        num2 += temp*factor                    

    return num2


if __name__ == '__main__':
    print(comp(5))
    print(comp(4))
    print(comp(2))

