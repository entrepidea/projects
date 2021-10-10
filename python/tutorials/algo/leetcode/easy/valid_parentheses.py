"""
Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

https://leetcode.com/problems/valid-parentheses/

date: 10/08/21

"""
def valid(arr):
    
    if len(str) % 2 != 0:
        return False

    dic = {')':'(', ']':'[','}':'{'}

    l = list(arr)
    stack = []
    
    for i in range(0,len(l)):
        if l[i] == '(' or l[i] == '[' or l[i] == '{':
            stack.append(l[i])
        else:
            top = stack.pop()
            if top != dic[l[i]]:
                return False
    return True

if __name__ == '__main__':
    str = '()[]{}'
    print(valid(str))
    str = '(]'
    print(valid(str))
    str = '({[]})'
    print(valid(str))
    str = '([)]'
    print(valid(str))
