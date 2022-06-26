"""
A perfect number is a positive integer that is equal to the sum of its positive divisors, excluding the number itself. A divisor of an integer x is an integer that can divide x evenly.
https://leetcode.com/problems/perfect-number/

Date: 11/04/21

"""
def perfect(num)->bool:
    fac = num//2
    arr = []
    while fac > 0:
        if num%fac==0:
            arr.append(fac)
        fac -= 1

    return sum(arr) == num


if __name__ == '__main__':
    print(perfect(6))
    print(perfect(28))
    print(perfect(8128))
    print(perfect(2))

