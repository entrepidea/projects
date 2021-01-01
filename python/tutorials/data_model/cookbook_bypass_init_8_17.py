from time import localtime
"""

use operator __new__ to bypass the exxecution of __init__ when instantiating a class. Sometime it's necessary, such as 
when deserializing a JSON string.

01/01/2021

"""
class Date:
    @classmethod
    def today(cls):
        t = localtime()
        d = cls.__new__(cls)
        d.year = t.tm_year
        d.month = t.tm_mon
        d.day = t.tm_mday
        return d
