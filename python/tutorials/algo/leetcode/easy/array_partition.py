"""
Given an integer array nums of 2n integers, group these integers into n pairs (a1, b1), (a2, b2), ..., (an, bn) such that the sum of min(ai, bi) for all i is maximized. Return the maximized sum.
https://leetcode.com/problems/array-partition-i/

NOTE: look at this solution, easy and make sense: https://leetcode.com/problems/array-partition-i/discuss/1563380/Python-simple-solution

Date: 11/07/21

"""
def partition(arr)->int:
    arr.sort()
    print(arr[::2])
    return sum(arr[::2])

if __name__ == '__main__':
    arr = [1,4,3,2]
    print(partition(arr))
    arr = [6,2,6,5,1,2]
    print(partition(arr))
