"""
You are given an array of integers nums. You are also given an integer original which is the first number that needs to be searched for in nums.

You then do the following steps:

If original is found in nums, multiply it by two (i.e., set original = 2 * original).
Otherwise, stop the process.
Repeat this process with the new number as long as you keep finding the number.
Return the final value of original.

https://leetcode.com/problems/keep-multiplying-found-values-by-two/description/

02/25/24

"""
def h(nums:[int],k:int)->bool:
    if k in nums:
        return True
    return False

def foo(nums:[int], k:int)->int:
    if not h(nums,k):
        return k
    return foo(nums,k*2)

#another one
#https://leetcode.com/problems/keep-multiplying-found-values-by-two/solutions/2763487/python-while-loop-easiest/
def foo2(nums:[int], k:int)->int:
    a = True
    while a:
        if k in nums:
            k *= 2
        else:
            a = False
    return k            

if __name__ == '__main__':
    nums = [5,3,6,1,12]
    k = 3
    print(foo2(nums,k))
    nums = [2,7,9]
    k = 4
    print(foo2(nums,k))
