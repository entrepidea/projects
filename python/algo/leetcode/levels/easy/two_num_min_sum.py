"""
You are given a positive integer num consisting of exactly four digits. Split num into two new integers new1 and new2 by using the digits found in num. Leading zeros are allowed in new1 and new2, and all the digits found in num must be used.

For example, given num = 2932, you have the following digits: two 2's, one 9 and one 3. Some of the possible pairs [new1, new2] are [22, 93], [23, 92], [223, 9] and [2, 329].
Return the minimum possible sum of new1 and new2.

https://leetcode.com/problems/minimum-sum-of-four-digit-number-after-splitting-digits/description/

02/24/24

"""

def foo(num:int) -> int:
    arr = []
    while num//10 > 0:
        arr.append(num%10)
        num = num//10
    arr.append(num%10)
    arr = sorted(arr)
    return arr[0]*10+arr[2] + arr[1]*10+arr[3]

if __name__ == '__main__':
    print(f'input=2932, result={foo(2932)}')
    print(f'input=4009, result={foo(4009)}')
