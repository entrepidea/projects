"""
Given two integer arrays nums1 and nums2, return an array of their intersection. Each element in the result must appear as many times as it shows in both arrays and you may return the result in any order.

https://leetcode.com/problems/intersection-of-two-arrays-ii/

Date: 10/29/21

"""
def inter(num1,num2):
    rlt = []
    dic = {}
    for e in num1:
        if e not in dic:
            dic[e] = 1
        else:
            dic[e] += 1

    for e in num2:
        if e in dic and dic[e]!=0:
            rlt.append(e)
            dic[e] -= 1

    return rlt

if __name__ == '__main__':
    num1 = [1,2,2,1]
    num2 = [2,2]
    rlt = inter(num1,num2)
    print(rlt)

    num1 = [4,9,5]
    num2 = [9,4,9,8,4]
    rlt = inter(num1,num2)
    print(rlt)

    num1 = [4,4,9,9,9,5]
    num2 = [9,4,9,8,4,5,5]
    rlt = inter(num1,num2)
    print(rlt)
