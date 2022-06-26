"""
Given an array nums of n integers where nums[i] is in the range [1, n], return an array of all the integers in the range [1, n] that do not appear in nums.

Date: 10/30/21

"""
def disappear(nums):
    leng = len(nums)
    new_arr = [0]*leng
    for e in nums:
        new_arr[e-1] += 1
    
    rlt = []
    for i in range(leng):
        if new_arr[i] == 0:
            rlt.append(i+1)
                
    return rlt

if __name__ == '__main__':
    nums = [4,3,2,7,8,2,3,1]
    print(disappear(nums))
    nums = [1,1]
    print(disappear(nums))
