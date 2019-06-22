import math

class Vector2d:
    typecode = 'd'

    def __init__(self,x,y):
        self.x = float(x)
        self.y = float(y)

    def __iter__(self):
        return (i for i in (self.x,self.y))


    def __repr__(self):
        classname = type(self).__name__
        return '{}({!r},{!r})'.format(classname,*self)

    def __eq__(self, other):
        return tuple(self) == tuple(other)

    def __abs__(self):
        return math.hypot(self.x,self.y)

    def __bool__(self):
        return bool(abs(self))

    def __str__(self):
        return str(tuple(self))
