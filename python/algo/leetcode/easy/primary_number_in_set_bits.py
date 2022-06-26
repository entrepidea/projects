"""
Given two integers left and right, return the count of numbers in the inclusive range [left, right] having a prime number of set bits in their binary representation.

Recall that the number of set bits an integer has is the number of 1's present when written in binary.

For example, 21 written in binary is 10101, which has 3 set bits.

https://leetcode.com/problems/prime-number-of-set-bits-in-binary-representation/

Date: 12/24/21

"""
def is_prime(num : int) -> bool:
    if num == 1:
        return False
    for i in range(2,num):
        if num%i == 0:
            print(f'{num} is NOT a prime')        
            return False
    print(f'{num} is a prime')        
    return True        

def set_bits(num : int) -> int:
    tmp = num
    ret = 0
    while num //2 != 0:
        ret += num%2
        #print(f'num={num%2},ret={ret}')
        num //= 2
    ret += num%2

    print(f'num={tmp}, set_bis={int(ret)}')

    return int(ret)

if __name__ == '__main__':
    low_num = 10
    high_num = 15

    count = 0
    for n in range(low_num,high_num+1):
        b = set_bits(n)
        if is_prime(b):
            count += 1

    print(f'{count}')
