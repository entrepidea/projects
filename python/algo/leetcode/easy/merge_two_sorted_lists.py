"""
https://leetcode.com/problems/merge-two-sorted-lists/
date:10/08/21
"""
def merge(l1,l2):
    #min_len = min(len(l1),len(l2))
    #print('min:=%d'%min_len)
    l = [None]*(len(l1)+len(l2))
    count = 0 
    count1 = 0
    count2 = 0

    while count1 < len(l1) and count2 < len(l2):
        if l1[count1]<=l2[count2]:
            l[count] = l1[count1]
            count1 += 1
        else:
            l[count] = l2[count2]
            count2 += 1
        
        count += 1

    #print(l)
    #print(count)
    #print(count1)
    #print(count2)

    if len(l1) > count1:
        while count1 < len(l1):
            l[count] = l1[count1]
            count+=1
            count1+=1

    if len(l2) > count2:
        while count2 < len(l2):
            l[count] = l2[count2]
            count+=1
            count2+=1

    return l

if __name__ == '__main__':
    l1 = [1,2,4,5,6,8] 
    l2 = [1,3,4,9,10,15,19]
    print(merge(l1,l2))
