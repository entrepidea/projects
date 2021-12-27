"""
A self-dividing number is a number that is divisible by every digit it contains.

For example, 128 is a self-dividing number because 128 % 1 == 0, 128 % 2 == 0, and 128 % 8 == 0.
A self-dividing number is not allowed to contain the digit zero.

Given two integers left and right, return a list of all the self-dividing numbers in the range [left, right].

https://leetcode.com/problems/self-dividing-numbers/

Date: 12/15/21

"""
def self_divid(num : int) -> bool:
    tmp = num
    while tmp > 0:
        r = tmp%10
        #print(f'num={num}, tmp={tmp},r={r}')
        if r ==0 or num % r != 0:
            return False
        tmp //=10
    return True        


if __name__ == '__main__':
    left = 1
    right = 22
    l = list(range(left,right+1))
    r = list(filter(lambda x : self_divid(x), l))
    print(r)
    left = 47
    right = 85
    l = list(range(left,right+1))
    r = list(filter(lambda x : self_divid(x), l))
    print(r)
    left = 47
    right = 128
    l = list(range(left,right+1))
    r = list(filter(lambda x : self_divid(x), l))
    print(r)
