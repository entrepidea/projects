"""
find out the max product of three numbers in an array.
https://leetcode.com/problems/maximum-product-of-three-numbers/

NOTE: one pass. Idea: https://leetcode.com/problems/maximum-product-of-three-numbers/discuss/1574142/python3-one-pass-no-sorting

Date: 11/21/21

"""
def max_prod(nums) -> int:
    max1 = max2 = max3 = float('-inf')
    min1 = min2 = float('inf')
    for num in nums:
        if num >=max1:
            max3 = max2
            max2 = max1
            max1 = num
        elif num>=max2:
            max3 = max2
            max2 = num
        elif num>=max3:
            max3 = num

        if num<=min1:
            min2 = min1
            min1 = num
        elif num<=min2:
            min2 = num
        
    return max(max1*max2*max3, max1*min1*min2)

if __name__ == '__main__':
    nums = [1,2,3]
    print(max_prod(nums))
    nums = [1,2,3,4]
    print(max_prod(nums))
    nums = [-1,-2,-3,-4,0]
    print(max_prod(nums))
