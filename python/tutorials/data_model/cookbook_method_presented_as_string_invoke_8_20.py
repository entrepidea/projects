import math
import operator
"""

When python calls a method on an object, it's two-step procedure: first, lookup the object's attributes; seconds, invoke
the method. Below two approaches are presented to show how we can translate a string-represented-method into the action.

01/02/2021

"""
class Point:
    def __init__(self,x,y):
        self.x = x
        self.y = y

    def __repr__(self):
        return 'Point({!r},{!r})'.format(self.x, self.y)

    def distance(self,x,y):
        return math.hypot(self.x - x, self.y - y)

if __name__ == "__main__":
    p = Point(0,0)
    # first approach, use getattr to locate the method
    d = getattr(p, 'distance')(3,4)
    print(d)

    #second approach, use opeator.methodcall - has the advantage when repeative calls involve
    points = [
        Point(1, 2),
        Point(3, 0),
        Point(10, -3),
        Point(-5, -7),
        Point(-1, 8),
        Point(3, 2)
    ]
    # Sort by distance from origin (0, 0)
    points.sort(key=operator.methodcaller('distance', 0, 0))

    [print(p) for p in points]