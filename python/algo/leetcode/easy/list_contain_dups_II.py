"""
Given an integer array nums and an integer k, return true if there are two distinct indices i and j in the array such that nums[i] == nums[j] and abs(i - j) <= k.
https://leetcode.com/problems/contains-duplicate-ii/

date: 10/14/21

"""
def find_dup(arr, k):
    dic = {}
    for i in range(len(arr)):
        if arr[i] not in dic.keys():
            dic[arr[i]] = i
        else:
            dist = abs(i-dic[arr[i]])
            if dist <= k:
                return True
            else:
                dic[arr[i]] = i
    return False

if __name__ == '__main__':
    nums = [1,2,3,1]
    k = 3
    print(find_dup(nums,k))
    nums = [1,0,1,1]
    k = 1
    print(find_dup(nums,k))
    nums = [1,2,3,1,2,3]
    k = 2
    print(find_dup(nums,k))
