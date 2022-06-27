"""
find the sqrt of a number
https://leetcode.com/problems/sqrtx/
date: 10/09/21
"""
def sqrt(x):
    j = x
    while (j // 2)*(j//2)>x:
        j = j//2
    j = j*2

    for i in range(1,j+1):
        if i*i == x:
            return i
        else:
            if i*i > x:
                return i-1
            
    return -1

if __name__ == '__main__':
    print(sqrt(4))
    print(sqrt(8))
    print(sqrt(64))
    print(sqrt(65))
    print(sqrt(85))
