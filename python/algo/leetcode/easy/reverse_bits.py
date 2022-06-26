"""
Reverse bits of a given 32 bits unsigned integer.
https://leetcode.com/problems/reverse-bits/

date: 10/13/21

"""
def reverse(arr):
    real_len = len(arr)
    #the three lines below are not necessary but it might have slight performance boost.
    #print(f'length={real_len}, arr={arr}')        
    #while real_len >= 0 and int(arr[real_len-1])==0:
    #    real_len -= 1
    
    sum = int(arr[0])
    for i in range(1,real_len):
        temp = int(arr[i])
        for j in range(0,i):
            temp *= 2
        sum += temp
    
    return sum

    
if __name__ == '__main__':
    s = '00000010100101000001111010011100'
    arr = list(s)
    print(reverse(arr))
    s = '11111111111111111111111111111101'
    arr = list(s)
    print(reverse(arr))
