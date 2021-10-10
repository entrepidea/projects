"""
remove a specified number from an array. The operation is in place, no extra space is allowed.
source: https://leetcode.com/problems/remove-element/
date: 10/08/21
"""
def remove(arr, val):
    start_pt = 0
    while start_pt < len(arr):
        pt = start_pt
        while pt < len(arr) and arr[pt] == val:
            #print('start_pt=%d,pt=%d,value=%d'%(start_pt,pt,val))
            pt += 1
        if pt != start_pt: #value matched            
            temp = start_pt 
            while pt < len(arr):
                arr[temp] = arr[pt]
                arr[pt] = None
                pt += 1
                temp += 1

        #print(arr)
        start_pt += 1        


if __name__ == '__main__':
    num = [0,1,2,2,3,0,4,2]
    print('original:')
    print(num)
    val = 2
    remove(num, val)
    print('after removing %d'%val)
    print(num)

    nums = [3,2,2,3]
    val = 3
    print('original:')
    print(num)
    remove(num, val)
    print('after removing %d'%val)
    print(num)
