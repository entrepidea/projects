"""
https://leetcode.com/problems/two-sum/
date: 10/07/21
"""

#brutal force - stupid, but it's a start. 10/07/21
def brutal(arr, target):
    ret = [-1]*2
    if len(arr)<2:
        return None
    for i in range(0, len(arr)):
        if arr[i] < target:
            remain = target - arr[i]
            for j in range(i+1, len(arr)):
                if arr[j] == remain:
                    ret[0] = i
                    ret[1] = j
                    return ret 

#problematic - same elements can't be used as dict's keys. TODO 10/07/21
def use_map(arr, target):
    #convert arr to dict with key=value,val=index
    dic = {arr[i]:i for i in range(0,len(arr))}
    #print(dic)
    for key, _ in dic.items():
        if key < target:
            remain = target - key
            #print('%d,%d'%(key,remain))
            if dic[remain] >= 0 and remain != key:
                return dic[key],dic[remain]

if __name__ == '__main__':
    arr = [2,7,11,15]
    print(brutal(arr,9))
    print(use_map(arr,9))

    arr = [3,2,4]
    print(brutal(arr,6))
    print(use_map(arr,6))

    arr = [3,3]
    print(brutal(arr,6))
    print(use_map(arr,6))
    

