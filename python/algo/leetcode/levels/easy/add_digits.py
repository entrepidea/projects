"""
keep adding up the digits of a number, loop until the result is a single digit number.
https://leetcode.com/problems/add-digits/

Date: 10/26/21

"""
def add(num):
    if num//10 == 0:
        return num%10

    tmp = 0
    while num != 0:
        tmp += num%10
        num //=10
    
    tmp += num%10
    #print(tmp)

    return add(tmp)


if __name__ == '__main__':
    num = 38
    print(add(num))
    num = 0
    print(add(num))
    num = 67
    print(add(num))
