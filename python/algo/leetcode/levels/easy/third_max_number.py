"""
Given an integer array nums, return the third distinct maximum number in this array. If the third maximum does not exist, return the maximum number.
https://leetcode.com/problems/third-maximum-number/

Date: 10/30/21

"""
def sort(pl):
    pl[0] = max(pl)
    pl[2] = min(pl)
    for e in pl:
        if e != pl[0] and e!= pl[2]:
            pl[1] = e                

def third(arr) -> int:
    if len(arr)<3:
        return max(arr)
    
    pl = arr[:3]
    sort(pl)

    for i in range(3,len(arr)):        
        if arr[i] in pl:
            continue
        if arr[i] > pl[0]:
            pl[2] = pl[1]
            pl[1] = pl[0]
            pl[0] = arr[i]
        else:
            if arr[i]> pl[1]:
                pl[2] = pl[1]
                pl[1] = arr[i]
            else:
                if arr[i] > pl[2]:
                    pl[2] = arr[i]
    return pl[2]                    

if __name__ == '__main__':
    nums = [3,2,1]
    print(third(nums))
    nums = [3,2,1,4,7,9,100,700,101,200,200]
    print(third(nums))
    
    

