"""
Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.

Notice that the solution set must not contain duplicate triplets.

https://leetcode.com/problems/3sum/

05/15/24

"""

def foo(nums:[int])->[]:
    ret = []
    for i in range(len(nums)-2):
        for j  in range(i+1, len(nums)-1):
            for k in range(j+1, len(nums)):
                if nums[i] + nums[j] + nums[k] == 0:
                    ret.append([nums[i],nums[j],nums[k]])
    return ret        

#two pointers
def foo2(nums:[int])->[]:
    ret = []
    nums.sort()
    print(f'sorted: {nums}')

    for i in range(len(nums)-2):
        j,k=i+1,len(nums)-1
        while j!=k:
            if nums[i]+nums[j]+nums[k]>0:
                if nums[k]==nums[k-1]:
                    k-=2
                else:
                    k-=1
            elif nums[i]+nums[j]+nums[k]<0:
                if nums[j]==nums[j+1]:
                    j+=2
                else:
                    j+=1
            else:
                ret.append([nums[i],nums[j],nums[k]])
                break
            
    #print(nums)
    return ret

if __name__ == '__main__':
    nums = [-1,0,1,2,-1,-4]
    print(foo2(nums))
