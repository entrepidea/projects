"""
distribute candies, choose as much a variety of candies as you can but still refrain yourself from eating half of the total.
https://leetcode.com/problems/distribute-candies/

Date: 11/14/21

"""
def candies(arr)-> int:
    max_len = len(arr)/2
    tmp = [arr[0]]
    count = 1
    for i in range(1,len(arr)):
        if arr[i] not in tmp:
            tmp.append(arr[i])

    #below is a much easier way of converting an array to a set in Python
    #tmp1 = set(arr)
    #print(tmp1)

    return min(len(tmp), max_len)

if __name__ == '__main__':
    types = [1,1,2,2,3,3]
    print(candies(types))
    types = [1,1,2,3]
    print(candies(types))
    types = [6,6,6,6]
    print(candies(types))
