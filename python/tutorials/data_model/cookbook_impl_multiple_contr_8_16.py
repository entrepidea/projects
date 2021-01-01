import time
"""

A technique to implement more than one constructor is to use class method. 
Comparing to the other alternative, such as: __init__(self, *args), this technique
has the advantage of clarity. For example, t = Date.today() is obvisouly more clear 
than t= Date().

01/01/2021

"""
class Date:
    def __init__(self, year, month, day):
        self.year = year
        self.month = month
        self.day = day

    @classmethod
    def today(cls):
        t = time.localtime()
        return cls(t.tm_year, t.tm_mon, t.tm_mday)

    def print_today(self):
        cur = Date.today()
        print('Today is: {}/{}/{}'.format(cur.month, cur.day, cur.year))