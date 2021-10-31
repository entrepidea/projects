"""
find a perfect sqrt
https://leetcode.com/problems/valid-perfect-square/

Date: 10/29/21

"""
def sq(num) -> bool:
    i = 1
    while i*i<=num:
        if i*i==num:
            return True
        else:
            i +=1

    return False

if __name__ == '__main__':
    print(sq(16))
    print(sq(14))
