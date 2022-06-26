"""

convert an array of digits into an integer and plus one and convert the result back to an array of digits
https://leetcode.com/problems/plus-one/
date: 10/09/21

"""
def plus(digits):
    sum = 0
    for i in range(0,len(digits)):
        temp = digits[i]
        for j in range(1,len(digits)-i):
            temp *= 10
        sum += temp
    sum +=1
    stack = []
    rlt = []
    while sum > 0:
        remainder = sum%10
        stack.append(remainder)
        sum = sum//10

    for i in range(0,len(stack)):
        rlt.append(stack.pop())
    return rlt

if __name__ == '__main__':
    digits = [1,2,3]
    print(plus(digits))
    digits = [4,3,2,1]
    print(plus(digits))
    digits = [0]
    print(plus(digits))
    digits = [9]
    print(plus(digits))
