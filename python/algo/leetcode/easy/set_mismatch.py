
"""
You have a set of integers s, which originally contains all the numbers from 1 to n. Unfortunately, due to some error, one of the numbers in s got duplicated to another number in the set, which results in repetition of one number and loss of another number.
https://leetcode.com/problems/set-mismatch/

NOTE: borrow the idea from here: https://leetcode.com/problems/set-mismatch/discuss/1588039/c%2B%2B-one-go-answer
NOTE: still have to figure out the logic. TODO

Date: 11/25/21

"""
def mis_match(nums : [int])->(int, int): 
    for i in range(len(nums)):
        while nums[i] != nums[nums[i]-1]:
            nums[i],nums[nums[i]-1] = nums[nums[i]-1], nums[i]

    for i in range(len(nums)):
        if nums[i] != i+1:
            return nums[i], i+1

    return -1,-1        

if __name__ == '__main__':
    nums = [1,2,2,4]
    print(mis_match(nums))
