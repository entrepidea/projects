"""
https://leetcode.com/problems/palindrome-number/
date: 10/08/21
"""

#not the most optimal one. 10/08/21
def palindrom(num):
    arr = []
    count = 0
    while num//10 != 0:
        arr.append(num%10)
        num = num//10
        count += 1

    arr.append(num)
    for i in range(0,len(arr)):
        if arr[i] != arr[len(arr)-1-i]:
            return False
    return True


#A little shorter. Same idea. 12/04/21
def palindrom2(num)->bool:
    arr = list(num)
    left = 0
    right = len(arr)-1
    while left < right:
        if arr[left]!= arr[right]:
            return False
        left += 1
        right -= 1
    return True  

if __name__ == '__main__':
    num = 12321
    if palindrom(num):
        print('%d is a palindrom.'%num)
    else:
        print('%d is NOT a palindrom.'%num)

    num = '12321'
    print(f'{num} is palindrome? {palindrom2(num)}')
