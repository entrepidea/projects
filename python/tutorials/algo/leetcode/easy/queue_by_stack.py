"""
create a queue using two stacks. https://leetcode.com/problems/implement-queue-using-stacks/
date: 10/17/21
note: I used the Stack class that I created in stack_by_queue.py
"""
from stack_by_queue import Stack

class Queue:
    def __init__(self):
        self.primary_s = Stack()
        self.backup_s = Stack()
    
    def pop(self) -> int:
        while not self.primary_s.is_empty():
            self.backup_s.push(self.primary_s.pop())
        ret = self.backup_s.pop()
        while not self.backup_s.is_empty():
            self.primary_s.push(self.backup_s.pop())
        return ret      

    def push(self, data):
        self.primary_s.push(data)

    def is_empty(self) -> bool:
        return self.primary_s.is_empty()

if __name__ == '__main__':
    q = Queue()
    q.push(1)
    q.push(2)
    q.push(3)
    q.push(4)
    print(f'pop: {q.pop()}')
    print(f'pop: {q.pop()}')
    q.push(5)
    
    print('the queue is: ', end=' ')
    while not q.is_empty():
        print(q.pop(), end=',')
        
