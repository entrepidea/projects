"""
A Bitset is a data structure that compactly stores bits.

03/14/24

https://leetcode.com/problems/design-bitset/description/

"""

#https://leetcode.com/problems/design-bitset/solutions/1748480/c-python-different-solutions/
class Bitset(object):
    def __init__(self,size):
        self.a = 0
        self.size = size
        self.cnt = 0

    def fix(self, idx):
        if self.a & (1<<idx) == 0:
            self.a |= 1 << idx
            self.cnt += 1

    def unfix(self, idx):
        if self.a & (1<<idx):
            self.a ^= 1 << idx
            self.cnt -= 1

    def flip(self):
        self.a ^= (1<<self.size) - 1
        self.cnt = self.size - self.cnt

    def all(self):
        return self.cnt == self.size

    def one(self):
        return self.a > 0

    def count(self):
        return self.cnt

    def toString(self):
        a = bin(self.a)[2:]
        return a[::-1] + '0' * (self.size-len(a))

if __name__ == '__main__':
    bs = Bitset(8)
    bs.fix(1)
    print(bs.one())
    bs.unfix(1)
    print(bs.all())
    bs.flip()
    print(bs.all())
    print(bs.toString())
    print(bs.count())
    bs.flip()
    print(bs.toString())
    print(bs.count())

