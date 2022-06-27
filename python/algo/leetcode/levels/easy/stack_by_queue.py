"""
Implement a last-in-first-out (LIFO) stack using only two queues. The implemented stack should support all the functions of a normal stack (push, top, pop, and empty).
https://leetcode.com/problems/implement-stack-using-queues/
date: 10/16/21

note: when pop is invoked, move all the elements prior to the last one to a secondary queue.

"""

class Stack:
    def __init__(self):
        self.primary_q = []
        self.backup_q = []

    def __repr__(self):
        return str(self.primary_q)

    def push(self,data : int):
        #print(f'push: {data}')
        self.primary_q.append(data)

    def pop(self) -> int:
        if len(self.primary_q) == 1:
            return self.primary_q.pop()

        for i in range(len(self.primary_q)-1):
            self.backup_q.append(self.primary_q.pop(0))
        
        ret = self.primary_q.pop(0)
        self.primary_q = self.backup_q
        self.backup_q = []
        return ret

    def top(self) -> int:
        return self.primary_q[self.size()-1] if self.size() > 0 else None

    def empty(self):
        self.primary_q = []

    def is_empty(self) -> bool:
        return len(self.primary_q) == 0

    def size(self) -> int:
        return 0 if self.is_empty() else len(self.primary_q)


if __name__ == '__main__':
    s = Stack()
    #print(f'self made stack: {type(s)}')
    s.push(1)
    s.push(2)
    print(f'now stack is {s}, length={s.size()}')

    d = s.pop()
    print(f'{d} is popped.')
    d = s.pop()
    print(f'{d} is popped.')
    print(f'now stack is {s}, length={s.size()}')
    
    s.push(3)
    s.push(4)
    s.push(5)
    print(f'now stack is {s}, length={s.size()}')

    print(f'peek on the top of the stack: {s.top()}, size is still {s.size()}')

