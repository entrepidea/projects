"""

find two nums in an array, whose sum equals the target number.
https://leetcode.cn/problems/two-sum/
Date: 07/01/22

"""

def sum_of_two(nums, target) -> []:
    for i in range(len(nums)):
        other = target - nums[i]
        for j in range(i+1, len(nums)):
            if other == nums[j]:
                return [nums[i], nums[j]]
    
if __name__ == '__main__':
    nums = [1,2,3,4,5,6]
    target = 10
    print(sum_of_two(nums,target))
