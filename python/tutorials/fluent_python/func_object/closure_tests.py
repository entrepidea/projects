"""
A closure is a function with extended scope that encompass nonglobal variables referenced in the body of the function but not defined there.
A closure can be implemented in one of the two approaches as shown below.

From the book chapter 7 "Function decorators and closures", P.199

Date: 01/17/22

"""
class Averager():
    def __init__(self):
        self.series = []

    def __call__(self, new_val):
        self.series.append(new_val)
        total = sum(self.series)
        return total/len(self.series)


def make_averager():
    series = []
    def averager(new_val):
        series.append(new_val)
        total = sum(series)
        return total/len(series)

    return averager

if __name__ == '__main__':
    avg = Averager()
    print(avg(10))
    print(avg(11))
    print(avg(12))


    avg2 = make_averager()
    print(avg2(10))
    print(avg2(11))
    print(avg2(12))
