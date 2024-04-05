"""
You are given a 0-indexed array nums consisting of n positive integers.

The array nums is called alternating if:

nums[i - 2] == nums[i], where 2 <= i <= n - 1.
nums[i - 1] != nums[i], where 1 <= i <= n - 1.
In one operation, you can choose an index i and change nums[i] into any positive integer.

Return the minimum number of operations required to make the array alternating.

https://leetcode.com/problems/minimum-operations-to-make-the-array-alternating/description/

04/21/24

"""


from typing import List
from collections import defaultdict


#https://leetcode.com/problems/minimum-operations-to-make-the-array-alternating/solutions/1767132/python-3-hashmap-solution-o-n-no-sort-needed/
def min_op(nums: List[int]) -> int:
    n = len(nums)
    odd,even = defaultdict(int), defaultdict(int)
    for i in range(n):
        if i % 2 == 0:
            even[nums[i]] += 1
        else:
            odd[nums[i]] += 1
    
    topEven, secEven = (None,0), (None,0)
    for num in even:
        if even[num] > topEven[1]:
            topEven,secEven = (num, even[num]), topEven
        elif even[num] > secEven[1]:
            secEven = (num, even[num])
                
    topOdd, secOdd = (None,0), (None,0)
    for num in odd:
        if odd[num] > topOdd[1]:
            topOdd, secOdd = (num, odd[num]), topOdd
        elif odd[num] > secOdd[1]:
            secOdd = (num, odd[num])

    if topOdd[0] != topEven[0]:
        return n - topOdd[1] - topEven[1]
    else:
        return n - max(secOdd[1]+topEven[1],secEven[1]+topOdd[1])

if __name__ == '__main__':
    nums = [3,1,3,2,4,3]
    print(f'number of operations to turn array {nums} into an alternate one is {min_op(nums)}')
