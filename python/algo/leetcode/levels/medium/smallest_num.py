"""
You are given an integer num. Rearrange the digits of num such that its value is minimized and it does not contain any leading zeros.

Return the rearranged number with minimal value.

Note that the sign of the number does not change after rearranging the digits.

https://leetcode.com/problems/smallest-value-of-the-rearranged-number/

03/08/24

"""

def foo(num:int)->int:
    positive = True if num > 0 else False
    if not positive:
        num = 0 - num
    arr = []
    while num//10>0:
        arr.append(num%10)
        num //= 10
    arr.append(num%10)
    if not positive:
        arr = sorted(arr)[::-1]
    else:
        arr = sorted(arr)
    i = 0
    while arr[i] == 0:
        i+=1
    if i != 0:
        arr[0],arr[i] = arr[i], arr[0]
    
    ret = int("".join(str(d) for d in arr))
    if not positive:
        ret = 0 - ret
    return ret

if __name__ == '__main__':
    num = -7605
    print(f'original number: {num}, smallest number after re-arrange: {foo(num)}')
    num = 310
    print(f'original number: {num}, smallest number after re-arrange: {foo(num)}')
    num = 50001
    print(f'original number: {num}, smallest number after re-arrange: {foo(num)}')
    num = 50000
    print(f'original number: {num}, smallest number after re-arrange: {foo(num)}')
    num = -50001
    print(f'original number: {num}, smallest number after re-arrange: {foo(num)}')
    num = -50000
    print(f'original number: {num}, smallest number after re-arrange: {foo(num)}')
