"""
give the area of a rectange, find out l and w (l>=w) and the distance b/w l and w is minumum.
https://leetcode.com/problems/construct-the-rectangle/

Date: 11/02/21

"""
import math
def s(num)->[]:
    b = int(math.sqrt(num))
    if b*b == num:
        return [b,b]
    l = b
    w = b
    while l <= num :
        if l * w == num:
            return [l,w]
        if num % l == 0:
            return [l,num//l]
        l += 1

    return None

if __name__ == '__main__':
    print(s(8))
    print(s(25))
    print(s(26))
    print(s(37))
    print(s(122122))
