import heapq
"""
heapq can heapify a list and do many other things.
See the official doc: https://docs.python.org/3/library/heapq.html
"""


class PriorityQueue:
    def __init__(self):
        self._queue = []
        self._index = 0

    def push(self, item, priority):
        heapq.heappush(self._queue, (-priority, self._index, item))
        self._index += 1

    def pop(self):
        return heapq.heappop(self._queue)[-1]


class Item:
    def __init__(self, name):
        self.name = name

    def __repr__(self):
        return "Item({!r})".format(self.name)


if __name__ == '__main__':
    q = PriorityQueue()
    q.push(Item('Bar'), 1)
    q.push(Item('Foo'), 4)
    q.push(Item('Xyz'), 3)
    q.push(Item('Abc'), 2)
    print(q.pop())
    print(q.pop())
    print(q.pop())
    print(q.pop())
