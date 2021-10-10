"""
find the sum of two binary numbers represented by a string, and print out the result in a string format.
https://leetcode.com/problems/add-binary/
date: 10/09/21
"""
def add(str1,str2):
    bin_arr1 = list(str1)
    bin_arr2 = list(str2)
    
    min_len = min(len(bin_arr1), len(bin_arr2))
    max_len = max(len(bin_arr1), len(bin_arr2))
    
    if len(bin_arr1) < len(bin_arr2):
        for i in range(0,len(bin_arr2)-len(bin_arr1)):
            bin_arr1.insert(i,'0')
            
    if len(bin_arr2) < len(bin_arr1):
        for i in range(0,len(bin_arr1)-len(bin_arr2)):
            bin_arr2.insert(i,'0')
    
    #print(bin_arr1)
    #print(bin_arr2)

    
    stack = []
    rlt = []

    carry = 0
    for i in range(max_len-1,-1,-1):
        a = int(bin_arr1[i])
        b = int(bin_arr2[i])
        c = a+b+carry
        remainder = c%2
        carry = c//2
        #print('i=%d,carry=%d,remainder=%d'%(i,carry,remainder))
        stack.append(remainder)
        #rlt.insert(i+1,remainder)
    if carry > 0:
        stack.append(carry)

    while len(stack) > 0:
        rlt.append(stack.pop())

    return ''.join(str(e) for e in rlt)
    

if __name__ == '__main__':
    s1 = "11"
    s2 = "1"
    print(add(s1,s2))
    s1 = "1010"
    s2 = "1011"
    print(add(s1,s2))
