"""

desc: to check if the parentheses come in pairs.
source: https://leetcode.com/problems/valid-parentheses/
date: 04/21/19

"""


class Stack:
    def __init__(self):
        self.stack = []

    def push(self,val):
        self.stack.append(val)
        return True

    def peek(self):
        return self.stack[-1]

    def pop(self):
        return self.stack.pop()

    def is_empty(self):
        return len(self.stack) == 0


def test_stack():
    s = Stack()
    s.push('t')
    s.push('u')
    s.push('s')
    assert s.pop() == 's'
    s.pop()
    s.pop()
    assert s.is_empty()


def test_string():
    str = "hello"
    #assert str[-1] == 'o'
    #assert str[0] == 'h'

    for s in str:
        print(s)

def valid_parentheses(str):
    stack = Stack()

    for s in str:
        if s =='{' or s == '[' or s == '(':
            stack.push(s)
        else:
            if s =='}':
                if stack.is_empty():
                    return False
                else:
                    if stack.pop() != '{':
                        return False
            else:
                if s == ']':
                    if stack.is_empty():
                        return False
                    else:
                        if stack.pop() != '[':
                            return False
                else:
                    if s == ')':
                        if stack.is_empty():
                            return False
                        else:
                            if stack.pop() != '(':
                                return False

    return stack.is_empty()


def test_valid_parentheses():
    assert valid_parentheses('([{}])') == True

def main():
    test_stack()

if __name__ == '__main__':
    main()