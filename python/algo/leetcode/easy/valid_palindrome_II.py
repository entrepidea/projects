"""
Given a string s, return true if the s can be palindrome after deleting at most one character from it.
https://leetcode.com/problems/valid-palindrome-ii/

Date: 12/04/21

"""
from collections import Counter

#my solution - look like a little cumbersome? 
def palindrome(s) -> bool:
    arr = list(s)
    dic = Counter(arr)
    new_dic = dict(filter(lambda e : e[1]%2 !=0, dic.items()))
    if len(new_dic)>2:
        return False

    single_count_arr = []
    for e in new_dic.items():
        single_count_arr.append(e[0])
            
    #print(f'single count arr = {single_count_arr}')            
    a1 = arr.copy()
    if len(single_count_arr)==2:
        a1.remove(single_count_arr[0])
        #print(f'a1={a1}')

    ret = True
    for i in range(len(a1)//2):
        if a1[i] != a1[len(a1)-i-1]:
            ret = False
            break

    if ret == False:
        a2 = arr.copy()    
        if len(single_count_arr)==2:
            a2.remove(single_count_arr[1])
            #print(f'a2={a2}')
        for i in range(len(a2)//2):
            if a2[i] != a2[len(a2)-i-1]:
                ret = False

    return ret

#this solution is using two pointers from: 
#https://leetcode.com/problems/valid-palindrome-ii/discuss/1606437/Python-solution-beats-95-on-run-time-using-two-pointer
#NOTE: TODO don't understand
#Date: 12/06/21
def palindrome2(s: str) -> bool:
        left = 0
        right = len(s) - 1

        while left < right:
            if s[left]!=s[right]: #####first occurance of different char so check it s can be palindrome after deleting at most one character from it.
                temp = s[left+1:right+1] #####removing the first item
                temp2 = s[left:right] #####removing the  last item
                print(f'left={left}, right={right}, temp={temp},temp2={temp2},temp[::-1]={temp[::-1]}, temp2[::-1]={temp2[::-1]}')
                if temp==temp[::-1] or temp2==temp2[::-1]:  #####if any of them are palindrome then we got our result 
                    return True
                else: #####otherwise plindrome not possible 
                    return False 
			##### otherwise left item ar right item same so loop goes on
            left = left + 1
            right = right -1   
            
        return True

if __name__ == '__main__':
    s = 'abca'
    print(f'{s} satisfied? {palindrome2(s)}')
    """
    s = 'aaca'
    print(f'{s} satisfied? {palindrome2(s)}')
    s = 'aaaa'
    print(f'{s} satisfied? {palindrome2(s)}')
    s = 'aabaa'
    print(f'{s} satisfied? {palindrome2(s)}')
    s = 'aabcaa'
    print(f'{s} satisfied? {palindrome2(s)}')
    s = 'aaaabc'
    print(f'{s} satisfied? {palindrome2(s)}')
    s = 'cbaabc'
    print(f'{s} satisfied? {palindrome2(s)}')
    """
