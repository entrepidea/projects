"""
You are given two non-negative integers num1 and num2.

In one operation, if num1 >= num2, you must subtract num2 from num1, otherwise subtract num1 from num2.

For example, if num1 = 5 and num2 = 4, subtract num2 from num1, thus obtaining num1 = 1 and num2 = 4. However, if num1 = 4 and num2 = 5, after one operation, num1 = 4 and num2 = 1.
Return the number of operations required to make either num1 = 0 or num2 = 0.

https://leetcode.com/problems/count-operations-to-obtain-zero/

03/14/24

"""

def foo(num1:int, num2:int)->int:
    bigger = max(num1,num2)
    smaller = min(num1,num2)
    cnt = 0
    while smaller > 0:
        temp = bigger - smaller
        bigger,smaller = max(temp,smaller), min(temp,smaller)
        cnt += 1
    return cnt        

if __name__ == '__main__':
    num1 = 2
    num2 = 3
    print(foo(num1,num2))
    print(foo(10,10))
    print(foo(5,4))
