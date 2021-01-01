"""
Techniques shown in this code snippet eliminates the boilerplate code of __init__ in every class.

12/20/20

"""

import math


class Structure:
    # class variable that specifies expected fields
    _fields = []

    def __init__(self, *args):
        if len(args) != len(self._fields):
            raise TypeError('Expected to have {} fields.'.format(len(self._fields)))

        # set the attributes
        for name, value in zip(self._fields, args):
            setattr(self, name, value)


class Stock(Structure):
    _fields = ['name', 'price', 'shares']


class Point(Structure):
    _fields = ['x', 'y']


class Circle(Structure):
    _fields = ['radius']

    def area(self):
        return math.pi * self.radius ** 2
