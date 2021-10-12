#import sys
#sys.path.append('utils/')
#from LinkedListUtils import ListNode
#from LinkedListUtils import construct_linked_list

#from typing import Optional
def reverse(arr):
    print(f'length={len(arr)}, arr={arr}')        
    real_len = len(arr)
    k = 0

    for i in range(len(arr)-1,-1,-1):
        if i==0:
            k = i
            while arr[k] == 0:
                k += 1
            real_len = real_len - k
            i = k

    arr = arr[0:real_len]
    print(f'length={len(arr)}, arr={arr}')        

if __name__ == '__main__':
    s = '00000010100101000001111010011100'
    arr = list[s]
    reverse(arr)
