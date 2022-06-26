"""

find out the longest common prefix among a bunch of strings
https://leetcode.com/problems/longest-common-prefix/
date: 10/08/21

"""
def longest(arr):
    arr_list = []
    for i in range(0,len(arr)):
        l = list(arr[i])
        arr_list.append(l)

    count = 0
    common = []
    while True:
        temp = arr_list[0][count]

        for i in range(1, len(arr_list)):
            if count < len(arr_list[i]) and temp == arr_list[i][count]:
                continue
            else:
                return common

        common.append(temp)        
        count += 1
            


                


if __name__ == '__main__':
    strs = ["flower","flow","flowight"]
    #strs = ["dog","racecar","car"]
    common = longest(strs)
    print(common)
