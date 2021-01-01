"""
shows how to cache a computed value - through the use of a descriptor class.

to test the code below, from python3 shell:
from cookbook_lazy_property_8_10 import Circle
from cookbook_lazy_property_8_10 import LazyProperty

12/19/20

"""
import math


# a descriptor class that can be used to cache a property
class LazyProperty:
    def __init__(self, func):
        self.func = func

    def __get__(self, instance, cls):
        if instance is None:
            return self
        else:
            value = self.func(instance)
            setattr(instance, self.func.__name__, value)
            return value


class Circle:
    def __init__(self, radius):
        self.radius = radius

    @LazyProperty
    def area(self):
        print('Compute Area')
        return math.pi * self.radius ** 2

    @LazyProperty
    def parameter(self):
        print('Compute parameter')
        return 2 * math.pi * self.radius
