"""

In addition, you may ask
why the __init__() method sets self.first_name instead of self._first_name . In
this example, the entire point of the property is to apply type checking when setting an
attribute. Thus, chances are you would also want such checking to take place during
initialization. By setting self.first_name , the set operation uses the setter method (as
opposed to bypassing it by accessing self._first_name ).

"""
import math


class Person:
    def __init__(self, first_name):
        self.first_name = first_name

    @property
    def first_name(self):
        return self._first_name

    @first_name.setter
    def first_name(self, value):
        if not isinstance(value, str):
            raise TypeError("Expected a string")
        self._first_name = value

    @first_name.deleter
    def first_name(self):
        raise AttributeError("Can't delete an attribute")


# Properties can also be a way to define computed attributes.
class Circle:
    def __init__(self, radius):
        self.radius = radius

    @property
    def area(self):
        return math.pi * self.radius ** 2

    @property
    def parameter(self):
        return 2 * math.pi * self.radius


def main():
    """
    a = Person('Guido')
    a.first_name = 42
    del a.first_name
    """
    c = Circle(4.0)
    print(c.area)
    print(c.parameter)


if __name__ == '__main__':
    main()
